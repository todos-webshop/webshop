package webshop.product;

import webshop.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.validator.ProductValidator;

import java.util.Arrays;
import java.util.List;

@RestController

public class ProductController {

    @Autowired
    private ProductService productService;

    private ProductValidator productValidator = new ProductValidator(productService);

    public static final String INVALID_ADDRESS = "Invalid address";

    @GetMapping("/api/product/")
            public Object throwErrorOnWrongAddress() {
            return new CustomResponseStatus(Response.FAILED, INVALID_ADDRESS);
            }

    @GetMapping("/api/product/{address}")
    public Object findProductByAddressTwo(@PathVariable String address) {
        productValidator = new ProductValidator(productService);
        if (productValidator.isValidAddress(address)) {
          try {
              return productService.findProductByAddress(address);
          } catch (Exception e){
              return new CustomResponseStatus(Response.FAILED, INVALID_ADDRESS);
          }
        } else {
            return new CustomResponseStatus(Response.FAILED,  INVALID_ADDRESS);
        }
    }

  //  @GetMapping("/api/product/{address}")
    public Category findProductByAddress(@PathVariable String address) {
        return productService.findProductByAddress(address);
    }
    @PostMapping("/api/products")
    public CustomResponseStatus addNewProduct(@RequestBody Category category) {
        productValidator.isAddressNull(category.getProducts().get(0));
        try {
            CustomResponseStatus responseStatus = productValidator.validateProduct(category.getProducts().get(0));
            if (responseStatus.getResponse().equals(Response.SUCCESS)) {
                long id = productService.addNewProductAndGetId(category);
                return new CustomResponseStatus(Response.SUCCESS, String.format("Successfully created with %d id", id));
            } else {
                return responseStatus;
            }
        } catch (IllegalArgumentException iae) {
            return new CustomResponseStatus(Response.FAILED, iae.getMessage());
        }
    }

    //@RequestMapping(value = "/api/product/{productId}", method = RequestMethod.POST)
    @PostMapping("/api/product/{productId}")
    public CustomResponseStatus updateProduct(@PathVariable Long productId, @RequestBody Category category) {
            CustomResponseStatus responseStatus = productValidator.validateProduct(category.getProducts().get(0));
            if (responseStatus.getResponse().equals(Response.FAILED)) {
                return responseStatus;
            } else {
                return productService.updateProduct(productId, category);
            }
    }

    @DeleteMapping("/api/product/{productId}")
    public CustomResponseStatus logicalDeleteProductById(@PathVariable long productId){
        return productService.logicalDeleteProductById(productId);
    }

    // just to know how the JSON should be
    @GetMapping("/api/productg")
    public Category get(){
        Category category = new Category(1,"Cat_name",2);
        category.setProducts(Arrays.asList(new Product(1,"code","name","manu",123,ProductStatus.ACTIVE)));
        return category;
    }



    @GetMapping("/api/product/recommend")
    public List<Product> lastThreeProducts(){
       return  productService.lastThreeProducts();
    }
}
