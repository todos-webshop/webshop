package webshop.product;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProductTest {



    @Test
    public void testCreateProduct() {
        Product product = new Product(1266, "H59L", "something good", "manufact", 1255,
        ProductStatus.ACTIVE );

        assertThat(product.getAddress(), equalTo("something_good"));
        assertThat(product.getProductStatus(), equalTo(ProductStatus.ACTIVE));
    }

    @Test
    public void testNormalize() {
        Product product = new Product(1266, "H59L", "árvíztűrő tükörfúrógép ", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product2 = new Product(1266, "H59L", "SoMeThInG   Good ", "manufact", 1255,
                ProductStatus.ACTIVE );
        Product product3 = new Product(1266, "H59L", "elkkelkáposztástalanítottátok", "manufact", 1255,
                ProductStatus.ACTIVE );

        assertThat(product.getAddress(), equalTo("arvizturo_tukorfurogep"));
        assertThat(product3.getAddress(), equalTo("elkkelkaposztastalanitottatok"));
        assertThat(product2.getAddress(), equalTo("something_good"));
    }

}
