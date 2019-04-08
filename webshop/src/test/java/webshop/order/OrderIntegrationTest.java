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

    private Authentication authenticationUser = new Authentication() {
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

    private Authentication authenticationAdmin = new Authentication() {
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
            return "admin";
        }
    };

    @Test
    public void testListOrders() {
        List<Order> orders = orderController.listOrdersByUserId(authenticationUser);
        assertEquals(3, orders.size());
        assertEquals(2,orders.get(0).getId());
        assertEquals(2,orders.get(2).getUserId());
        assertEquals(3, orders.get(1).getOrderItems().size());
    }

    @Test
    public void testAddOrder() {
        Order orderWithShippingAddressOnly = new Order(0, 0, null, null, 0, "2119 Pécel Diófa utca 23.");
        orderController.getOrderDataForActualUser(authenticationUser, orderWithShippingAddressOnly);
        List<Order> orders = orderController.listOrdersByUserId(authenticationUser);
        assertEquals(4, orders.size());
        assertEquals(2,orders.get(0).getUserId());
        assertEquals(3,orders.get(0).getOrderItems().size());
        assertEquals(68250,orders.get(0).getTotalOrderPrice());
        assertEquals("2119 Pécel Diófa utca 23.",orders.get(0).getShippingAddress());
        assertEquals(10,orders.get(0).getOrderItems().get(2).getPieces());
        assertEquals("DELETED", orders.get(1).getOrderStatus().name());
        assertEquals(29, orders.get(1).getOrderTime().getDayOfMonth());
    }


    @Test
    public void testListAllOrderData(){
        List<OrderData> allOrders = orderController.listAllOrderData();
        assertEquals("DELETED", allOrders.get(0).getOrderStatus().name());
        assertEquals("2019-03-29T11:31:08", allOrders.get(0).getOrderTime().toString());
        assertEquals(38, allOrders.get(0).getSumOrderPieces());
        assertEquals(9800, allOrders.get(0).getSumOrderPrice());
        assertEquals(2, allOrders.get(0).getOrderId());
        assertEquals("user", allOrders.get(0).getUsername());
    }

    @Test
    public void testListFilteredOrders(){
        List<OrderData> filteredOrders = orderController.listFilteredOrderData("ACTIVE");
        assertEquals("ACTIVE", filteredOrders.get(0).getOrderStatus().name());
        assertEquals(14, filteredOrders.get(0).getSumOrderPieces());
        assertEquals(11700,filteredOrders.get(0).getSumOrderPrice());
        assertEquals(1,filteredOrders.get(0).getOrderId());
        assertEquals("user", filteredOrders.get(0).getUsername());
    }

    @Test
    public void testUpdateOrderStatus(){
        CustomResponseStatus rsForActive = orderController.updateOrderStatus(1);
        assertEquals("Success", rsForActive.getResponse().getDescription());

        CustomResponseStatus rsForDeleted = orderController.updateOrderStatus(2);
        assertEquals("Failed", rsForDeleted.getResponse().getDescription());
    }

    @Test
    public void testLogicalDeleteOrder(){
        CustomResponseStatus rsForActive = orderController.logicalDeleteOrderByOrderId(1);
        assertEquals("Success", rsForActive.getResponse().getDescription());

        CustomResponseStatus rsForDelivered = orderController.logicalDeleteOrderByOrderId(3);
        assertEquals("Failed", rsForDelivered.getResponse().getDescription());
    }

    @Test
    public void testDeleteItemFromOrderByProductAddress(){
    orderController.deleteItemFromOrderByProductAddress(2, "bamboo_toothbrush");
    List<Order> orders = orderController.listOrdersByUserId(authenticationUser);
        assertEquals(2, orders.get(0).getId());
        assertEquals(1, orders.get(0).getOrderItems().size());
        assertEquals(30, orders.get(0).getOrderItems().get(0).getPieces());
        assertEquals("CB4", orders.get(0).getOrderItems().get(0).getProduct().getCode());
    }

    @Test
    public void testListOrderItemsByOrderId(){
        List<OrderItem> orderItems = orderController.listOrderItemsByOrderId(1);
        assertEquals(3, orderItems.size());
        assertEquals("natural_coconut_bowl_set", orderItems.get(1).getProduct().getAddress());
        assertEquals(1, orderItems.get(0).getPieces());
        assertEquals(3, orderItems.get(2).getPieces());
    }

    @Test
    public void testGetFormerShippingAddressesForActualUser(){
        List<Order> ordersWithAddressOnly = orderController.getFormerShippingAddressesForActualUser(authenticationUser);
        assertEquals(3, ordersWithAddressOnly.size());
        assertEquals("1111 BP. Csiga sor 3", ordersWithAddressOnly.get(0).getShippingAddress());
    }

}
