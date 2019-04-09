package webshop.user;

import org.springframework.stereotype.Component;
import webshop.validator.Validator;


@Component
public class UserValidator implements Validator {

    private UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean userCanBeUpdated(User user) {
        return !isEmpty(user.getFirstName()) && !isEmpty(user.getPassword()) && !isEmpty(user.getLastName()) && isUsernameValid(user) && passwordIsValid(user.getPassword());
    }

    private boolean isUsernameValid(User user) {
        return !isEmpty(user.getUsername()) && !user.getUsername().toLowerCase().contains("deleted_user");
    }

    private boolean passwordIsValid(String pass) {
        return !isEmpty(pass);
    }
}
