package webshop.basket;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import webshop.product.Product;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;
import webshop.user.User;
import webshop.user.UserDao;
import webshop.user.UserRole;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BasketDaoIntegrationTest {

    private BasketDao basketDao;
    private UserDao userDao;
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
    public void testAddProductToBasket(){

        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long userId1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long userId2 = userDao.getUserByUsername(user2.getUsername()).getId();
        long basketId1 =basketDao.createBasketForUserIdAndReturnBasketId(userId1);


        Product product = new Product(1266, "H59L", "something good", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59LTM", "something good23", "manufact", 1000,
                ProductStatus.ACTIVE );
        long productId1 = productDao.addNewProductAndGetId(product);
        long productId2 = productDao.addNewProductAndGetId(product2);


        basketDao.addProductToBasket(basketId1,productId1,5);
        basketDao.addProductToBasket(basketId1,productId2,5);
        //then
        List<BasketItem>  basketItems = basketDao.getBasketItemsInBasketByBasketId(basketId1);

        assertThat(basketItems.size() ,equalTo(2));
        assertThat(basketItems.get(0).getProduct().getId(),equalTo(productId1));
        assertThat(basketItems.get(1).getPieces(),equalTo(1));

    }
    @Test
    public void testSumProductPriceInBasketByBasketId(){


        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long userId1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long userId2 = userDao.getUserByUsername(user2.getUsername()).getId();
        long basketId1 =basketDao.createBasketForUserIdAndReturnBasketId(userId1);
        long basketId2 = basketDao.createBasketForUserIdAndReturnBasketId(userId2);

        Product product = new Product(1266, "H59L", "Something good", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59LTM", "Something good23", "manufact", 1000,
                ProductStatus.ACTIVE );
        long productId1 = productDao.addNewProductAndGetId(product);
        long productId2 = productDao.addNewProductAndGetId(product2);


        basketDao.addProductToBasket(basketId1,productId1,5);
        basketDao.addProductToBasket(basketId1,productId2,5);
        //then
        int prices = basketDao.sumProductPriceInBasketByBasketId(basketId1);

        assertThat(prices ,equalTo(2255));

    }

    @Test
    public void testClearBasketByBasketId(){
        //Given


        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long userId1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long userId2 = userDao.getUserByUsername(user2.getUsername()).getId();
        long basketId1 =basketDao.createBasketForUserIdAndReturnBasketId(userId1);
        long basketId2 = basketDao.createBasketForUserIdAndReturnBasketId(userId2);

        Product product = new Product(1266, "H59L", "Something good", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59LTM", "Something good23", "manufact", 1000,
                ProductStatus.ACTIVE );
        long productId1 = productDao.addNewProductAndGetId(product);
        long productId2 = productDao.addNewProductAndGetId(product2);


        basketDao.addProductToBasket(basketId1,productId1,5);
        basketDao.addProductToBasket(basketId1,productId2,5);
        //then
        basketDao.clearBasketByBasketId(basketId1);
        List<BasketItem> basketItems = basketDao.getBasketItemsInBasketByBasketId(basketId1);

        assertThat(basketItems.size() ,equalTo(0));
    }


    @Test
    public void testSumProductPiecesInBasketByBasketId(){


        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long userId1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long userId2 = userDao.getUserByUsername(user2.getUsername()).getId();
        long basketId1 =basketDao.createBasketForUserIdAndReturnBasketId(userId1);
        long basketId2 = basketDao.createBasketForUserIdAndReturnBasketId(userId2);

        Product product = new Product(1266, "H59L", "Something good", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59LTM", "Something good23", "manufact", 1000,
                ProductStatus.ACTIVE );
        long productId1 = productDao.addNewProductAndGetId(product);
        long productId2 = productDao.addNewProductAndGetId(product2);


        basketDao.addProductToBasket(basketId1,productId1,5);
        basketDao.addProductToBasket(basketId1,productId2,5);
        //then
        int pieces = basketDao.sumProductPiecesInBasketByBasketId(basketId1);

        assertThat(pieces ,equalTo(2));

    }


    @Test
    public void testDeleteOneProductFromBusket(){


        //When
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
        User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);

        userDao.createUserAndReturnUserId(user1);
        userDao.createUserAndReturnUserId(user2);
        long userId1 = userDao.getUserByUsername(user1.getUsername()).getId();
        long userId2 = userDao.getUserByUsername(user2.getUsername()).getId();
        long basketId1 =basketDao.createBasketForUserIdAndReturnBasketId(userId1);
        long basketId2 = basketDao.createBasketForUserIdAndReturnBasketId(userId2);

        Product product = new Product(1266, "H59L", "Something good", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59LTM", "Something good23", "manufact", 1000,
                ProductStatus.ACTIVE );
        long productId1 = productDao.addNewProductAndGetId(product);
        long productId2 = productDao.addNewProductAndGetId(product2);


        basketDao.addProductToBasket(basketId1,productId1,5);
        basketDao.addProductToBasket(basketId1,productId2,5);
        basketDao.deleteOneProductFromBusket(basketId1,productId2);
        //then
        int pieces = basketDao.sumProductPiecesInBasketByBasketId(basketId1);

        assertThat(pieces ,equalTo(1));

    }



}
