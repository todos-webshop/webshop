package webshop.rate;

import webshop.CustomResponseStatus;

public class RateWithStatus {

    private CustomResponseStatus customResponseStatus;
    private Rate rate;

    public RateWithStatus(CustomResponseStatus customResponseStatus, Rate rate) {
        this.customResponseStatus = customResponseStatus;
        this.rate = rate;
    }

    public CustomResponseStatus getCustomResponseStatus() {
        return customResponseStatus;
    }

    public Rate getRate() {
        return rate;
    }
}
