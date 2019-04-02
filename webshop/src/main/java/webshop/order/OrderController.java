package webshop.order;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.Basket;
import webshop.basket.BasketData;
import webshop.user.UserData;
import webshop.user.UserRole;
import webshop.validator.OrderValidator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private OrderValidator orderValidator = new OrderValidator();

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/myorders")
    @ResponseBody
    public CustomResponseStatus getOrderDataForActualUser(Authentication authentication,
                                                          @RequestBody Order orderWithShippingAddressOnly) {
        if (authentication != null) {
            String shippingAddress;
            if (orderWithShippingAddressOnly == null) {
                shippingAddress = "";
            } else {
                shippingAddress = orderWithShippingAddressOnly.getShippingAddress();
            }
            if (orderValidator.isEmpty(shippingAddress)) {
                return new CustomResponseStatus(Response.FAILED, "Shipping address can not be empty.");
            }
            String loggedInUsername = authentication.getName();
            if (orderService.isShippingAddressAlreadyStored(loggedInUsername, shippingAddress)) {
                return new CustomResponseStatus(Response.FAILED, "Shipping address already stored. Please chose from the list " +
                        "below.");
            }
            return orderService.placeOrder(loggedInUsername, shippingAddress);
        } else {
            return new CustomResponseStatus(Response.FAILED, "Please log in to order.");
        }
    }


    @PostMapping("/myorders/storedaddresses")
    @ResponseBody
    public CustomResponseStatus sendOrderWithStoredAddress(Authentication authentication,
                                                           @RequestBody Order orderWithShippingAddressOnly) {
        if (authentication != null) {
            String shippingAddress;
            if (orderWithShippingAddressOnly == null) {
                shippingAddress = "";
            } else {
                shippingAddress = orderWithShippingAddressOnly.getShippingAddress();
            }
            if (orderValidator.isEmpty(shippingAddress)) {
                return new CustomResponseStatus(Response.FAILED, "Shipping address can not be empty.");
            }
            String loggedInUsername = authentication.getName();
            return orderService.placeOrder(loggedInUsername, shippingAddress);
        } else {
            return new CustomResponseStatus(Response.FAILED, "Please log in to order.");
        }
    }


    @GetMapping("/myorders")
    public List<Order> listOrdersByUserId(Authentication authentication) {
        if (authentication != null) {
            String loggedInUsername = authentication.getName();

            return orderService.listOrdersByOrderId(loggedInUsername);
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
        if (orderService.isOrderDeleted(orderId)) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("Order %d is already deleted.", orderId));
        }
        if (orderService.isOrderDelivered(orderId)) {
            return new CustomResponseStatus(Response.FAILED, String.format("Can not delete: order %d is already delivered.",
                    orderId));
        }
        if (orderService.logicalDeleteOrderByOrderId(orderId) == 1) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("Order %d successfully deleted.", orderId));
        }
        return new CustomResponseStatus(Response.FAILED, "An error occured during order delete.");
    }

    @DeleteMapping("/orders/{orderId}/{productAddress}")
    public CustomResponseStatus deleteItemFromOrderByProductAddress(@PathVariable long orderId,
                                                                    @PathVariable String productAddress) {
        if (orderService.deleteItemFromOrderByProductAddress(orderId, productAddress) > 0) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("Order item successfully removed from order.",
                    orderId));
        }
        return new CustomResponseStatus(Response.FAILED, "An error occured during item delete from order.");
    }

    @GetMapping("/orders/filtered/{filter}")
    public List<OrderData> listFilteredOrderData(@PathVariable String filter) {
        return orderService.listFilteredOrderData(filter);
    }

    @PostMapping("/orders/{orderId}/status")
    public CustomResponseStatus updateOrderStatus(@PathVariable long orderId) {
        String newOrderStatus = "DELIVERED";
        if (orderService.isOrderDelivered(orderId)) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("Order %d is already delivered.", orderId));
        }
        if (orderService.isOrderDeleted(orderId)) {
            return new CustomResponseStatus(Response.FAILED, String.format("Can not change status: order %d is already deleted.",
                    orderId));
        }
        if (orderService.updateOrderStatus(orderId, newOrderStatus) == 1) {
            return new CustomResponseStatus(Response.SUCCESS, String.format("Order status successfully updated for order %d.",
                    orderId));
        }
        return new CustomResponseStatus(Response.FAILED, "An error occured during order status update.");
    }

    @GetMapping(value = "/orders/shippingaddresses")
    @ResponseBody
    public List<Order> getFormerShippingAddressesForActualUser(Authentication authentication) {

        if (authentication != null) {
            String loggedInUsername = authentication.getName();

            return orderService.getOrderListWithFormerShippingAddressesOnly(loggedInUsername);
        } else {
            return new ArrayList<>();
        }
    }

}
