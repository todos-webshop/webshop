package webshop.order;

import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.BasketDao;
import webshop.basket.BasketItem;
import webshop.user.User;
import webshop.user.UserDao;

import java.util.List;

@Service
public class OrderService {
    private OrderDao orderDao;
    private UserDao userDao;
    protected BasketDao basketDao;

    public OrderService(OrderDao orderDao, UserDao userDao, BasketDao basketDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.basketDao = basketDao;
    }

    public CustomResponseStatus placeOrder(String loggedInUsername) {

        User user = userDao.getUserByUsername(loggedInUsername);

        long userId = user.getId();
        long basketId = basketDao.getBasketIdByUserId(userId);

        List<BasketItem> products = basketDao.getBasketItemsInBasketByBasketId(basketId);

        if (products.size() == 0){
            return new CustomResponseStatus(Response.FAILED, "Your cart is empty.");
        }
        long orderid = orderDao.insertIntoOrdersFromBasketsByUserId(userId);

        for (BasketItem basketItem : products){
            orderDao.insertIntoOrderedItemsFromBasketItemsByOrderId(orderid, basketItem.getProduct().getId(), basketItem.getProduct().getPrice());
        }

        basketDao.clearBasketByBasketId(basketId);
        return new CustomResponseStatus(Response.SUCCESS, "Your order was placed, thank you for your purchase.");
    }
}