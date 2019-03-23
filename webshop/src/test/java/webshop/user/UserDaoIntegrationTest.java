package webshop.user;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import webshop.basket.BasketDao;
import webshop.product.Product;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserDaoIntegrationTest {
    private UserDao userDao;
    private BasketDao basketDao;
    private ProductDao productDao;

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

    @Test
    public void testCreateAndList(){
        //Given


        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

          userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);

        //then
        List<User> users = userDao.listAllUsers();
        List<String> names = userDao.getAllUsernames();
        assertThat(users.size() ,equalTo(2));
        assertThat(names.size() ,equalTo(2));
        assertThat(users.get(0).getUsername(),equalTo("testwoman"));
        assertThat(users.get(1).getUsername(),equalTo("testman"));
        assertThat(names.get(1),equalTo("testwoman"));
        assertThat(names.get(0),equalTo("testman"));

    }
}
