package webshop.user;

import webshop.validator.Validator;

import javax.validation.ConstraintValidatorContext;

public class UserValidator implements Validator {
    UserService userService;


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
