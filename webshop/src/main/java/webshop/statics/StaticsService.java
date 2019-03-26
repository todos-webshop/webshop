package webshop.statics;

import org.springframework.stereotype.Service;
import webshop.order.OrderDao;
import webshop.order.OrderStatus;
import webshop.product.ProductDao;
import webshop.user.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaticsService {

    private UserDao userDao;
    private ProductDao productDao;
    private OrderDao orderDao;
    private StaticsDao staticsDao;

    public StaticsService(UserDao userDao, ProductDao productDao, OrderDao orderDao, StaticsDao staticsDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.staticsDao = staticsDao;
    }

    public Stat getStat() {
        int users = userDao.countAllUsers();
        int countProducts = productDao.countAllProducts();
        int activeProducts = productDao.countActiveProducts();
        int activeOrders = orderDao.countActiveOrders();
        int countOrders = orderDao.countAllOrders();
        return new Stat(users, activeProducts, countProducts, activeOrders, countOrders);
    }

    public List<StatusOrderReport> doReportOne() {
        List<StatData> statDatas = staticsDao.doReportOne();
        System.out.println(statDatas.toString());
        List<StatusOrderReport> statusOrderReports = new ArrayList<>();
        for (StatData statData : statDatas) {
            StatusOrderReport actReport = findYearMonth(statData, statusOrderReports);
            if (actReport == null) {
                StatusOrderReport newReport = new StatusOrderReport(statData.getYear(), statData.getMonth(), null,
                        null, null, null, null, null);
                addStatus(statData, newReport);
                statusOrderReports.add(newReport);
            } else {
                addStatus(statData, actReport);

            }
        }
        removeNull(statusOrderReports);
        return statusOrderReports;
    }


    private StatusOrderReport findYearMonth(StatData statdata, List<StatusOrderReport> statusOrderReports) {
        for (StatusOrderReport statusOrderReport : statusOrderReports) {
            if (statusOrderReport.getYear() == statdata.getYear() && statusOrderReport.getMonth() == statdata.getMonth()) {
                return statusOrderReport;
            }
        }

        return null;
    }

    private void addStatus(StatData statData, StatusOrderReport statusOrderReport) {
        System.out.println("statdata"+statData.toString());
        System.out.println("statdatareport"+statusOrderReport.toString());
        switch (statData.getOrderStatus()) {
            case ACTIVE: {
                statusOrderReport.setSumOfAmountForActiveOrdersForThisMonth(statData.getAmount());
                statusOrderReport.setSumOfActiveOrdersForThisMonth(statData.getPiece());
            }
            ;
            case DELIVERED: {
                statusOrderReport.setSumOfAmountForDeliveredOrdersForThisMonth(statData.getAmount());
                statusOrderReport.setSumOfDeliveredOrdersForThisMonth(statData.getPiece());
            }
            ;
            case DELETED: {
                statusOrderReport.setSumOfAmountForDeletedOrdersForThisMonth(statData.getAmount());
                statusOrderReport.setSumOfDeletedOrdersForThisMonth(statData.getPiece());
            }
            ;
        }
    }

    private void removeNull(List<StatusOrderReport> statusOrderReports) {
        for (StatusOrderReport statusOrderReport : statusOrderReports) {
            if (statusOrderReport.getSumOfActiveOrdersForThisMonth() == null) {
                statusOrderReport.setSumOfActiveOrdersForThisMonth(0);
            }
            if (statusOrderReport.getSumOfDeliveredOrdersForThisMonth() == null) {
                statusOrderReport.setSumOfDeliveredOrdersForThisMonth(0);
            }
            if (statusOrderReport.getSumOfDeletedOrdersForThisMonth() == null) {
                statusOrderReport.setSumOfDeletedOrdersForThisMonth(0);
            }
            if (statusOrderReport.getSumOfAmountForActiveOrdersForThisMonth() == null) {
                statusOrderReport.setSumOfAmountForActiveOrdersForThisMonth(0);
            }
            if (statusOrderReport.getSumOfAmountForDeliveredOrdersForThisMonth() == null) {
                statusOrderReport.setSumOfAmountForDeliveredOrdersForThisMonth(0);
            }
            if (statusOrderReport.getSumOfAmountForDeletedOrdersForThisMonth() == null) {
                statusOrderReport.setSumOfAmountForDeletedOrdersForThisMonth(0);
            }

        }
    }

    public List<StatSummary> doReportOneSummary() {
        return staticsDao.doReportOneSummary();

}




}
