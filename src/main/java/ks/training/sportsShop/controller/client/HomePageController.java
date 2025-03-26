package ks.training.sportsShop.controller.client;


import java.util.List;

//import ks.training.sportsShop.dto.RegisterDTO;
import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.entity.User;
import ks.training.sportsShop.service.OrderService;
import ks.training.sportsShop.service.ProductService;
import ks.training.sportsShop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public HomePageController(
            ProductService productService,
            UserService userService,
            OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        // List<Product> products = this.productService.fetchProducts();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> prs = this.productService.fetchProducts(pageable);
        List<Product> products = prs.getContent();

        model.addAttribute("products", products);
        return "client/homepage/show";
    }

//    @GetMapping("/register")
//    public String getRegisterPage(Model model) {
//        model.addAttribute("registerUser", new RegisterDTO());
//        return "client/auth/register";
//    }
//
//    @PostMapping("/register")
//    public String handleRegister(
//            @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
//            BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "client/auth/register";
//        }
//
//        User user = this.userService.registerDTOtoUser(registerDTO);
//
//        String hashPassword = this.passwordEncoder.encode(user.getPassword());
//
//        user.setPassword(hashPassword);
//        user.setRole(this.userService.getRoleByName("USER"));
//        // save
//        this.userService.handleSaveUser(user);
//        return "redirect:/login";
//
//    }

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
