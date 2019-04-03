package webshop.product;
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
import webshop.basket.BasketController;
import webshop.basket.BasketDao;
import webshop.category.Category;
import webshop.category.CategoryController;
import webshop.product.*;
import webshop.user.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init2.sql")
public class ProductIntegrationTest {

    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductController productController;
    @Autowired
    private CategoryController categoryController;




    @Test
    public void testCreateProduct() {
        Category category =new Category( 1, "newCategory" , 5);
        categoryController.addNewCategory(category);
        Product product = new Product(5, "PROBA" ,"Sample", "factory",15990,ProductStatus.ACTIVE);
        List <Product>   productList = new ArrayList<>() ;
        productList.add(product);
        category.setProducts(productList);
        List<Category> categoryList = categoryController.listAllCategories();
        Category catSample = categoryList.get(0);
        catSample.setProducts(productList);
        productController.addNewProduct(catSample);
        Category catResult =productController.findProductByAddress("sample");
        assertEquals( catResult.getProducts().get(0).getName(),"Sample");
        assertEquals("PROBA", catResult.getProducts().get(0).getCode());

    }

//    @Test
//    public void testUpdateProduct() {
//        Category category =new Category( 1, "newCategory" , 5);
//        categoryController.addNewCategory(category);
//        Product product = new Product(5, "PROBA" ,"Sample", "factory",15990,ProductStatus.ACTIVE);
//        List <Product>   productList = new ArrayList<>() ;
//        productList.add(product);
//        category.setProducts(productList);
//        List<Category> categoryList = categoryController.listAllCategories();
//        Category catSample = categoryList.get(0);
//        catSample.setProducts(productList);
//        long productId = categoryList.get(0).getProducts().get(0).getId();
//        Category catChanged = new Category(1, "newCategory" , 5);
//        List <Product>   productList2 = new ArrayList<>() ;
//        Product product2 = new Product(5, "PROBA2" ,"Sample2", "factory2",15990,ProductStatus.ACTIVE);
//        productList2.add(product2);
//        category.setProducts(productList2);
//        productController.updateProduct(productId,category);
//        Category catResult =productController.findProductByAddress("sample2");
//        assertEquals( catResult.getProducts().get(0).getName(),"Sample2");
//    }

//@Test
//    public void testLogicalDeleteProduct(){
//    Category category =new Category( 1, "newCategory" , 5);
//
//    Product product = new Product(5, "PROBA" ,"Sample", "factory",15990,ProductStatus.ACTIVE);
//    List <Product>   productList = new ArrayList<>() ;
//    productList.add(product);
//    category.setProducts(productList);
//    List<Category> categoryList = categoryController.listAllCategories();
//    long productId = categoryList.get(0).getProducts().get(0).getId();
//    productController.logicalDeleteProductById(productId);
//    List<Category> categoryListChanged = categoryController.listAllCategories();
//    assertEquals(categoryListChanged.get(0).getProducts().get(0).getProductStatus(),ProductStatus.DELETED);


//}




}
