package webshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ProductController {

    @Autowired
    private ProductService productService;

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
}
