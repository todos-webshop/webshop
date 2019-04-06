package webshop.rate;

import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;
import webshop.Response;
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

    public CustomResponseStatus addRate(Rate rate){
        rate.setMessage(deleteHTMLelements(rate.getMessage()));
        if (rateDao.orderedProductByUser(rate.getProduct(),rate.getUser())) {
            if (!rateDao.getRateForUserAndProduct(rate).isEmpty()) {
                int updateRows = rateDao.updateRate(rate);
                if (updateRows > 0) {
                    return new CustomResponseStatus(Response.SUCCESS, "Rate succesfully updated!");
                }
            }
            long addRows = rateDao.addNewRateAndGetId(rate);
            if (addRows >= 0) {
                return new CustomResponseStatus(Response.SUCCESS, "Rate successfully added!");
            }
            return new CustomResponseStatus(Response.FAILED, "Failed to rate!");
        } else {
            return new CustomResponseStatus(Response.FAILED, "Rate is only possible after the order was delivered.");
        }
    }

    public Rate getRateForUserAndProduct(Rate rate){
        List<Rate>ratingList = rateDao.getRateForUserAndProduct(rate);
        if (!ratingList.isEmpty()) {
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
