package ks.training.sportsShop.controller.client;


import jakarta.servlet.http.HttpSession;
import ks.training.sportsShop.dto.UserNotificationDTO;
import ks.training.sportsShop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalNotificationAdvice {

    @Autowired
    private NotificationService notificationService;

    @ModelAttribute
    public void addUnreadNotificationCount(HttpSession session) {
        Long userId = (Long) session.getAttribute("id");
        if (userId != null) {
            List<UserNotificationDTO> notifications = notificationService.getAllForUser(userId);
            long unreadCount = notifications.stream().filter(n -> !n.isRead()).count();
            session.setAttribute("count", unreadCount);
        }
    }
}

