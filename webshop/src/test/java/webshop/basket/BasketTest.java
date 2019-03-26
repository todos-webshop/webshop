package webshop.basket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.product.Product;
import webshop.product.ProductStatus;
import webshop.user.User;
import webshop.user.UserData;
import webshop.user.UserRole;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class BasketTest {




    @Test
    public void testCreateBasket() {
        User user = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null );
        UserData userData = new UserData(user.getUsername(),UserRole.ROLE_USER);
        Basket basket = new Basket(14, userData);
        assertThat(basket.getBasketOwner().getUsername(), equalTo("testwoman"));
        assertThat(basket.getBasketItems().size(), equalTo(0));
    }


    @Test
    public void testAddItems(){
        User user = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null );
        UserData userData = new UserData(user.getUsername(),UserRole.ROLE_USER);
        Basket basket = new Basket(14, userData);
        Product product = new Product(1266, "H59L", "something good", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59L", "something good23", "manufact", 1000,
                ProductStatus.ACTIVE );
        BasketItem basketItem = new BasketItem(product,15);
        BasketItem basketItem2 = new BasketItem(product2,15);
        basket.addBasketItem(basketItem);
        basket.addBasketItem(basketItem2);
       assertThat(basket.getBasketItems().size(), equalTo(2));
    }
}
