package ks.training.sportsShop.controller.client;


import java.security.Principal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.sportsShop.dto.RegisterDTO;
import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.entity.User;
import ks.training.sportsShop.service.OrderService;
import ks.training.sportsShop.service.ProductService;
import ks.training.sportsShop.service.UploadService;
import ks.training.sportsShop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;


    public HomePageController(
            ProductService productService,
            UserService userService,
            OrderService orderService, PasswordEncoder passwordEncoder, UploadService uploadService, EntityManager entityManager, UserDetailsService userDetailsService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> prs = this.productService.fetchProducts(pageable);
        List<Product> products = prs.getContent();

        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            model.addAttribute("passwordError", "Mật khẩu và xác nhận mật khẩu không khớp!");
            return "client/auth/register";
        }
        if (this.userService.getUserByEmail(registerDTO.getEmail()) != null) {
            model.addAttribute("passwordError", "Email đã tồn tại trong hệ thống");
            return "client/auth/register";
        }
        User user = this.userService.registerDTOtoUser(registerDTO);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));
        // save
        this.userService.handleSaveUser(user);
        return "redirect:/login";

    }

    @GetMapping("/account/info")
    public String getEditUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "client/account/detail";
    }

    @GetMapping("/account/update")
    public String getUpdateAccount(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
        }

        User user = userService.getUserByEmail(principal.getName());
        System.out.println("name:"+principal.getName());
        System.out.println(user);
        if (user == null) {
            return "redirect:/error"; // Tránh lỗi nếu user không tồn tại
        }

        model.addAttribute("newUser", user); // Đảm bảo model có newUser
        return "client/account/update";
    }


    @PostMapping("/account/update")
    public String handleUpdateAcc(@ModelAttribute("newUser") User user,
                                  @RequestParam("accountFile") MultipartFile file,
                                  HttpServletRequest request) { // Thêm HttpServletRequest
        User currentUser = this.userService.getUserById(user.getId());

        if (currentUser != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(img);
            }
            currentUser.setAddress(user.getAddress());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());

            this.userService.handleSaveUser(currentUser);
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("fullName", currentUser.getFullName());
            newSession.setAttribute("avatar", currentUser.getAvatar());
            newSession.setAttribute("user", currentUser);
        }

        return "redirect:/account/info";
    }

    @GetMapping("/account/change-password")
    public String showChangePasswordPage() {
        return "client/account/change-password";
    }

    @PostMapping("/account/update-password")
    public String handleChangePassword(@RequestParam("currentPassword") String currentPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(principal.getName());
        if (user == null) {
            return "redirect:/error";
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "redirect:/account/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới không khớp!");
            return "redirect:/account/change-password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.handleSaveUser(user);

        redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");
        return "redirect:/account/change-password";
    }
    @GetMapping("/lock")
    public String lockUser( Principal principal, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByEmail(principal.getName());
        user.setEnabled(false);
        this.userService.handleSaveUser(user);
        if (principal.getName().equals(user.getEmail())) {
            try {
                request.logout();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {

        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }


}
