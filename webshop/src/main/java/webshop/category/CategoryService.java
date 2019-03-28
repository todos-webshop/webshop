package webshop.category;

import org.springframework.stereotype.Service;
import webshop.product.ProductDao;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDao categoryDao;
    private ProductDao productDao;

    public CategoryService(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    public List<Category> listAllProducts(){
        List<Category> categoryList = categoryDao.listAllCategories();

        for (Category category : categoryList){
            category.setProducts(productDao.listAllProductsByCategory(category));
        }
        return categoryList;
    }
}
