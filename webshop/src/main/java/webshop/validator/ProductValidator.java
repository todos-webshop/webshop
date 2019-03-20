package webshop.validator;

import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.product.Product;

public class ProductValidator implements Validator{

    public CustomResponseStatus validateProduct(Product product){
        if (isEmpty(product.getName()) || isEmpty(product.getCode()) || isEmpty(product.getManufacturer()) || product.getPrice() == 0){
            return new CustomResponseStatus(Response.FAILED, "These parameters can not be empty: code, name, manufacturer, price");
        }
        if (product.getPrice() > 2_000_000 || product.getPrice() < 0){
            return new CustomResponseStatus(Response.FAILED, "Price must be between 0 and 2M Ft.");
        }
        return new CustomResponseStatus(Response.SUCCESS, "Product is okay.");
    }

}
