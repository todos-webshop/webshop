//package webshop;
//
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import webshop.basket.Basket;
//import webshop.basket.BasketController;
//import webshop.basket.BasketData;
//import webshop.basket.BasketService;
//import webshop.product.Product;
//import webshop.product.ProductController;
//import webshop.product.ProductData;
//import webshop.product.ProductStatus;
//import webshop.user.User;
//import webshop.user.UserController;
//import webshop.user.UserRole;
//import webshop.user.UserService;
//
//import static junit.framework.TestCase.assertEquals;
//import static org.junit.Assert.assertThat;
//import static org.hamcrest.CoreMatchers.equalTo;
//
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql(scripts = "/init.sql")
//public class WebshopApplicationTests {
//    @Autowired
//    private ProductController productController;
//    @Autowired
//    private UserController userController;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private BasketController basketController;
//    @Autowired
//    private BasketService basketService;
//
//    @Test
//    public void testCreateProduct() {
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//        List<Product> products = productController.listAllProducts();
//
//        assertEquals(2, products.size());
//        assertEquals("Sample", products.get(0).getName());
//
//    }
//
//    @Test
//    public void testCreateBadProduct() {
//        Product product1 = new Product(15, "PROD", "", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//        List<Product> products = productController.listAllProducts();
//
//        assertEquals(1, products.size());
//        assertEquals("Sample2 Prod", products.get(0).getName());
//
//    }
//
//    @Test
//    public void testUpdateProduct() {
//
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//        Product product3 = new Product(15, "PROD3", "Sample3 Prod", "manufacture", 1560, ProductStatus.ACTIVE);
//
//        Product prod = productController.findProductByAddress("sample");
//        CustomResponseStatus customResponseStatus = productController.updateProduct(prod.getId(), product3);
//        List<Product> products = productController.listAllProducts();
//        assertEquals(2, products.size());
//        //assertEquals(customResponseStatus.getResponse(), Response.SUCCESS);
//        assertEquals(customResponseStatus.getMessage(), "");
//        assertEquals("Sample2 Prod", products.get(0).getName());
//        assertEquals("Sample3 Prod", products.get(1).getName());
//    }
//
//    @Test
//    public void testUpdateBadProduct() {
//
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//        Product product3 = new Product(15, "PROD3", "Sample3 Prod", "manufacture", 2300000, ProductStatus.ACTIVE);
//
//        Product prod = productController.findProductByAddress("sample");
//        productController.updateProduct(prod.getId(), product3);
//        List<Product> products = productController.listAllProducts();
//        assertEquals(2, products.size());
//        assertEquals("Sample2 Prod", products.get(1).getName());
//        assertEquals("Sample", products.get(0).getName());
//    }
//
//
//    @Test
//    public void testLogicalDeleteProductById() {
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//
//
//        Product prodOriginal = productController.findProductByAddress("sample2_prod");
//        productController.logicalDeleteProductById(prodOriginal.getId());
//        Product result = productController.findProductByAddress(prodOriginal.getAddress());
//        assertThat(result.getProductStatus(), equalTo(ProductStatus.DELETED));
//
//    }
//
//    @Test
//    public void testFindProductByAddress() {
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//
//
//        Product prod = productController.findProductByAddress("sample2_prod");
//        assertThat(prod.getCode(), equalTo("PROD2"));
//
//
//    }
//
//    @Test
//    public void basketTest() {
//        User user1 = new User(45, "Angéla", "Tömlőssy", "edvin23", "diSzno!%sajt", 2, UserRole.ROLE_USER);
//        userController.createUser(user1);
//        String user1name = userController.listAllUsers().get(0).getUsername();
//
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productController.addNewProduct(product1);
//        productController.addNewProduct(product2);
//
//        ProductData productData1 = new ProductData("PROD", 3);
//        ProductData productData2 = new ProductData("PROD2", 1);
//        basketService.addProductToLoggedInBasketByProductData(user1name, productData1);
//        basketService.addProductToLoggedInBasketByProductData(user1name, productData2);
//
//        BasketData userBasketData = basketService.getBasketDataByUser(user1name);
//
//        assertEquals(2, userBasketData.getSumPieces());     //only 1 allowed for now
//        assertEquals(product1.getPrice() + product2.getPrice(), userBasketData.getSumPrice());
//        assertEquals(2, userBasketData.getBasket().getBasketItems().size());
//        assertEquals("Sample2 Prod", userBasketData.getBasket().getBasketItems().get(1).getProduct().getName());
//
//        basketService.clearBasketByUsername(user1name);
//        userBasketData = basketService.getBasketDataByUser(user1name);
//
//        assertEquals(0, userBasketData.getSumPieces());
//        assertEquals(0, userBasketData.getSumPrice());
//        assertEquals(0, userBasketData.getBasket().getBasketItems().size());
//    }
//
//    //    @Test
////    //@WithMockUser(username="admin",roles="ADMIN")
////    public void testAddProductToLoggedInBasket() {
////
////        User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, UserRole.ROLE_ADMIN);
////        userController.createUser(user1);
////        Product product1 = new Product(15, "PROD", "", "manufacture", 1546, ProductStatus.ACTIVE);
////        productController.addNewProduct(product1);
////        ProductData productData = new ProductData(product1.getCode(),1);
////        basketController.addProductToLoggedInBasket(productData);
////        BasketData basketData = basketController.getBasketDataForActualUser();
////        assertEquals(1,basketData.getBasket().getBasketItems().size());
////    }
//    @Test
//    public void testStatistics() {
//        //select count(*) order_time   FROM `orders` group by YEAR(order_time) ,  month(order_time)
//
//    }
//}
