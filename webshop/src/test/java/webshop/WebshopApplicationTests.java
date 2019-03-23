package webshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.product.Product;
import webshop.product.ProductController;
import webshop.product.ProductStatus;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class WebshopApplicationTests {
	@Autowired
	private ProductController productController;

	@Test
	public void contextLoads() {
		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);
		productController.addNewProduct(product1);
		productController.addNewProduct(product2);
		List<Product> products = productController.listAllProducts();

		assertEquals(2, products.size());
		assertEquals("Sample", products.get(0).getName());

	}

	@Test
	public void  testUpdateProduct(){

		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546,ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);

		productController.addNewProduct(product1);
		productController.addNewProduct(product2);
		Product product3 = new Product(15, "PROD3", "Sample3 Prod", "manufacture", 1560,ProductStatus.ACTIVE);

		Product prod = productController.findProductByAddress("sample");
		productController.updateProduct(prod.getId(), product3) ;
		List<Product> products = productController.listAllProducts();
		assertEquals(2, products.size());
		assertEquals("Sample2 Prod", products.get(0).getName());
		assertEquals("Sample3 Prod", products.get(1).getName());
	}

	}
