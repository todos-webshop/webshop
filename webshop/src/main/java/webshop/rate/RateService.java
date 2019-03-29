package webshop.rate;

import org.springframework.stereotype.Service;
import webshop.product.Product;

import java.util.List;
@Service
public class RateService {
    private RateDao rateDao;

    public RateService(RateDao rateDao){
        this.rateDao = rateDao;
    }

    public List<Rate> getRatesForProduct(long productId){
        return rateDao.getRatesForProduct(productId);
    }

    public double getAvgRatesForProduct(long productId){
        return rateDao.getAvgRatesForProduct(productId);
    }

    public long addRate(Rate rate, long rateId){
        if (rateDao.getRateForUserAndProduct(rate).size()!=0){
            return rateDao.updateRate(rate, rateId);
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
}
