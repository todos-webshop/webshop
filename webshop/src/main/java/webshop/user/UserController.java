package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Autowired
    private UserValidator validator;
    @Autowired
    private UserDao userDao;

    @PostMapping("/users")
    public CustomResponseStatus createUser(@RequestBody User user) {
        if (!validator.userCanBeUpdated(user)) {
            return new CustomResponseStatus(Response.FAILED, "Error! All fields are required.");
        }

        long newUserId = 0;
        try {
            newUserId = userService.createUserAndReturnUserId(user);
        } catch (DuplicateKeyException dke) {
            return new CustomResponseStatus(Response.FAILED, String.format("User already exists. " +
                            "New user can " +
                            "not be created for %s.",
                    user.getUsername()));
        }

        if (newUserId > 0) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("User %s " +
                            "successfully created.",
                    user.getUsername()));
        }
        return new CustomResponseStatus(Response.FAILED, "Error! User was not created.");
    }


    @GetMapping("/userdata")
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

    @GetMapping("/currentuserdata")
    public User currentUser(Authentication authentication) {
        if (authentication != null) {
            return userDao.getUserByUsername(authentication.getName());
        }
        return userDao.getUserByUsername(authentication.getName());
    }

    @GetMapping("/api/users")
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

//    @PostMapping("/user/{id}")
//public CustomResponseStatus modifyUserByUser(@PathVariable long id, @RequestBody User user){
//        if (validator.userCanBeUpdated(user)){
//            try {
//               userService.modifyUserByUser(id, user);
//               return new CustomResponseStatus(Response.SUCCESS, "User updated!");
//          } catch (org.springframework.dao.DuplicateKeyException exc) {
//          }}
//      return new CustomResponseStatus(Response.FAILED, "User update invalid!");}


    @PostMapping("/api/users/{id}")
    @ResponseBody
    public CustomResponseStatus checkPasswordAndmodifyUser(@PathVariable long id, @RequestBody User user) {
        if (validator.userCanBeUpdated(user)) {
            try {
                userService.checkPasswordAndmodifyUser(id, user);
                return new CustomResponseStatus(Response.SUCCESS, "User updated!");
            } catch (org.springframework.dao.DuplicateKeyException exc) {
                return new CustomResponseStatus(Response.FAILED, "This username already exists.");
            }
        }
        return new CustomResponseStatus(Response.FAILED, "User update invalid!");
    }

    @DeleteMapping("/api/users/{id}")
    public CustomResponseStatus logicalDeleteUserById(@PathVariable long id) {
        return userService.logicalDeleteUserById(id);
    }


    @ResponseBody
    public Long getCurrentUserId(@RequestBody User user, Authentication authentication) {
        if (currentUserName(authentication).equals(user.getUsername())) {
            return user.getId();
        }
        return user.getId();
    }

    @PostMapping("/api/user/{id}")
    public CustomResponseStatus modifyUserByUser(@PathVariable long id, @RequestBody User user) {
        if (validator.userCanBeUpdated(user)) {
            try {
                userService.modifyUserByUser(id, user);
                return new CustomResponseStatus(Response.SUCCESS, "User updated");
            } catch (org.springframework.dao.DuplicateKeyException exc) {
            }
        }
        return new CustomResponseStatus(Response.FAILED, "User update invalid!");
    }

//
//    @GetMapping("/userprofile")
//    public User findUserByUserName(Authentication authentication) {
//        return userService.findUserByUserName(authentication.getName());
//    }
//    @PostMapping("/userprofile")
//    public CustomResponseStatus updateUserDatasByUser(@RequestParam long id, @RequestBody User user ) {
//         if (validator.userCanBeUpdated(user)){
//            try {
//               userService.modifyUserByUser(id, user);
//              return new CustomResponseStatus(Response.SUCCESS, "User updated!");
//            } catch (org.springframework.dao.DuplicateKeyException exc) {
//        }}
//      return new CustomResponseStatus(Response.FAILED, "User update invalid!");}
//
//
}