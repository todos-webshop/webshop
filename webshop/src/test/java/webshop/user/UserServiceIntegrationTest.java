package webshop.user;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.basket.BasketDao;
import webshop.product.ProductDao;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BasketDao basketDao  ;
    @Autowired
    private UserDao userDao ;
    @Autowired
    private ProductDao productDao  ;

//    @Before
//    public void init(){
//        MysqlDataSource dataSource = new MysqlDataSource();
//
//        dataSource.setURL("jdbc:mysql://localhost:3306/todos_webshoptest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//        dataSource.setUser("root");
//        dataSource.setPassword("");
//
//        basketDao = new BasketDao(dataSource);
//        userDao = new UserDao(dataSource);
//        productDao = new ProductDao(dataSource);
//        userService = new UserService(userDao,basketDao);
//        basketDao.deleteAllBusketItems();
//        basketDao.deleteAll();
//        productDao.deleteAll();
//        userDao.deleteAll();
//
//    }

    @Test
        public void TestCreateUserAndReturnUserId()throws DuplicateKeyException {
        User user = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null );

        userService.createUserAndReturnUserId(user);

        assertThat(userDao.listAllUsers().size(),equalTo(1));
        assertThat(basketDao.getAllBasketOwnerIds().size(),equalTo(1));
        assertThat(basketDao.getAllBasketOwnerIds().contains(userDao.getUserByUsername("testwoman").getId()),equalTo(true));

    }
    @Test
    public void TestGetAllUsernames() throws DuplicateKeyException{
        User user = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null );
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);
        User user3 = new User(123, "Test", "Kid", "testkid", "passTest", 1, UserRole.ROLE_ADMIN);

        userService.createUserAndReturnUserId(user);
        userService.createUserAndReturnUserId(user2);
        userService.createUserAndReturnUserId(user3);

        List<String> usernames = userService.getAllUsernames();
        assertThat(usernames.size(),equalTo(3));
        assertThat(usernames.contains("testkid"),equalTo(true));
    }

    @Test
    public void TestListAllUsers() throws DuplicateKeyException {
        User user = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null );
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);
        User user3 = new User(123, "Test", "Kid", "testkid", "passTest", 1, UserRole.ROLE_ADMIN);

        userService.createUserAndReturnUserId(user);
        userService.createUserAndReturnUserId(user2);
        userService.createUserAndReturnUserId(user3);

        List<User> users = userService.listAllUsers();
        assertThat(users.size(),equalTo(3));
        assertThat(users.get(0).getUsername(),equalTo("testkid"));
    }
}
