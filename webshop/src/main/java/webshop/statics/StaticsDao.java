package webshop.statics;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import webshop.order.OrderStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.text.Normalizer;
import java.util.List;
import java.util.Map;
@Repository

public class StaticsDao {
    private JdbcTemplate jdbcTemplate;

    public StaticsDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<StatData> doReportOne() {
        return jdbcTemplate.query("select year(order_time), month(order_time), status, count(*), sum(total_order) from orders"+
       " group by  year(order_time), month(order_time)  , STATUS  order by year(order_time), month(order_time) , status"
                ,
                (rs,rowNum)-> new StatData(rs.getInt(1),rs.getInt(2), OrderStatus.valueOf(rs.getString(3)),rs.getInt(4),rs.getInt(5)));

    }

    public List<StatSummary> doReportOneSummary() {
        return jdbcTemplate.query("select  status, count(*), sum(total_order) from orders group by   STATUS order by  status"
                ,
                (rs,rowNum)-> new StatSummary( OrderStatus.valueOf(rs.getString(1)),rs.getInt(2),rs.getLong(3)));

    }



}
