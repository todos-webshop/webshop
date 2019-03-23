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
import webshop.user.User;
import webshop.user.UserController;
import webshop.user.UserRole;

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
	private UserController userController;

	@Test
	public void testCreateProduct() {
		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);
		productController.addNewProduct(product1);
		productController.addNewProduct(product2);
		List<Product> products = productController.listAllProducts();

		assertEquals(2, products.size());
		assertEquals("Sample", products.get(0).getName());

	}
	@Test
	public void testCreateBadProduct() {
		Product product1 = new Product(15, "PROD", "", "manufacture", 1546, ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);
		productController.addNewProduct(product1);
		productController.addNewProduct(product2);
		List<Product> products = productController.listAllProducts();

		assertEquals(1, products.size());
		assertEquals("Sample2 Prod", products.get(0).getName());

	}

	@Test
	public void  testUpdateProduct(){

		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546,ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);

		productController.addNewProduct(product1);
		productController.addNewProduct(product2);
		Product product3 = new Product(15, "PROD3", "Sample3 Prod", "manufacture", 1560,ProductStatus.ACTIVE);

		Product prod = productController.findProductByAddress("sample");
		CustomResponseStatus customResponseStatus = productController.updateProduct(prod.getId(), product3);
		List<Product> products = productController.listAllProducts();
		assertEquals(2, products.size());
		//assertEquals(customResponseStatus.getResponse(), Response.SUCCESS);
		assertEquals(customResponseStatus.getMessage(), "");
		assertEquals("Sample2 Prod", products.get(0).getName());
		assertEquals("Sample3 Prod", products.get(1).getName());
	}

	@Test
	public void  testUpdateBadProduct(){

		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546,ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);

		productController.addNewProduct(product1);
		productController.addNewProduct(product2);
		Product product3 = new Product(15, "PROD3", "Sample3 Prod", "manufacture", 2300000,ProductStatus.ACTIVE);

		Product prod = productController.findProductByAddress("sample");
		productController.updateProduct(prod.getId(), product3) ;
		List<Product> products = productController.listAllProducts();
		assertEquals(2, products.size());
		assertEquals("Sample2 Prod", products.get(1).getName());
		assertEquals("Sample", products.get(0).getName());
	}


	@Test
	public void testLogicalDeleteProductById() {
		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156,ProductStatus.ACTIVE);
		productController.addNewProduct(product1);
		productController.addNewProduct(product2);


		Product prodOriginal = productController.findProductByAddress("sample2_prod");
		productController.logicalDeleteProductById(prodOriginal.getId());
		Product result = productController.findProductByAddress(prodOriginal.getAddress());
		assertThat(result.getProductStatus(),equalTo(ProductStatus.DELETED));

	}

	@Test
	public void testFindProductByAddress() {
		Product product1 = new Product(15, "PROD", "Sample", "manufacture", 1546, ProductStatus.ACTIVE);
		Product product2 = new Product(15, "PROD2", "Sample2 Prod", "manufacture", 156, ProductStatus.ACTIVE);
		productController.addNewProduct(product1);
		productController.addNewProduct(product2);


		Product prod = productController.findProductByAddress("sample2_prod");
		assertThat(prod.getCode(),equalTo("PROD2"));


	}

	@Test
	public void testCreateUser() {
		User user1 = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null);
		User user2 = new User(123, "Test", "Man", "testman", "passTest", 1, UserRole.ROLE_ADMIN);
		userController.createUser(user1);
		userController.createUser(user2);
		List<User> users = userController.listAllUsers();

		assertEquals(2, users.size());
		assertEquals(UserRole.ROLE_ADMIN, users.get(1).getUserRole());
	}
	}
