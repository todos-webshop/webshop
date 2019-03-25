package webshop.statics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticsController {
    private StaticsService staticsService;

    public StaticsController(StaticsService staticsService) {
        this.staticsService = staticsService;
    }

    @GetMapping("/dashboard")
    public Stat getStat(){
        return staticsService.getStat();
    }

    @GetMapping("/reports/orders")
    public void getOrdersReport(){

    }

    @GetMapping("/reports/products")
    public void getProductsReport(){

    }
}
