package webshop.statics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/reports/reportone")
    public List<StatusOrderReport> doReportOne(){
       return staticsService.doReportOne();

    }
    @GetMapping("/reports/reportonesummary")
    public List<StatSummary> doReportOneSummary(){
       return staticsService.doReportOneSummary();

    }
}
