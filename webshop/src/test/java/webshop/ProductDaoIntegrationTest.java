package webshop;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import webshop.product.Product;
import webshop.product.ProductDao;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ProductDaoIntegrationTest {

    private ProductDao productDao;


    @Before
    public void init(){
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setURL("jdbc:mysql://localhost:3306/todos_webshop");
        dataSource.setUser("root");
        dataSource.setPassword("");

        productDao = new ProductDao(dataSource);

    }

    @Test
    public void testCreateAndList(){
        //Given
        productDao.deleteAll();

        //When
        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546);
        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156);
        productDao.addNewProductAndGetId(product1);
        productDao.addNewProductAndGetId(product2);

        //then
        List<Product> prod = productDao.listAllProducts();
        assertThat(prod.size() ,equalTo(2));
        assertThat(prod.get(0).getCode(),equalTo("PROD"));
        assertThat(prod.get(1).getCode(),equalTo("PROD2"));
        assertThat(prod.get(1).getAddress(),equalTo("sample2_prod"));
    }

    @Test
    public void testFindProduct(){
        //Given
        productDao.deleteAll();

        //When
        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546);
        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156);
        productDao.addNewProductAndGetId(product1);
        productDao.addNewProductAndGetId(product2);

        //then
        Product prod = productDao.findProductByAddress("sample2_prod");
        assertThat(prod.getCode(),equalTo("PROD2"));
    }


    @Test
    public void  testUpdateProduct(){
        //Given
        productDao.deleteAll();

        //When
        Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546);
        Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156);
        productDao.addNewProductAndGetId(product1);
        productDao.addNewProductAndGetId(product2);

        //then
       Product prod = productDao.findProductByAddress("sample2_prod");
       // productDao.updateProduct(prod.getId(), prod.getCode(), prod.getName(),  "manu2", 16000);
    }
}
