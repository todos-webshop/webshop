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

    @GetMapping("api/products")
    public List<Product> listAllProducts(){
        return productService.listAllProducts();
    }


    @PostMapping("api/product/{address}")
    public void ProductShow(@PathVariable String addresss, @RequestBody Product product){
    }

    @GetMapping("product/{address}")
    public Product findProductByAddress(@PathVariable String address){
        return productService.findProductByAddress(address);
    }

    @PostMapping("api/products")
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
    @PostMapping("api/product/{id}")
    public ProductResponseStatus updateProduct(@PathVariable Long id, @RequestBody Product product){
        if (product.getName()==null|| product.getName().trim().equals("")){
            return new ProductResponseStatus(false,"Product name empty");
        }
        productService.updateProduct(id,product.getCode(),product.getName(),product.getAddress(),product.getManufacturer(),product.getPrice());
    return new ProductResponseStatus(true,"Minden ok");
    }
}
