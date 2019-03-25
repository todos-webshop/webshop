package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import webshop.validator.Validator;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

@Component
public class UserValidator implements Validator {

    private UserService userService;
    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean userCanBeSaved(User user) {
        return nameIsNotEmptyOrNull(user.getFirstName() + user.getLastName()) && passwordIsNotEmptyOrNull(user.getPassword()) &&
                userIsNotRegisteredWithThisNameYet(user.getUsername())&& isUsernameValid(user);
    }

    public boolean userCanBeUpdated(User user) {
        return nameIsNotEmptyOrNull(user.getFirstName() + user.getLastName()) && passwordIsNotEmptyOrNull(user.getPassword())
                && isUsernameValid(user);
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
}
