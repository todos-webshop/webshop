package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.user.UserService;

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
        if (userService.createUser(user) == 1) {
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


}