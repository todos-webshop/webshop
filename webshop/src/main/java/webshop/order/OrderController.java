package webshop.order;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.Basket;
import webshop.basket.BasketData;
import webshop.user.UserData;
import webshop.user.UserRole;

import java.text.MessageFormat;
import java.util.List;

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

    @GetMapping("/myorders")
    public List<Order> listOrdersByUserId(Authentication authentication) {
        if (authentication != null) {
            String loggedInUsername = authentication.getName();

            return orderService.listOrdersByUserId(loggedInUsername);
        } else {
            return null;
        }
    }


    @GetMapping("/orders")
    public List<OrderData> listAllOrderData() {
        return orderService.listAllOrderData();
    }


    @GetMapping("/orders/{orderId}")
    public List<OrderItem> listOrderItemsByOrderId(@PathVariable long orderId) {
        return orderService.listOrderItemsByOrderId(orderId);
    }


    @DeleteMapping("/orders/{orderId}")
    public CustomResponseStatus logicalDeleteOrderByOrderId(@PathVariable long orderId) {
        if (orderService.logicalDeleteOrderByOrderId(orderId) == 1) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("Order %s successfully deleted.", orderId));
        }
        return new CustomResponseStatus(Response.FAILED, "An error occured during order delete.");
    }

}
