package webshop.product;

import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;
import webshop.Response;

import java.util.List;


@Service
public class ProductService {

   private ProductDao productDao;

   public ProductService(ProductDao productDao){
       this.productDao = productDao;
   }

   public List<Product> listAllProducts(){
       return productDao.listAllProducts();
   }
   public Product findProductByAddress(String address){
       return productDao.findProductByAddress(address);
   }

    public long addNewProductAndGetId(Product product){
       if (!productDao.isCodeUnique(product.getCode())){
           throw new IllegalArgumentException("This code already exists.");
       }
       if (!productDao.isNameUnique(product.getName())){
           throw new IllegalArgumentException("This name already exists.");
       }
       return productDao.addNewProductAndGetId(product);
    }
    public CustomResponseStatus updateProduct(Product product, long id){
       if (!productDao.isIdTheSameForUpdatingTheSameCode(product.getCode(), id)){
           return new CustomResponseStatus(Response.FAILED, String.format("Code must be unique and %s already exists in database", product.getCode()));
       }
       if (!productDao.isIdTheSameForUpdatingTheSameName(product.getName(), id)){
           return new CustomResponseStatus(Response.FAILED, String.format("Name must be unique and %s already exists in database", product.getName()));
       }
       int responseInt = productDao.updateProduct(product, id);
       if (responseInt == 1) {
           return new CustomResponseStatus(Response.SUCCESS, "Updated successfully.");
       } else {
           return new CustomResponseStatus(Response.FAILED, "Can not update.");
       }
    }

    public void logicalDeleteProductById(long id){
       productDao.logicalDeleteProductById(id);
    }
}
