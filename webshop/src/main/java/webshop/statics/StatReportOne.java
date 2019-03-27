package webshop.statics;

import java.util.ArrayList;
import java.util.List;

public class StatReportOne {
    private List<StatusOrderReport> statusOrderReportList = new ArrayList<>();
    private StatRowSummary statRowSummary;

    public StatReportOne(List<StatusOrderReport> statusOrderReportList, StatRowSummary statRowSummary) {
        this.statusOrderReportList = statusOrderReportList;
        this.statRowSummary = statRowSummary;
    }

    public List<StatusOrderReport> getStatusOrderReportList() {
        return statusOrderReportList;
    }

    public StatRowSummary getStatRowSummary() {
        return statRowSummary;
    }
}
