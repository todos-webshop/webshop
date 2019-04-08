package webshop.statics;

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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init2.sql")
public class StaticsIntegrationTest {


    @Autowired
    private StaticsController staticsController;
    @Autowired
    private ProductController productController;
    @Autowired
    private CategoryController categoryController;


    @Test
    public void testGetStat(){
       Stat stat = staticsController.getStat();
        assertEquals(2,stat.getNumOfUsers());
        assertEquals(1,stat.getNumOfActiveOrders());
        assertEquals(5,stat.getNumOfProducts());

    }

    @Test
    public void testGetStatAfterAddProduct(){
        Category category =new Category( 1, "newCategory" , 5);
        categoryController.addNewCategory(category);
        Product product = new Product(5, "PROBA" ,"sample", "factory",15990,ProductStatus.ACTIVE);
           List <Product>   productList = new ArrayList<>() ;
           productList.add(product);
        category.setProducts(productList);
       List<Category> categoryList = categoryController.listAllCategories();
       Category catSample = categoryList.get(0);
       catSample.setProducts(productList);
        productController.addNewProduct(catSample);
        Stat stat = staticsController.getStat();
        assertEquals(6, stat.getNumOfProducts());

    }

    @Test
    public void testGetOrdersReport(){
        StatReportOne  statReportOne = staticsController.getOrdersReport();
        assertEquals(1,statReportOne.getStatusOrderReportList().size());
        assertThat(statReportOne.getStatusOrderReportList().get(0).getSumOfActiveOrdersForThisMonth(), is(1));
        assertEquals(2019,statReportOne.getStatusOrderReportList().get(0).getYear());
        assertEquals(1,statReportOne.getStatRowSummary().getActPiece());
    }


    @Test
    public void testGetProductsReport(){

    List<StatByProduct>  statByProducts = staticsController.getProductsReport();
        assertEquals(2,statByProducts.size());
        assertEquals(58800, statByProducts.get(0).getAmount());
        assertEquals("Natural Coconut Bowl Set", statByProducts.get(0).getProductName());

}
}

