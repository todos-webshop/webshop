package webshop.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.BasketDao;
import webshop.product.Product;
import webshop.product.ProductController;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;
import webshop.user.User;
import webshop.user.UserController;
import webshop.user.UserDao;
import webshop.user.UserRole;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init2.sql")
public class OrderIntegrationTest {

    @Autowired
    private UserController userController;
    @Autowired
    private ProductController productController;
    @Autowired
    private OrderController orderController;

    private Authentication authentication = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "user";
        }
    };

    @Test
    public void test(){
        List<Order> orders = orderController.listOrdersByUserId(authentication);
        System.out.println(orders.toString());
        assertEquals(orders.size(),3);
        assertEquals(orders.get(0).getId(),1);
        assertEquals(orders.get(2).getUserId(),2);
        assertEquals(orders.get(1).getOrderItems().size(),2);
    }

}
