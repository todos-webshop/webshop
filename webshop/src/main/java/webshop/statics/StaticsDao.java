package webshop.statics;

import org.springframework.jdbc.core.JdbcTemplate;


import org.springframework.stereotype.Repository;
import webshop.order.OrderStatus;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StaticsDao {
    private JdbcTemplate jdbcTemplate;

    public StaticsDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<StatData> doReportOne() {
        return jdbcTemplate.query(" select year(order_time), month(order_time), status, count(distinct order_id), sum(ordered_items.order_price*ordered_items.quantity)" +
                        "    from orders join ordered_items on ordered_items.order_id=orders.id" +
                        "     group by  year(order_time), month(order_time)  , STATUS  order by year(order_time), month(order_time) , status"
                          ,
                (rs,rowNum)-> new StatData(rs.getInt(1),rs.getInt(2), OrderStatus.valueOf(rs.getString(3)),rs.getInt(4),rs.getInt(5)));

    }



    public List<StatSummary> doReportOneSummary() {
        return jdbcTemplate.query("select  status, count(distinct order_id), sum(ordered_items.order_price*ordered_items.quantity)"+
                        "from orders join ordered_items on ordered_items.order_id=orders.id group by   STATUS order by  status",
                (rs,rowNum)-> new StatSummary( OrderStatus.valueOf(rs.getString(1)),rs.getInt(2),rs.getLong(3)));

    }


    public List<StatByProduct> doReportTwo() {
        return jdbcTemplate.query( "select year(orders.order_time), \n" +
         "month(orders.order_time), \n" +
         "products.name, ordered_items.order_price, \n" +
         "ordered_items.quantity, \n" +
         "ordered_items.order_price * ordered_items.quantity \n" +
         "from products join ordered_items on products.id=ordered_items.product_id   join orders on ordered_items.order_id= orders.id \n" +
         " where orders.status ='DELIVERED'\n"+
         "group by year(orders.order_time), month(orders.order_time), \n" +
         "products.name order by year(orders.order_time),\n" +
         "month(orders.order_time), products.name\n",
                (rs,rowNum)-> new StatByProduct( rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));

    }
}
