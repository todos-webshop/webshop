package webshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.validator.ProductValidator;

import java.util.List;

@RestController

public class ProductController {

    @Autowired
    private ProductService productService;

    private ProductValidator productValidator = new ProductValidator(productService);

    @GetMapping("/api/products")
    public List<Product> listAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping("/api/product/")
            public Object throwErrorOnWrongAddress() {
            return new CustomResponseStatus(Response.FAILED, "Invalid address");
            }

    @GetMapping("/api/product/{address}")
    public Object findProductByAddressTwo(@PathVariable String address) {
        productValidator = new ProductValidator(productService);
        if (productValidator.isValidAddress(address)) {
          try {
              return productService.findProductByAddress(address);
          } catch (Exception e){
              return new CustomResponseStatus(Response.FAILED, "Invalid address");
          }
        } else {
            return new CustomResponseStatus(Response.FAILED, "Invalid address");
        }
    }

  //  @GetMapping("/api/product/{address}")
    public Product findProductByAddress(@PathVariable String address) {
        return productService.findProductByAddress(address);
    }
    @PostMapping("/api/products")
    public CustomResponseStatus addNewProduct(@RequestBody Product product) {
        try {
            CustomResponseStatus responseStatus = productValidator.validateProduct(product);
            if (responseStatus.getResponse().equals(Response.SUCCESS)) {
                long id = productService.addNewProductAndGetId(product);
                return new CustomResponseStatus(Response.SUCCESS, String.format("Successfully created with %d id", id));
            } else {
                return responseStatus;
            }
        } catch (IllegalArgumentException iae) {
            return new CustomResponseStatus(Response.FAILED, iae.getMessage());
        }
    }

    @PostMapping("/api/product/{productId}")
    public CustomResponseStatus updateProduct(@PathVariable Long productId, @RequestBody Product product) {
            CustomResponseStatus responseStatus = productValidator.validateProduct(product);
            if (responseStatus.getResponse().equals(Response.FAILED)) {
                return responseStatus;
            } else {
                return productService.updateProduct(product, productId);
            }
    }

    @DeleteMapping("/api/product/{productId}")
    public CustomResponseStatus logicalDeleteProductById(@PathVariable long productId){
        return productService.logicalDeleteProductById(productId);
    }
}
