package webshop.user;

import webshop.validator.Validator;

public class UserValidator implements Validator {
    UserService userService;


    public boolean userCanBeSaved(User user) {
        return nameIsNotEmptyOrNull(user.getFirstName() + user.getLastName()) && passwordIsNotEmptyOrNull(user.getPassword()) &&
                userIsNotRegisteredWithThisNameYet(user.getUsername());
    }

    public boolean userCanBeUpdated(User user) {
        return nameIsNotEmptyOrNull(user.getFirstName() + user.getLastName()) && passwordIsNotEmptyOrNull(user.getPassword());
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

//    @Override
//    public boolean isEmpty(String str) {
//        return false;
//    }
}
