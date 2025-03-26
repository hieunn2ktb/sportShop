package ks.training.sportsShop.controller.admin;

import jakarta.validation.Valid;
import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.entity.Notification;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.service.NotificationService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController( NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/admin/notification")
    public String getDashboard(Model model,@RequestParam("page") Optional<String> pageOptional,
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
        Page<Notification> notifications = notificationService.getAllNotifications(title,pageable);
        List<Notification> list = notifications.getContent();
        int totalPages = notifications.getTotalPages();
        if (totalPages == 0){
            totalPages = 1;
        }
        model.addAttribute("notifications", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/notification/show";
    }
    @GetMapping("/admin/notification/create")
    public String getCreateNotification(Model model){
        model.addAttribute("newNotification", new Notification());
        return "admin/notification/create";
    }
    @PostMapping("/admin/notification/create")
    public String handleCreateNotification(@ModelAttribute("newProduct") @Valid Notification notification,
                                           BindingResult newProductBindingResult){
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        this.notificationService.createNotification(notification.getTitle(),notification.getMessage());
        return "redirect:/admin/notification";
    }
}
