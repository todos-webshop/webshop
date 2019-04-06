//package webshop.rate;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import webshop.CustomResponseStatus;
//import webshop.Response;
//import webshop.basket.BasketDao;
//import webshop.product.*;
//import webshop.user.*;
//
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.Matchers.closeTo;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql(scripts = "/init2.sql")
//public class RateIntegrationTest {
//
//    @Autowired
//    private UserController userController;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private RateController rateController;
//
//    private Authentication authenticationUser = new Authentication() {
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return null;
//        }
//
//        @Override
//        public Object getCredentials() {
//            return null;
//        }
//
//        @Override
//        public Object getDetails() {
//            return null;
//        }
//
//        @Override
//        public Object getPrincipal() {
//            return null;
//        }
//
//        @Override
//        public boolean isAuthenticated() {
//            return false;
//        }
//
//        @Override
//        public void setAuthenticated(boolean b) throws IllegalArgumentException {
//
//        }
//
//        @Override
//        public String getName() {
//            return "user";
//        }
//    };
//
//    private Authentication authenticationAdmin = new Authentication() {
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return null;
//        }
//
//        @Override
//        public Object getCredentials() {
//            return null;
//        }
//
//        @Override
//        public Object getDetails() {
//            return null;
//        }
//
//        @Override
//        public Object getPrincipal() {
//            return null;
//        }
//
//        @Override
//        public boolean isAuthenticated() {
//            return false;
//        }
//
//        @Override
//        public void setAuthenticated(boolean b) throws IllegalArgumentException {
//
//        }
//
//        @Override
//        public String getName() {
//            return "admin";
//        }
//    };
//
//    @Test
//    public void testGetRatesForProduct() {
//        Product product = productService.getProductByProductId(1);
//        List<Rate> rates = rateController.getRatesForProduct(1);
//        //(authenticationUser);
//        assertEquals(1, rates.size());
//        assertEquals(rates.get(0).getMessage(), "Szuper");
//        assertEquals(rates.get(0).getStars(), 3);
//
//    }
//
//    @Test
//    public void testGetUserRateForProduct() {
//        Rate rate = rateController.getUserRateForProduct(authenticationUser, 1);
//
//        assertEquals("Szuper", rate.getMessage());
//
//    }
//
//    @Test
//    public void testAddRate() {
//        Rate rate = rateController.getUserRateForProduct(authenticationUser, 4);
//        Product product = productService.getProductByProductId(4);
//        String loggedInUsername = authenticationUser.getName();
//        User loggedInUser = userService.getUserByUsername(loggedInUsername);
//        Rate newRate = new Rate (1,"Jó!",4, LocalDate.now(),loggedInUser,product);
//        rateController.addRate(authenticationUser , rate.getId(),  newRate);
//        List<Rate> rates = rateController.getRatesForProduct(4);
//        double avg = rateController.getAvgRatesForProduct(4);
//        assertEquals(1, rates.size());
//        assertEquals("Jó!", rates.get(0).getMessage());
//        assertEquals(4, rates.get(0).getStars());
//        assertThat(avg, closeTo(4.0, 0.1));
//
//
//}
//   @Test
//   public void testGetAvgRatesForProduct() {
//       double avg = rateController.getAvgRatesForProduct(1);
//       double avg2 = rateController.getAvgRatesForProduct(2);
//       double avg3 = rateController.getAvgRatesForProduct(3);
//       assertThat(avg, closeTo(3.0, 0.1));
//       assertThat(avg2, closeTo(1.0, 0.1));
//       assertThat(avg3, closeTo(5.0, 0.1));
//   }
//
//
//
//    @Test
//    public void testDeleteRate(){
//        Rate rate = rateController.getUserRateForProduct(authenticationUser, 4);
//        Product product = productService.getProductByProductId(4);
//        String loggedInUsername = authenticationUser.getName();
//        User loggedInUser = userService.getUserByUsername(loggedInUsername);
//        Rate newRate = new Rate (1,"Jó!",4, LocalDate.now(),loggedInUser,product);
//        rateController.addRate(authenticationUser , rate.getId(),  newRate);
//        rateController.deleteRate(authenticationUser, 4);
//        List<Rate> rates = rateController.getRatesForProduct(4);
//        Rate rateAfterDelete = rateController.getUserRateForProduct(authenticationUser, 4);
//
//        assertEquals(0, rates.size());
//        assertEquals("", rateAfterDelete.getMessage());
//
//    }
//
//
//    }
