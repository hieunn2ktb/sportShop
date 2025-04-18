package ks.training.sportsShop.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserNotificationId implements Serializable {
    private Long userId;
    private Long notificationId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserNotificationId that = (UserNotificationId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(notificationId, that.notificationId);
    }

    public UserNotificationId(Long userId, Long notificationId) {
        this.userId = userId;
        this.notificationId = notificationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, notificationId);
    }

    public UserNotificationId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
