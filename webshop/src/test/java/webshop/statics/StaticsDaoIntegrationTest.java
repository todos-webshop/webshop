package webshop.statics;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import webshop.basket.BasketDao;
import webshop.order.OrderDao;
import webshop.product.ProductDao;
import webshop.user.UserDao;

public class StaticsDaoIntegrationTest {


    private UserDao userDao;
    private BasketDao basketDao;
    private ProductDao productDao;
    private OrderDao orderDao;
    private StaticsDao staticsDao;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setURL("jdbc:mysql://localhost:3306/todos_webshoptest");
        dataSource.setUser("root");
        dataSource.setPassword("");

        basketDao = new BasketDao(dataSource);
        userDao = new UserDao(dataSource);
        productDao = new ProductDao(dataSource);
        basketDao.deleteAllBusketItems();
        basketDao.deleteAll();
        productDao.deleteAll();
        userDao.deleteAll();

    }
}
