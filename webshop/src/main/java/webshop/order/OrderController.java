package webshop.order;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.Basket;
import webshop.basket.BasketData;
import webshop.user.UserData;
import webshop.user.UserRole;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/myorders")
    @ResponseBody
    public CustomResponseStatus getOrderDataForActualUser(Authentication authentication) {

        if (authentication != null) {
            String loggedInUsername = authentication.getName();

            return orderService.placeOrder(loggedInUsername);
        } else {
            return new CustomResponseStatus(Response.FAILED, "You must log in to order items.");
        }
    }
}
