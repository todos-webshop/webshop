package webshop.product;

import webshop.category.Category;
import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.category.Category;
import webshop.category.CategoryDao;

import java.util.List;


@Service
public class ProductService {

   private ProductDao productDao;
   private CategoryDao categoryDao;

    public ProductService(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    public List<Product> listAllProducts(){
       return productDao.listAllProducts();
   }

   public Object findProductByAddressTwo(String address){
       return productDao.findProductByAddressTwo(address);
   }
    public Product findProductByAddress(String address){
        return productDao.findProductByAddress(address);
    }
    public long addNewProductAndGetId(Product product, Category category){
       if (!productDao.isCodeUnique(product.getCode())){
           throw new IllegalArgumentException("This code already exists.");
       }
       if (!productDao.isNameUnique(product.getName())){
           throw new IllegalArgumentException("This name already exists.");
       }
       return productDao.addNewProductAndGetId(product, category);
    }

    public CustomResponseStatus updateProduct(long id, Category category){

        Category foundCategory = categoryDao.getIdOfTheUpdatedName(category);

       if (!productDao.isIdTheSameForUpdatingTheSameCode(category.getProducts().get(0).getCode(), id)){
           return new CustomResponseStatus(Response.FAILED, String.format("Code must be unique and %s already exists in database", category.getProducts().get(0).getCode()));
       }
       if (!productDao.isIdTheSameForUpdatingTheSameName(category.getProducts().get(0).getName(), id)){
           return new CustomResponseStatus(Response.FAILED, String.format("Name must be unique and %s already exists in database", category.getProducts().get(0).getName()));
       }
       if (productDao.isAddressEdited(category.getProducts().get(0).getAddress(), id)){
           return new CustomResponseStatus(Response.FAILED, "Address can not be edited.");
       }
       int responseInt = productDao.updateProduct(id, category, foundCategory.getId());
       if (responseInt == 1) {
           return new CustomResponseStatus(Response.SUCCESS, "Updated successfully.");
       } else {
           return new CustomResponseStatus(Response.FAILED, "Can not update.");
       }
    }



    public CustomResponseStatus logicalDeleteProductById(long id){
       if (productDao.isAlreadyDeleted(id)){
           return new CustomResponseStatus(Response.FAILED, "This product is already deleted.");
       }
       productDao.logicalDeleteProductById(id);
       return new CustomResponseStatus(Response.SUCCESS, "Deleted!");
    }
}
