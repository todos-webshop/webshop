package webshop.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webshop.user.User;
import webshop.user.UserData;
import webshop.user.UserRole;
import webshop.user.UserService;

@RestController
public class BasketController {

//    @Autowired
    private BasketService basketService;
    private UserService userService;

       public BasketController(BasketService basketService, UserService userService) {
        this.basketService = basketService;
        this.userService = userService;
    }


    @GetMapping(value = "/basket")
    @ResponseBody
    public Basket getBasketForActualUser(Authentication authentication) {

        if (authentication != null) {
            String loggedInUsername = authentication.getName();
            User loggedInUser = userService.getUserByUsername(loggedInUsername);
            return basketService.getBasketByUser(loggedInUser);
        } else {
            return new Basket(0, new UserData("", UserRole.NOT_AUTHENTICATED));
        }



}
