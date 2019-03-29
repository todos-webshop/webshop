package webshop.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.product.Product;
import webshop.user.User;
import webshop.user.UserService;

import java.util.List;
@RestController
public class RateController {
    @Autowired
    private RateService rateService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/rating/{productId}")
    @ResponseBody
    public List<Rate> getRatesForProduct(@PathVariable long productId) {
        return rateService.getRatesForProduct(productId);
    }

    @GetMapping("/api/rating/avg{productId}")
    public double getAvgRatesForProduct(@PathVariable long productId) {
        return rateService.getAvgRatesForProduct(productId);
    }

    @PostMapping("/api/rating/userrating/{productId}")
    public CustomResponseStatus addRate(Authentication authentication, @PathVariable long productId,@RequestBody Rate rate) {
        if (authentication == null) {
            return new CustomResponseStatus(Response.FAILED, "Please sign in to rate.");
        }
        rate.setUser(userService.getUserByUsername(authentication.getName()));
        long returnLong = rateService.addRate(rate,productId);
        System.out.println(returnLong);
        return new CustomResponseStatus(Response.SUCCESS, "Successful rating!");
    }


}
