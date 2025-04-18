package ks.training.sportsShop.repository;

import ks.training.sportsShop.dto.UserNotificationDTO;
import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n " +
            "WHERE (:title IS NULL OR n.title LIKE %:title%)")
    Page<Notification> findByNotificationByTitle(@Param("title") String title,
                                        Pageable pageable);

    @Query("""
    SELECT new ks.training.sportsShop.dto.UserNotificationDTO(
        n.id,
        n.title,
        n.message,
        COALESCE(un.isRead, false)
    )
    FROM Notification n
    LEFT JOIN UserNotification un 
        ON un.notification.id = n.id 
        AND un.user.id = :userId
    WHERE n.isSystem = true 
       OR un.user.id = :userId
        ORDER BY n.createdAt DESC
""")
    List<UserNotificationDTO> findAllWithStatus(@Param("userId") Long userId);

}
