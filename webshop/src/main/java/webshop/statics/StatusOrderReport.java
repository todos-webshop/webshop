package webshop.statics;

import java.time.LocalDate;

public class StatusOrderReport {

    private int year ;
    private int month ;
    private Integer sumOfActiveOrdersForThisMonth;
    private Integer sumOfAmountForActiveOrdersForThisMonth;
    private Integer sumOfDeliveredOrdersForThisMonth;
    private Integer sumOfAmountForDeliveredOrdersForThisMonth;
    private Integer sumOfDeletedOrdersForThisMonth;
    private Integer sumOfAmountForDeletedOrdersForThisMonth;

    public StatusOrderReport(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public Integer getSumOfActiveOrdersForThisMonth() {
        return sumOfActiveOrdersForThisMonth;
    }

    public Integer getSumOfAmountForActiveOrdersForThisMonth() {
        return sumOfAmountForActiveOrdersForThisMonth;
    }

    public Integer getSumOfDeliveredOrdersForThisMonth() {
        return sumOfDeliveredOrdersForThisMonth;
    }

    public Integer getSumOfAmountForDeliveredOrdersForThisMonth() {
        return sumOfAmountForDeliveredOrdersForThisMonth;
    }

    public Integer getSumOfDeletedOrdersForThisMonth() {
        return sumOfDeletedOrdersForThisMonth;
    }

    public Integer getSumOfAmountForDeletedOrdersForThisMonth() {
        return sumOfAmountForDeletedOrdersForThisMonth;
    }


    public void setSumOfActiveOrdersForThisMonth(Integer sumOfActiveOrdersForThisMonth) {
        this.sumOfActiveOrdersForThisMonth = sumOfActiveOrdersForThisMonth;
    }

    public void setSumOfAmountForActiveOrdersForThisMonth(Integer sumOfAmountForActiveOrdersForThisMonth) {
        this.sumOfAmountForActiveOrdersForThisMonth = sumOfAmountForActiveOrdersForThisMonth;
    }

    public void setSumOfDeliveredOrdersForThisMonth(Integer sumOfDeliveredOrdersForThisMonth) {
        this.sumOfDeliveredOrdersForThisMonth = sumOfDeliveredOrdersForThisMonth;
    }

    public void setSumOfAmountForDeliveredOrdersForThisMonth(Integer sumOfAmountForDeliveredOrdersForThisMonth) {
        this.sumOfAmountForDeliveredOrdersForThisMonth = sumOfAmountForDeliveredOrdersForThisMonth;
    }

    public void setSumOfDeletedOrdersForThisMonth(Integer sumOfDeletedOrdersForThisMonth) {
        this.sumOfDeletedOrdersForThisMonth = sumOfDeletedOrdersForThisMonth;
    }

    public void setSumOfAmountForDeletedOrdersForThisMonth(Integer sumOfAmountForDeletedOrdersForThisMonth) {
        this.sumOfAmountForDeletedOrdersForThisMonth = sumOfAmountForDeletedOrdersForThisMonth;
    }

    @Override
    public String toString() {
        return "StatusOrderReport{" +
                "year=" + year +
                ", month=" + month +
                ", sumOfActiveOrdersForThisMonth=" + sumOfActiveOrdersForThisMonth +
                ", sumOfAmountForActiveOrdersForThisMonth=" + sumOfAmountForActiveOrdersForThisMonth +
                ", sumOfDeliveredOrdersForThisMonth=" + sumOfDeliveredOrdersForThisMonth +
                ", sumOfAmountForDeliveredOrdersForThisMonth=" + sumOfAmountForDeliveredOrdersForThisMonth +
                ", sumOfDeletedOrdersForThisMonth=" + sumOfDeletedOrdersForThisMonth +
                ", sumOfAmountForDeletedOrdersForThisMonth=" + sumOfAmountForDeletedOrdersForThisMonth +
                '}';
    }
}
