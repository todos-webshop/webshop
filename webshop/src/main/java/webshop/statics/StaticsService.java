package webshop.statics;

import org.springframework.stereotype.Service;
import webshop.order.OrderDao;
import webshop.product.ProductDao;
import webshop.user.UserDao;

@Service
public class StaticsService {

    private UserDao userDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    public StaticsService(UserDao userDao, ProductDao productDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }


    public Stat getStat() {
      int users = userDao.countAllUsers();
        int countProducts = productDao.countAllProducts();
        int activeProducts = productDao.countActiveProducts();
        int activeOrders = orderDao.countActiveOrders();
        int countOrders = orderDao.countAllOrders();
      return new Stat(users, activeProducts, countProducts, activeOrders, countOrders);
    }
}
