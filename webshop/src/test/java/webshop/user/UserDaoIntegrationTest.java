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
import webshop.product.Product;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class UserDaoIntegrationTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;

    @Test
    public void testCreateAndList()throws DuplicateKeyException {
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
        assertThat(users.get(1).getUsername(),equalTo("testwoman"));
        assertThat(users.get(0).getUsername(),equalTo("testman"));
        assertThat(names.get(1),equalTo("testwoman"));
        assertThat(names.get(0),equalTo("testman"));

    }
}
