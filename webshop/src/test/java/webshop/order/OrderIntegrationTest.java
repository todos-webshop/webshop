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
        assertEquals(orders.get(0).getId(), 2);
        assertEquals(orders.get(2).getUserId(), 2);
        assertEquals(orders.get(1).getOrderItems().size(), 3);
    }

    @Test
    public void testAddOrder() {
        Order orderWithShippingAddressOnly = new Order(0, 0, null, null, 0, "2119 Pécel Diófa utca 23.");
        orderController.getOrderDataForActualUser(authenticationUser, orderWithShippingAddressOnly);
        List<Order> orders = orderController.listOrdersByUserId(authenticationUser);
        assertEquals(4, orders.size());
        assertEquals(orders.get(0).getUserId(), 2);
        assertEquals(orders.get(0).getOrderItems().size(), 3);
        assertEquals(orders.get(0).getTotalOrderPrice(), 68250);
        assertEquals(orders.get(0).getShippingAddress(), "2119 Pécel Diófa utca 23.");
        assertEquals(orders.get(0).getOrderItems().get(2).getPieces(), 10);
        assertEquals(orders.get(1).getOrderStatus().name(), "DELETED");
        assertEquals(orders.get(1).getOrderTime().getDayOfMonth(), 29);
    }


    @Test
    public void testListAllOrderData(){
        List<OrderData> allOrders = orderController.listAllOrderData();
        assertEquals(allOrders.get(0).getOrderStatus().name(), "DELETED");
        assertEquals(allOrders.get(0).getOrderTime().toString(), "2019-03-29T11:31:08");
        assertEquals(allOrders.get(0).getSumOrderPieces(), 38);
        assertEquals(allOrders.get(0).getSumOrderPrice(), 9800);
        assertEquals(allOrders.get(0).getOrderId(), 2);
        assertEquals(allOrders.get(0).getUsername(), "user");
    }

    @Test
    public void testListFilteredOrders(){
        List<OrderData> filteredOrders = orderController.listFilteredOrderData("ACTIVE");
        assertEquals(filteredOrders.get(0).getOrderStatus().name(), "ACTIVE");
        assertEquals(filteredOrders.get(0).getSumOrderPieces(), 14);
        assertEquals(filteredOrders.get(0).getSumOrderPrice(), 11700);
        assertEquals(filteredOrders.get(0).getOrderId(), 1);
        assertEquals(filteredOrders.get(0).getUsername(), "user");
    }

    @Test
    public void testUpdateOrderStatus(){
        CustomResponseStatus rsForActive = orderController.updateOrderStatus(1);
        assertEquals(rsForActive.getResponse().getDescription(), "Success");

        CustomResponseStatus rsForDeleted = orderController.updateOrderStatus(2);
        assertEquals(rsForDeleted.getResponse().getDescription(), "Failed");
    }

    @Test
    public void testLogicalDeleteOrder(){
        CustomResponseStatus rsForActive = orderController.logicalDeleteOrderByOrderId(1);
        assertEquals(rsForActive.getResponse().getDescription(), "Success");

        CustomResponseStatus rsForDelivered = orderController.logicalDeleteOrderByOrderId(3);
        assertEquals(rsForDelivered.getResponse().getDescription(), "Failed");
    }

    @Test
    public void testDeleteItemFromOrderByProductAddress(){
    orderController.deleteItemFromOrderByProductAddress(2, "bamboo_toothbrush");
    List<Order> orders = orderController.listOrdersByUserId(authenticationUser);
        assertEquals(orders.get(0).getId(), 2);
        assertEquals(orders.get(0).getOrderItems().size(), 1);
        assertEquals(orders.get(0).getOrderItems().get(0).getPieces(), 30);
        assertEquals(orders.get(0).getOrderItems().get(0).getProduct().getCode(), "CB4");
    }

    @Test
    public void testListOrderItemsByOrderId(){
        List<OrderItem> orderItems = orderController.listOrderItemsByOrderId(1);
        assertEquals(orderItems.size(), 3);
        assertEquals(orderItems.get(1).getProduct().getAddress(), "natural_coconut_bowl_set");
        assertEquals(orderItems.get(0).getPieces(), 1);
        assertEquals(orderItems.get(2).getPieces(), 3);
    }

    @Test
    public void testGetFormerShippingAddressesForActualUser(){
        List<Order> ordersWithAddressOnly = orderController.getFormerShippingAddressesForActualUser(authenticationUser);
        assertEquals(ordersWithAddressOnly.size(), 3);
        assertEquals(ordersWithAddressOnly.get(0).getShippingAddress(), "1111 BP. Csiga sor 3");
    }

}
