package webshop.category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/products")
    public List<Category> listAllProducts(){
        return categoryService.listAllProducts();
    }

    @GetMapping("/api/categories")
    public List<String> listAllCategories(){
        return categoryService.listAllCategories();
    }
}
