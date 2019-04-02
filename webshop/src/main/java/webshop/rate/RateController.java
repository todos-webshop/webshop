package webshop.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.product.Product;
import webshop.product.ProductStatus;
import webshop.user.User;
import webshop.user.UserRole;
import webshop.user.UserService;

import java.time.LocalDate;
import java.util.List;
@RestController
public class RateController {
    @Autowired
    private RateService rateService;
    @Autowired
    private UserService userService;


    @GetMapping("/api/rating/list/{productid}")
    public List<Rate> getRatesForProduct(@PathVariable long productid) {
        Product product = new Product(productid, "MUZ", "muz", "muz", 0, ProductStatus.ACTIVE);
        return rateService.getRatesForProduct(product);
    }

    @GetMapping("/api/rating/avg/{productid}")
    public double getAvgRatesForProduct(@PathVariable long productid) {
        Product product = new Product(productid, "MUZ", "muz", "muz", 0, ProductStatus.ACTIVE);
        return rateService.getAvgRatesForProduct(product);
    }

    @GetMapping("/api/rating/{productid}")
    public Rate getUserRateForProduct(Authentication authentication, @PathVariable long productid) {
        Rate rateFromDB = new Rate(0, "", 1, LocalDate.now(), new User(15, "John", "Doe", "john", "123456", 1, UserRole.ROLE_USER), new Product(productid, "MUZ", "muz", "muz", 0, ProductStatus.ACTIVE));

        if (authentication != null) {
            String loggedInUsername = authentication.getName();
            User loggedInUser = userService.getUserByUsername(loggedInUsername);
            Rate rate = new Rate(0, "", 1, null, loggedInUser, new Product(productid, "MUZ", "muz", "muz", 0, ProductStatus.ACTIVE));

            try {
                rateFromDB = rateService.getRateForUserAndProduct(rate);

            } catch (IllegalArgumentException ie) {

            }
        }
        return rateFromDB;

    }

    @PostMapping("/api/rating/userrating/{id}")
    @ResponseBody
    public CustomResponseStatus addRate(Authentication authentication, @PathVariable long id, @RequestBody Rate rate) {
        System.out.println(authentication == null);
        if (authentication != null) {
            String loggedInUsername = authentication.getName();
            User loggedInUser = userService.getUserByUsername(loggedInUsername);
            rate.setUser(loggedInUser);
            rateService.addRate(rate, id);
            return new CustomResponseStatus(Response.SUCCESS, "Successful rating!");
        }
        return new CustomResponseStatus(Response.FAILED, "Please sign in to rate.");
    }

    @DeleteMapping("/api/rating/delete/{productId}")
    public CustomResponseStatus deleteRate(Authentication authentication,@PathVariable long productId) {
        if (authentication != null) {
            String loggedInUsername = authentication.getName();
            User loggedInUser = userService.getUserByUsername(loggedInUsername);
            Product product = new Product(productId, "MUZ", "muz", "muz", 0, ProductStatus.ACTIVE);

            int sqlResponse =
                    rateService.deleteRate(product, loggedInUser);
            if (sqlResponse == 0) {
                return new CustomResponseStatus(Response.SUCCESS, "You have no review.");
            } else {
                return new CustomResponseStatus(Response.SUCCESS, "Your review has been deleted.");
            }
        } else {
            return new CustomResponseStatus(Response.FAILED, "Please sign in to delete your ewview.");
        }
    }

    @GetMapping("/api/rating/controll/{productid}")
    public boolean canUserRateProduct(Authentication authentication, @PathVariable long productid) {

        if (authentication != null) {
            String loggedInUsername = authentication.getName();
            User loggedInUser = userService.getUserByUsername(loggedInUsername);
            Product product = new Product(productid, "MUZ", "muz", "muz", 0, ProductStatus.ACTIVE);

            return rateService.orderedProductByUser(product, loggedInUser);

            }
        return false;
        }

}


