package webshop.product;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProductDataTest {

    @Test
    public void testCreateProductData() {
        ProductData productData = new ProductData("FGH",15);

        assertThat(productData.getProductCode(), equalTo("FGH"));
        assertThat(productData.getProductPieces(), equalTo(1));
    }
}
