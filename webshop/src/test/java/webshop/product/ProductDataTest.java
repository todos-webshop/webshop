package webshop.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ProductDataTest {

    @Test
    public void testCreateProductData() {
        ProductData productData = new ProductData("FGH",15);

        assertThat(productData.getProductCode(), equalTo("FGH"));
        assertThat(productData.getProductPieces(), equalTo(1));
    }
}
