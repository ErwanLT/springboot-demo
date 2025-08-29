package fr.eletutour.Internationalization.dto;

public class UserDto {

    private Long id;
    private String username;
    private String status;
    private String lastLoginDate;

    public UserDto() {
    }

    public UserDto(Long id, String username, String status, String lastLoginDate) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
