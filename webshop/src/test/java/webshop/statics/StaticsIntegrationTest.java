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
        assertEquals(stat.getNumOfUsers(), 2);
        assertEquals(stat.getNumOfActiveOrders(), 1);
        assertEquals(stat.getNumOfProducts(), 5);

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
        assertEquals(stat.getNumOfProducts(), 6);

    }

    @Test
    public void testGetOrdersReport(){
        StatReportOne  statReportOne = staticsController.getOrdersReport();
        assertEquals(statReportOne.getStatusOrderReportList().size(), 1);
        assertThat(statReportOne.getStatusOrderReportList().get(0).getSumOfActiveOrdersForThisMonth(), is(1));
        assertEquals(statReportOne.getStatusOrderReportList().get(0).getYear(), 2019);
        assertEquals(statReportOne.getStatRowSummary().getActPiece(),1);
    }


    @Test
    public void testGetProductsReport(){

    List<StatByProduct>  statByProducts = staticsController.getProductsReport();
        assertEquals(statByProducts.size(),2);
        assertEquals(statByProducts.get(0).getAmount(),58800);
        assertEquals(statByProducts.get(0).getProductName(),"Natural Coconut Bowl Set");

}
}

