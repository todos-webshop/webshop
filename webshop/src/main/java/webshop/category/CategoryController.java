package webshop.category;

import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.validator.CategoryValidator;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    private CategoryValidator categoryValidator = new CategoryValidator();

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/products")
    public List<Category> listAllProducts(){
        return categoryService.listAllProducts();
    }

    @GetMapping("/api/categories")
    public List<Category> listAllCategories(){
        return categoryService.listAllCategories();
    }

    @PostMapping("/api/categories")
    public CustomResponseStatus addNewCategory(@RequestBody Category category){
        if (categoryValidator.isEmpty(category.getCategoryName())){
            return new CustomResponseStatus(Response.FAILED, "Category name can not be empty.");
        }
        return categoryService.addNewCategory(category);
    }

    @PostMapping("/api/category/{categoryId}")
    public CustomResponseStatus updateCategoryById(@PathVariable long categoryId, @RequestBody Category category){
        category.setId(categoryId);
        if (categoryValidator.isEmpty(category.getCategoryName())){
            return new CustomResponseStatus(Response.FAILED, "Category name can not be empty.");
        }
        return categoryService.updateCategoryById(category);
    }

    @DeleteMapping("/api/category/{categoryId}")
    public CustomResponseStatus deleteCategoryAndUpdateProductCategoryToNoCategory(@PathVariable long categoryId){
        return categoryService.deleteCategoryAndUpdateProductCategoryId(categoryId);
    }

    @PostMapping("api/categories/update")
    public CustomResponseStatus updateAllCategories(@RequestBody List<Category> categories){
        return categoryService.updateAllCategories(categories);
    }

    @GetMapping("/api/category/{categoryName}")
    public Category listProductsByCategoryName(@PathVariable String categoryName){

        return categoryService.getCategoryWithProductsByName(categoryName.replaceAll("_"," "));
    }
}
