package ks.training.sportsShop.service;


import ks.training.sportsShop.dto.UserNotificationDTO;
import ks.training.sportsShop.entity.*;
import ks.training.sportsShop.repository.NotificationRepository;
import ks.training.sportsShop.repository.UserNotificationRepository;
import ks.training.sportsShop.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private  final UserRepository userRepository;
    private final UserNotificationRepository userNotificationsRepository;

    public NotificationService(NotificationRepository notificationRepository, UserService userService, UserRepository userRepository, UserNotificationRepository userNotificationsRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.userNotificationsRepository = userNotificationsRepository;
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
//    public List<Notification> getNotificationsForUser(Long userId) {
//        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
//    }

    public void markAsRead(Long userId, Long notificationId) {
        Optional<UserNotification> opt = userNotificationsRepository.findByUserIdAndNotificationId(userId, notificationId);

        if (opt.isPresent()) {
            UserNotification un = opt.get();
            un.setIsRead(true);
            userNotificationsRepository.save(un);
        } else {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));

            if (notification.isSystem()) {
                User user = userRepository.findById(userId);
                UserNotification un = new UserNotification();
                un.setId(new UserNotificationId(userId, notificationId));
                un.setUser(user);
                un.setNotification(notification);
                un.setIsRead(true);
                userNotificationsRepository.save(un);
            }
        }
    }

    public void createNotificationClient(User user, Order order, String title, String message) {
        Notification notification = new Notification();
        notification.setOrder(order);
        notification.setTitle(title);
        notification.setMessage(message);
        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();

        UserNotificationId userNotificationId = new UserNotificationId();
        userNotificationId.setUserId(user.getId());  // Gán userId
        userNotificationId.setNotificationId(notification.getId());

        userNotification.setId(userNotificationId);
        userNotification.setUser(user);
        userNotification.setNotification(notification);

        userNotificationsRepository.save(userNotification);
    }

    public void createNotificationAllClient(List<User> users, String title, String message) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notificationRepository.save(notification);

        for (User user : users) {
            UserNotification userNotification = new UserNotification();

            UserNotificationId userNotificationId = new UserNotificationId();
            userNotificationId.setUserId(user.getId());  // Gán userId
            userNotificationId.setNotificationId(notification.getId());

            userNotification.setId(userNotificationId);
            userNotification.setUser(user);
            userNotification.setNotification(notification);
            userNotificationsRepository.save(userNotification);
        }
    }


    public Optional<Notification> findById(Long notificationId) {
       return this.notificationRepository.findById(notificationId);
    }

    public List<UserNotificationDTO> getAllForUser(Long userId) {
        return notificationRepository.findAllWithStatus(userId);
    }


}
