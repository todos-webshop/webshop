package webshop.user;

import com.mysql.cj.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.validator.Validator;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    //    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }


    public boolean userCanBeUpdated(User user) {
        return !isEmpty(user.getFirstName()) && !isEmpty(user.getPassword()) && !isEmpty(user.getLastName()) && isUsernameValid(user) && passwordIsValid(user.getPassword());
    }

//    private boolean nameIsNotEmptyOrNull(String name) {
//        return name != null && !name.trim().equals("");
//    }
//
//    private boolean passwordIsNotEmptyOrNull(String pass) {
//        return pass != null && !pass.trim().equals("");
//    }

//    private boolean userIsNotRegisteredWithThisNameYet(String newUsername) {
//        System.out.println(userService == null);
//        for (User user : userService.listAllUsers()) {
//            if (newUsername.equals(user.getUsername())) {
//                return false;
//            }
//        }
//        return true;
//    }

    private boolean isUsernameValid(User user) {
        return !isEmpty(user.getUsername()) && !user.getUsername().toUpperCase().contains("DELETED_USER");
    }


//    private boolean passwordIsValid(String pass) {
//        return pass != null && pass.matches("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$");
//    }

    private boolean passwordIsValid(String pass) {
        return !isEmpty(pass);
    }
}
