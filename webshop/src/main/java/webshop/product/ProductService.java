package webshop.product;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

   private ProductDao productDao;

   public ProductService(ProductDao productDao){
       this.productDao = productDao;
   }

   public List<Product> findAll(){
       return productDao.findAll();
   }
}
