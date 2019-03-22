package webshop.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.product.Product;
import webshop.user.User;
import webshop.user.UserDao;
import webshop.user.UserData;

import java.util.List;

@Service
public class BasketService {

    //    @Autowired
    private BasketDao basketDao;
    private UserDao userDao;

    public BasketService(BasketDao basketDao, UserDao userDao) {
        this.basketDao = basketDao;
        this.userDao = userDao;
    }

    // user id is used from here (instead of username) in case username changes in time
    public Basket getBasketByUser(String loggedInUsername) {

        User user = userDao.getUserByUsername(loggedInUsername);

        long userId = user.getId();
        long basketId = 0;

        if (!basketDao.getAllBasketOwnerIds().contains(userId)) {
            basketId = basketDao.createBasketForUserIdAndReturnBasketId(userId);
        } else {
            basketId = basketDao.getBasketIdByUserId(userId);
        }

        if (basketId == 0) {
            throw new IllegalStateException("Basket does not exist.");
        }

        List<BasketItem> actualItemsInBasket = basketDao.getBasketItemsInBasketByBasketId(basketId);
        System.out.println(actualItemsInBasket);

        Basket basket = new Basket(basketId, new UserData(user.getUsername(), user.getUserRole()));
        for (BasketItem basketItem : actualItemsInBasket) {
            basket.addBasketItem(basketItem);
        }
        return basket;
    }


}
