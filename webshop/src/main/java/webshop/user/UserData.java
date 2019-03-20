package webshop.user;

public class UserData {

    private String username;
    private UserRole role;

    public UserData(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
