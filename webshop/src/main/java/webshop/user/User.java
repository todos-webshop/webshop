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


    public User(long id, String firstName, String lastName, String username, String password, int enabled, UserRole userRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = passwordMaker(password);
        this.userRole = roleMaker(userRole);
        this.enabled = enabled;
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


    private UserRole roleMaker(UserRole role){
        if (role == null){
            return UserRole.ROLE_USER;
        }
        return role;
    }

    private String passwordMaker(String passwordString){
        if (isEmpty(passwordString)){
            return null;
        }
        return new BCryptPasswordEncoder(4).encode(passwordString);
    }

    private boolean isEmpty(String string){
        return string == null || string.trim().isEmpty();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
