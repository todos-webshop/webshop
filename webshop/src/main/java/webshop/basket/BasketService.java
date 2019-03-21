package webshop.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.product.Product;
import webshop.user.User;
import webshop.user.UserData;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    private BasketDao basketDao;


    // user id is used in case username changes is time
    public Basket getBasketByUser(User user) {
        long userId = user.getId();
        long basketId;
        if (!basketDao.getAllBasketOwnerIds().contains(userId)) {
            basketId = basketDao.createBasketForUserIdAndReturnBasketId(userId);
        } else {
             basketId = basketDao.getBasketIdByUserId(userId);
        }

        List<Product> actualProductsInBasket = basketDao.getProductsInBasketByBasketId(basketId);

        Basket basket = new Basket(basketId, new UserData(user.getUsername(), user.getUserRole()));
        for (Product product : actualProductsInBasket) {
            basket.addProduct(product);
        }
        return basket;
    }


}
