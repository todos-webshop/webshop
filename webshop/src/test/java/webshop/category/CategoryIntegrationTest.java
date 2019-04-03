package webshop.category;
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
public class CategoryIntegrationTest {

    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductController productController;
    @Autowired
    private CategoryController categoryController;

   @Test
    public void testListProducts(){
       Category cat = categoryController.listProductsByCategoryName("First category");
       assertEquals(cat.getProducts().size(),2);
       assertEquals(cat.getProducts().get(0).getName(),"Natural Coconut Bowl Set");
   }

   @Test

    public void testListCategories(){
       List<Category> cat =  categoryController.listAllCategories();
       assertEquals(cat.size(),2);
       assertEquals(cat.get(0).getProducts().size(),0);
       assertEquals(cat.get(1).getProducts().size(),0);

   }

}
