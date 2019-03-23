package webshop.basket;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import webshop.product.ProductDao;
import webshop.user.User;
import webshop.user.UserDao;
import webshop.user.UserRole;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BasketDaoIntegrationTest {

    private BasketDao basketDao;
    private UserDao userDao;


    @Before
    public void init(){
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setURL("jdbc:mysql://localhost:3306/todos_webshoptest");
        dataSource.setUser("root");
        dataSource.setPassword("");

        basketDao = new BasketDao(dataSource);
        userDao = new UserDao(dataSource);

    }


    @Test
    public void testCreateAndList(){
        //Given

        basketDao.deleteAll();
        userDao.deleteAll();

        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long id1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long id2 = userDao.getUserByUsername(user2.getUsername()).getId();
        basketDao.createBasketForUserIdAndReturnBasketId(id1);
        basketDao.createBasketForUserIdAndReturnBasketId(id2);

        //then
        List<Long> basketIds = basketDao.getAllBasketOwnerIds();
        assertThat(basketIds.size() ,equalTo(2));
        assertThat(basketIds.contains(id1),equalTo(true));
        assertThat(basketIds.contains(id2),equalTo(true));
        assertThat(basketDao.getBasketItemsInBasketByBasketId(id1).size(),equalTo(0));

    }


    @Test
    public void testClearBasketByBasketId(){
        //Given

        basketDao.deleteAll();
        userDao.deleteAll();

        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long id1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long id2 = userDao.getUserByUsername(user2.getUsername()).getId();
        basketDao.createBasketForUserIdAndReturnBasketId(id1);
        basketDao.createBasketForUserIdAndReturnBasketId(id2);

        //then
        basketDao.clearBasketByBasketId(basketDao.getBasketIdByUserId(id1));
        List<Long> basketIds = basketDao.getAllBasketOwnerIds();
        assertThat(basketIds.size() ,equalTo(2));
        assertThat(basketIds.contains(id1),equalTo(false));
        assertThat(basketIds.contains(id2),equalTo(true));

    }



}
