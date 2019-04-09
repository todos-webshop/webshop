package webshop.user;

import org.springframework.stereotype.Component;
import webshop.validator.Validator;


@Component
public class UserValidator implements Validator {

    public boolean userCanBeUpdated(User user) {
        return !isEmpty(user.getFirstName()) && !isEmpty(user.getLastName()) && isUsernameValid(user) && isPasswordValid(user.getPassword());
    }


    private boolean isUsernameValid(User user) {
        return !isEmpty(user.getUsername()) && !user.getUsername().toUpperCase().contains("DELETED_USER");
    }


    private boolean isPasswordValid(String pass) {
        return !isEmpty(pass) && pass.matches("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$");
    }


}
