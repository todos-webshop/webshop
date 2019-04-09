package webshop.user;

import org.springframework.stereotype.Component;
import webshop.Validator;


@Component
public class UserValidator implements Validator {

    public boolean userCanBeUpdated(User user) {
        return !isEmpty(user.getFirstName()) && !isEmpty(user.getLastName()) && isUsernameValid(user) && !isEmpty(user.getPassword());
    }


    private boolean isUsernameValid(User user) {
        return !isEmpty(user.getUsername()) && !user.getUsername().toUpperCase().contains("DELETED_USER");
    }


}
