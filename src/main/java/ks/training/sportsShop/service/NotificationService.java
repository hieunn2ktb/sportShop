package ks.training.sportsShop.service;

import ks.training.sportsShop.entity.Notification;
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
}
