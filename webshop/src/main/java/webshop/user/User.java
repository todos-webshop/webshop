package webshop.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int enabled;
    private UserRole userRole;


    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = new BCryptPasswordEncoder(4).encode(password);
        this.enabled = 1;
        this.userRole = UserRole.ROLE_USER;
    }


    public User(long id, String firstName, String lastName, String username, String password, int enabled, UserRole userRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getEnabled() {
        return enabled;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
