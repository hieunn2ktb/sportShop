package ks.training.sportsShop.dto;

public class UserNotificationDTO {
    private Long id;
    private String title;
    private String content;
    private boolean isRead;

    public UserNotificationDTO(Long id, String title, String content, boolean isRead ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

}

