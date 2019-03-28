package webshop.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.product.Product;
import webshop.user.User;
import webshop.user.UserService;

import java.util.List;

public class RateController {
    @Autowired
    private RateService rateService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/rating")
    public List<Rate> getRatesForProduct(@RequestBody Product product) {
        return rateService.getRatesForProduct(product);
    }

    @GetMapping("/api/rating/avg")
    public double getAvgRatesForProduct(@RequestBody Product product) {
        return rateService.getAvgRatesForProduct(product);
    }

    @PostMapping("/api/rating/userrating/")
    public CustomResponseStatus addRate( Authentication authentication,  @PathVariable long id,@RequestBody Rate rate) {
        if (authentication != null) {
            String loggedInUsername = authentication.getName();
           User loggedInUser = userService.getUserByUsername(loggedInUsername);
             rateService.addRate(rate,id);
             return new CustomResponseStatus(Response.SUCCESS, "Successful rating!");
        }
        return new CustomResponseStatus(Response.FAILED, "Please sign in to rate.");
    }


}
