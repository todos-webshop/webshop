package webshop.statics;

import org.springframework.stereotype.Service;
import webshop.order.OrderDao;
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


        List<StatusOrderReport> statusOrderReports = new ArrayList<>();
        for (StatData statData : statDatas) {
            StatusOrderReport actReport = findYearMonth(statData, statusOrderReports);
            if (actReport == null) {
                StatusOrderReport newReport = new StatusOrderReport(statData.getYear(), statData.getMonth());

                StatusOrderReport changedReport=addStatus(statData, newReport);
                statusOrderReports.add(changedReport);
            } else {
                int index = findStatusOrderReportInList (actReport, statusOrderReports);
                statusOrderReports.set(index,addStatus(statData,statusOrderReports.get(index)));
            }
        }

        removeNull(statusOrderReports);

        return statusOrderReports;
    }

    private int findStatusOrderReportInList (StatusOrderReport statusOrderReport,List<StatusOrderReport> statusOrderReports){
        for (int i=0 ; i< statusOrderReports.size();i++){
            if (statusOrderReport.getYear() == statusOrderReports.get(i).getYear() && statusOrderReport.getMonth()==statusOrderReports.get(i).getMonth()){
                return i;
            }
        }
        return -1;
    }

    private StatusOrderReport findYearMonth(StatData statdata, List<StatusOrderReport> statusOrderReports) {
        for (StatusOrderReport statusOrderReport : statusOrderReports) {
            if (statusOrderReport.getYear() == statdata.getYear() && statusOrderReport.getMonth() == statdata.getMonth()) {
                return statusOrderReport;
            }
        }
        return null;
    }

    private StatusOrderReport addStatus(StatData statData, StatusOrderReport statusOrderReport) {

        switch (statData.getOrderStatus()) {
            case ACTIVE: {
                statusOrderReport.setSumOfAmountForActiveOrdersForThisMonth(statData.getAmount());
                statusOrderReport.setSumOfActiveOrdersForThisMonth(statData.getPiece());
                break;
            }

            case DELIVERED: {
                statusOrderReport.setSumOfAmountForDeliveredOrdersForThisMonth(statData.getAmount());
                statusOrderReport.setSumOfDeliveredOrdersForThisMonth(statData.getPiece());
                break;
            }

            case DELETED: {
                statusOrderReport.setSumOfAmountForDeletedOrdersForThisMonth(statData.getAmount());
                statusOrderReport.setSumOfDeletedOrdersForThisMonth(statData.getPiece());
                break;
            }
        }
        return statusOrderReport;
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

    public StatRowSummary doReportOneSummary() {
       List <StatSummary> statSummaries =staticsDao.doReportOneSummary();
        StatRowSummary statRowSummary = new StatRowSummary();
        for (StatSummary statSummary:statSummaries){
            switch(statSummary.getOrderStatus()){
                case ACTIVE:{
                    statRowSummary.setActPiece(statSummary.getPiece());
                    statRowSummary.setActAmount(statSummary.getAmount());
                }
                break;
                case DELIVERED:{
                    statRowSummary.setDeliPiece(statSummary.getPiece());
                    statRowSummary.setDeliAmount(statSummary.getAmount());
                }
                break;
                case DELETED:{
                    statRowSummary.setDelPiece(statSummary.getPiece());
                    statRowSummary.setDelAmount(statSummary.getAmount());
                }
                break;
                default:
            }
        }
        return statRowSummary;
}
     public List<StatByProduct> doReportTwo(){
        return staticsDao.doReportTwo();
     }




}
