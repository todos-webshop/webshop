package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.user.UserService;

import java.util.List;

@RestController
public class UserController {

    private UserValidator validator = new UserValidator();

    @Autowired
    private UserService userService;


    @PostMapping("/users")
    public CustomResponseStatus createUser(@RequestBody User user) {
        if (validator.isEmpty(user.getUsername()) || validator.isEmpty(user.getFirstName()) || validator.isEmpty(user.getLastName())) {
            return new CustomResponseStatus(Response.FAILED, "Error! All fields are required.");
        }
        if (userService.getAllUsernames().contains(user.getUsername())){
            return new CustomResponseStatus(Response.FAILED, String.format("User already exists. " +
                            "New user can " +
                    "not be created for %s.",
                    user.getUsername()));
        }
        if (userService.createUserAndReturnUserId(user) > 0) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("User %s " +
                            "successfully created.",
                    user.getUsername()));
        }
        return new CustomResponseStatus(Response.FAILED, "Error! User was not created.");

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
public List<User> listAllUsers(){
return userService.listAllUsers();}


}