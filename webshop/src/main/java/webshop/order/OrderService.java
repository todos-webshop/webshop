package webshop.order;

import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.Basket;
import webshop.basket.BasketData;
import webshop.basket.BasketItem;
import webshop.user.User;
import webshop.user.UserDao;
import webshop.user.UserData;

import java.util.List;

@Service
public class OrderService {
    private OrderDao orderDao;
    private UserDao userDao;

    public OrderService(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    public CustomResponseStatus getItemsFromBasketAndAddToMyOrders(String loggedInUsername) {

        User user = userDao.getUserByUsername(loggedInUsername);

        long userId = user.getId();
        long orderId = orderDao.getItemsFromBasketAndAddToMyOrders(userId);

        if (orderId == 0){
            return new CustomResponseStatus(Response.FAILED, "Your cart is empty.");
        }
        return new CustomResponseStatus(Response.SUCCESS, "Successfully ordered");
    }
}
