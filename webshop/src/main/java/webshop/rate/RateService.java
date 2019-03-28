package webshop.rate;

import webshop.product.Product;

import java.util.List;

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
    public  long  addRate(Rate rate, long id){
        if (rateDao.getRateForUserAndProduct(rate).size()!=0){
            rateDao.updateRate(rate, id );
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
