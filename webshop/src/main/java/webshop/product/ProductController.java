package webshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> listProduct(){
        return productService.findAll();
    }


    @PostMapping("/product/{address}")


    @GetMapping("/product/{address}")
    public Product findProductByAddress(@PathVariable String address){
        return productService.findProductByAddress(address);
    }
}
