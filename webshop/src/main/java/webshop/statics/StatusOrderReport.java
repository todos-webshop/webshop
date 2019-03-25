package webshop.statics;

import java.time.LocalDate;

public class StatusOrderReport {

    private LocalDate when;
    private Integer sumOfActiveOrdersForThisMonth;
    private Integer sumOfAmountForActiveOrdersForThisMonth;
    private Integer sumOfDeliveredOrdersForThisMonth;
    private Integer sumOfAmountForDeliveredOrdersForThisMonth;
    private Integer sumOfDeletedOrdersForThisMonth;
    private Integer sumOfAmountForDeletedOrdersForThisMonth;

    public StatusOrderReport(LocalDate when, Integer sumOfActiveOrdersForThisMonth, Integer sumOfAmountForActiveOrdersForThisMonth, Integer sumOfDeliveredOrdersForThisMonth, Integer sumOfAmountForDeliveredOrdersForThisMonth, Integer sumOfDeletedOrdersForThisMonth, Integer sumOfAmountForDeletedOrdersForThisMonth) {
        this.when = when;
        this.sumOfActiveOrdersForThisMonth = sumOfActiveOrdersForThisMonth;
        this.sumOfAmountForActiveOrdersForThisMonth = sumOfAmountForActiveOrdersForThisMonth;
        this.sumOfDeliveredOrdersForThisMonth = sumOfDeliveredOrdersForThisMonth;
        this.sumOfAmountForDeliveredOrdersForThisMonth = sumOfAmountForDeliveredOrdersForThisMonth;
        this.sumOfDeletedOrdersForThisMonth = sumOfDeletedOrdersForThisMonth;
        this.sumOfAmountForDeletedOrdersForThisMonth = sumOfAmountForDeletedOrdersForThisMonth;
    }

    public LocalDate getWhen() {
        return when;
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
}
