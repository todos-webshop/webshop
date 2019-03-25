package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.BasketDao;
import webshop.user.UserService;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserService userService;
    private UserValidator validator = new UserValidator();
    private UserValidator userValidator;
    private UserDao userDao;

    @PostMapping("/users")
    public CustomResponseStatus createUser(@RequestBody User user) {
        if (validator.isEmpty(user.getUsername()) || validator.isEmpty(user.getFirstName()) || validator.isEmpty(user.getLastName())) {
            return new CustomResponseStatus(Response.FAILED, "Error! All fields are required.");
        }
        if (userService.getAllUsernames().contains(user.getUsername())) {
            return new CustomResponseStatus(Response.FAILED, String.format("User already exists. " +
                            "New user can " +
                            "not be created for %s.",
                    user.getUsername()));
        }
        if (userService.createUserAndReturnUserId(user) > 0) {
            if (userValidator.userCanBeSaved(user)){
                return new CustomResponseStatus(Response.FAILED, "Error! User was not created.");}
        }
            return new CustomResponseStatus(Response.SUCCESS, String.format("User %s " +
                        "successfully created.",
                user.getUsername()));

    }


    @GetMapping(value = "/userdata")
    @ResponseBody
    public UserData currentUserName(Authentication authentication) {

        if (authentication != null) {
            String userRole = ((authentication.getAuthorities().toArray())[0]).toString();
            return new UserData(authentication.getName(),
                    UserRole.valueOf(userRole));

        } else {
            return new UserData("", UserRole.NOT_AUTHENTICATED);
        }
    }

    @GetMapping("/api/users")
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

    @PostMapping("/api/users/{id}")
    public CustomResponseStatus modifyUser(@PathVariable long id, @RequestBody User user) {
       UserValidator userValidator = new UserValidator();
        if (userValidator.userCanBeUpdated(user)) {
            userService.modifyUser(id, user);
            return new CustomResponseStatus(Response.SUCCESS, "User updated!");
        }
        return new CustomResponseStatus(Response.FAILED, "Failed to update user.");
    }
    @DeleteMapping("/api/users/{id}")
    public CustomResponseStatus logicalDeleteUserById(@PathVariable long id){
        if (userDao.isAlreadyDeleted(id)){
            return new CustomResponseStatus(Response.FAILED, "This user no longer exists.");
        }
        userService.logicalDeleteUserById(id);
        return new CustomResponseStatus(Response.SUCCESS, "User Deleted!");
    }
}