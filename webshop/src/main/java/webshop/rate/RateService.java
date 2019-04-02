package webshop.rate;

import org.springframework.stereotype.Service;
import webshop.product.Product;
import webshop.user.User;

import java.util.List;
@Service
public class RateService {
    private RateDao rateDao;

    public RateService(RateDao rateDao){
        this.rateDao = rateDao;
    }

    public List<Rate> getRatesForProduct(Product product){
        return rateDao.getRatesForProduct(product);
    }

    public double getAvgRatesForProduct(Product product){
        return rateDao.getAvgRatesForProduct(product);
    }

    public long addRate(Rate rate, long id){
        rate.setMessage(deleteHTMLelements(rate.getMessage()));
        if (rateDao.getRateForUserAndProduct(rate).size()!=0){
           return rateDao.updateRate(rate, id );
              }
        return rateDao.addNewRateAndGetId(rate);
    }

    public Rate getRateForUserAndProduct(Rate rate){
        List<Rate>ratingList = rateDao.getRateForUserAndProduct(rate);
       if (ratingList.size() !=0) {
          return ratingList.get(0);
       }
       throw new IllegalArgumentException("The Rate does not exist!");
    }

    private String deleteHTMLelements(String string){
        return string.replaceAll("\\<.*?>","");

    }
    public int deleteRate(Product product, User user){
        return rateDao.deleteRate(product,user);
    }

    public boolean orderedProductByUser(Product product, User user) {
        return rateDao.orderedProductByUser(product,user);}

}
