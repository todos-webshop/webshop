package webshop.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.basket.BasketDao;
import webshop.product.Product;
import webshop.product.ProductController;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;
import webshop.user.User;
import webshop.user.UserDao;
import webshop.user.UserRole;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class OrderDaoIntegrationTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private OrderDao orderDao;

    @Before
    public void init(){
        long userId = userDao.createUserAndReturnUserId(new User(1,"Fisrt", "User", "username", "Password2", 1, UserRole.ROLE_USER));
        Product product1 = new Product(11,"PR1", "Product 1", "manuf1", 1290, ProductStatus.ACTIVE);
        Product product2 = new Product(31,"PR2", "Product 2", "manuf2", 2290, ProductStatus.ACTIVE);
        long product1Id = productDao.addNewProductAndGetId(product1);
        long product2Id = productDao.addNewProductAndGetId(product2);
        long basketId = basketDao.createBasketForUserIdAndReturnBasketId(userId);
//      még semmi nem csinálok, de legalább vagyok ;)
    }

    @Test
    public void testMyOrders(){





    }

}
