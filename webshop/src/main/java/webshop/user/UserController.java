package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.user.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/users")
    public CustomResponseStatus createUser(@RequestBody User user) {
        if (userService.createUser(user) == 1) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("User %s " +
                            "successfully created.",
                    user.getUsername()));
        }
        return new CustomResponseStatus(Response.FAILED, "Error! User was not created.");

    }


    @GetMapping(value = "/userdata")
    @ResponseBody
    public String currentUserName(Authentication authentication) {

        if (authentication != null) {
            System.out.println(authentication.getName());
            System.out.println(authentication.getAuthorities().toString());
            return (authentication.getName());

        } else {
            return "";
        }
    }


}