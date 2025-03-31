package ks.training.sportsShop.repository;

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

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}
