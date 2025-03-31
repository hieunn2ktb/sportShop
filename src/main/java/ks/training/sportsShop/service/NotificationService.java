package ks.training.sportsShop.service;

import ks.training.sportsShop.entity.Notification;
import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.User;
import ks.training.sportsShop.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Page<Notification> getAllNotifications(String title,Pageable pageable) {
        return notificationRepository.findByNotificationByTitle(title,pageable);
    }

    public List<Notification> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll();
    }

    public Notification createNotification(String title, String message) {
        Notification notification = new Notification(title, message);
        return notificationRepository.save(notification);
    }
    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void createNotificationClient(User user, Order order, String title, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setOrder(order);
        notification.setTitle(title);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }
}
