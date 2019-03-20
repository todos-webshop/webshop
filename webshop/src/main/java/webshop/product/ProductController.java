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

    private ProductValidator productValidator = new ProductValidator();

    @GetMapping("/products")
    public List<Product> listAllProducts(){
        return productService.listAllProducts();
    }


    @PostMapping("/product/{address}")
    public void ProductShow(@PathVariable String addresss, @RequestBody Product product){

    }

    @GetMapping("/product/{address}")
    public Product findProductByAddress(@PathVariable String address){
        return productService.findProductByAddress(address);
    }

    @PostMapping("/products")
    public CustomResponseStatus addNewProduct(@RequestBody Product product){
        try {
            CustomResponseStatus responseStatus = productValidator.validateProduct(product);
            if (responseStatus.getResponse().equals(Response.SUCCESS)){
                long id = productService.addNewProductAndGetId(product);
                return new CustomResponseStatus(Response.SUCCESS, String.format("Successfully created with %d id", id));
            } else {
                return responseStatus;
            }
        } catch (IllegalArgumentException iae){
            return new CustomResponseStatus(Response.FAILED, iae.getMessage());
        }
    }
}
