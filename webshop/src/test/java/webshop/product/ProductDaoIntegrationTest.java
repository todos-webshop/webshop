//package webshop.product;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import webshop.basket.BasketDao;
//import webshop.user.UserDao;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.junit.Assert.assertThat;
//import java.util.List;
//
//import static junit.framework.TestCase.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql(scripts = "/init.sql")
//public class ProductDaoIntegrationTest {
//
//    @Autowired
//    private BasketDao basketDao;
//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private ProductDao productDao;
//
//
////    @Before
////    public void init(){
////        MysqlDataSource dataSource = new MysqlDataSource();
////
////        dataSource.setURL("jdbc:mysql://localhost:3306/todos_webshoptest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
////        dataSource.setUser("root");
////        dataSource.setPassword("");
////
////        basketDao = new BasketDao(dataSource);
////        userDao = new UserDao(dataSource);
////        productDao = new ProductDao(dataSource);
////        basketDao.deleteAllBusketItems();
////        basketDao.deleteAll();
////        productDao.deleteAll();
////        userDao.deleteAll();
////
////    }
//
//    @Test
//    public void testCreateAndList(){
//        //Given
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//
//        //then
//        List<Product> prod = productDao.listAllProducts();
//        assertThat(prod.size() ,equalTo(2));
//        assertThat(prod.get(0).getCode(),equalTo("PROD"));
//        assertThat(prod.get(1).getCode(),equalTo("PROD2"));
//        assertThat(prod.get(1).getAddress(),equalTo("sample2_prod"));
//    }
//    public void testDeleteAll(){
//        //Given
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//        productDao.deleteAll();
//        //then
//        List<Product> prod = productDao.listAllProducts();
//        assertThat(prod.size() ,equalTo(0));
//
//    }
//
//    @Test
//    public void testFindProductByAddress(){
//        //Given
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//
//        //then
//        Product prod = productDao.findProductByAddress("sample2_prod");
//        assertThat(prod.getCode(),equalTo("PROD2"));
//    }
//
//
//    @Test
//    public void  testUpdateProduct(){
//        //Given
//
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//
//        //then
//        Product product3 = new Product(15, "PROD3", "Sample3 Prod", "manufacture3", 1560,ProductStatus.ACTIVE);
//        Product prod = productDao.findProductByAddress("sample2_prod");
//        Product prodOriginal = productDao.findProductByAddress("sample2_prod");
//        productDao.updateProduct(product3,prod.getId());
//        Product prodChanged = productDao.findProductByAddress("sample3_prod");
//        assertThat(prodOriginal.getId(),equalTo(prodChanged.getId()));
//    }
//
//    @Test
//    public void  testGetProductIdByProductCode(){
//        //Given
//
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//
//        //then
//        Product prodOriginal = productDao.findProductByAddress("sample2_prod");
//        long id = productDao.getProductIdByProductCode("PROD2");
//
//        assertThat(prodOriginal.getId(),equalTo(id));
//    }
//
//
//    @Test
//    public void  testLogicalDeleteProductById(){
//        //Given
//
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//
//        //then
//
//        Product prodOriginal = productDao.findProductByAddress("sample2_prod");
//        productDao.logicalDeleteProductById(prodOriginal.getId());
//        Product result = productDao.findProductByAddress(prodOriginal.getAddress());
//        assertThat(result.getProductStatus(),equalTo(ProductStatus.DELETED));
//    }
//    @Test
//    public void  testIsCodeUnique(){
//        //Given
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//
//        //then
//        assertThat(productDao.isCodeUnique("PROD"),equalTo(false));
//        assertThat(productDao.isCodeUnique("PROD6"),equalTo(true));
//    }
//
//    @Test
//    public void  testIsIdTheSameForUpdatingTheSameName(){
//        //Given
//
//        //When
//        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
//        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//        productDao.addNewProductAndGetId(product1);
//        productDao.addNewProductAndGetId(product2);
//        Product product3 = new Product(15, "PROD2", "Sample3 Prod", "manufacture", 156, ProductStatus.ACTIVE);
//
//        //then
//        assertThat(productDao.isIdTheSameForUpdatingTheSameName(product3.getName(),product3.getId()),equalTo(true));
//        assertThat(productDao.isIdTheSameForUpdatingTheSameName(product2.getName(),product2.getId()),equalTo(false));
//    }
//
//
//
//
//}
