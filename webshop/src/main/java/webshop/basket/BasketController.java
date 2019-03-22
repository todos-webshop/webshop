package webshop.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.product.ProductData;
import webshop.user.User;
import webshop.user.UserData;
import webshop.user.UserRole;
import webshop.user.UserService;

@RestController
public class BasketController {

    //    @Autowired
    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping(value = "/basket")
    @ResponseBody
    public BasketData getBasketDataForActualUser(Authentication authentication) {

        if (authentication != null) {
            String loggedInUsername = authentication.getName();

            return basketService.getBasketDataByUser(loggedInUsername);
        } else {
            return (new BasketData(0, 0, new Basket(0, new UserData("",
                    UserRole.NOT_AUTHENTICATED))));
        }
    }

    @PostMapping("/basket")
    public CustomResponseStatus addProductToLoggedInBasket(Authentication authentication,
                                                           @RequestBody ProductData productData) {
        if (authentication != null) {
            String loggedInUsername = authentication.getName();
            int sqlResponse =
                    basketService.addProductToLoggedInBasketByProductData(loggedInUsername,
                            productData);
            if (sqlResponse != 1) {
                return new CustomResponseStatus(Response.FAILED, "Error. Could not add to basket.");
            } else {
                return new CustomResponseStatus(Response.SUCCESS, "Succesfully added to basket.");
            }
        } else {
            return new CustomResponseStatus(Response.FAILED, "Please sign in to start shopping.");
        }
    }


}
