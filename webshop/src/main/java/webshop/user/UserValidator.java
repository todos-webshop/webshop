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
        return nameIsNotEmptyOrNull(user.getFirstName()) || passwordIsNotEmptyOrNull(user.getPassword())
                || isUsernameValid(user) || nameIsNotEmptyOrNull(user.getLastName()) ||passwordIsValid(user.getPassword()) ;
    }

    private boolean nameIsNotEmptyOrNull(String name) {
        return name != null && !name.trim().equals("");
    }

    private boolean passwordIsNotEmptyOrNull(String pass) {
        return pass != null && !pass.trim().equals("");
    }

    private boolean userIsNotRegisteredWithThisNameYet(String newUsername) {
        System.out.println(userService == null);
        for (User user : userService.listAllUsers()) {
            if (newUsername.equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }
private boolean isUsernameValid(User user){
        if (user.getUsername().toUpperCase().contains("DELETED_USER")){
            return false;
        }return true;
}
    private boolean passwordIsValid(String pass) {
        return pass == null || pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$");
    }
    public boolean userCanBeSaved(User user) {
        return nameIsNotEmptyOrNull(user.getFirstName() + user.getLastName()) && passwordIsNotEmptyOrNull(user.getPassword()) &&
                userIsNotRegisteredWithThisNameYet(user.getUsername())&& isUsernameValid(user);
    }

}
