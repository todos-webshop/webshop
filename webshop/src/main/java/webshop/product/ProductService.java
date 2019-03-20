package webshop.product;

import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;

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
}
