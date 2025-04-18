package ks.training.sportsShop.controller.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ks.training.sportsShop.dto.UserNotificationDTO;
import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.entity.Notification;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.entity.User;
import ks.training.sportsShop.repository.UserNotificationRepository;
import ks.training.sportsShop.service.NotificationService;
import ks.training.sportsShop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    private final UserNotificationRepository userNotificationRepository;
    public NotificationController(NotificationService notificationService, UserService userService, UserNotificationRepository userNotificationRepository) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.userNotificationRepository = userNotificationRepository;
    }

    @GetMapping("/admin/notification")
    public String getDashboard(Model model, @RequestParam("page") Optional<String> pageOptional,
                               @RequestParam(required = false) String title) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
            }
        } catch (Exception e) {
        }

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Notification> notifications = notificationService.getAllNotifications(title, pageable);
        List<Notification> list = notifications.getContent();
        int totalPages = notifications.getTotalPages();
        if (totalPages == 0) {
            totalPages = 1;
        }
        model.addAttribute("notifications", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/notification/show";
    }

    @GetMapping("/admin/notification/create")
    public String getCreateNotification(Model model) {
        model.addAttribute("newNotification", new Notification());
        return "admin/notification/create";
    }

    @PostMapping("/admin/notification/create")
    public String handleCreateNotification(@ModelAttribute("newNotification") @Valid Notification notification) {
        List<User> users = userService.getAllUsers();
        this.notificationService.createNotificationAllClient(users,notification.getTitle(),notification.getMessage());
        return "redirect:/admin/notification";
    }
    @GetMapping("/notifications")
    public String getAllNotifications(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/login";
        }
        List<UserNotificationDTO> notifications = notificationService.getAllForUser(userId);
        long unreadCount = notifications.stream().filter(n -> !n.isRead()).count();
        session.setAttribute("count", unreadCount);
        model.addAttribute("notifications", notifications);
        return "client/notification/show";
    }

    @PostMapping("/markAsRead")
    public String markAsRead(@RequestParam("userId") Long userId,
                             @RequestParam("notificationId") Long notificationId) {
        notificationService.markAsRead(userId, notificationId);
        return "redirect:/notifications";
    }

}
