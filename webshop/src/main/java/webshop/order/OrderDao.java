package webshop.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getItemsFromBasketAndAddToMyOrders(long userId){
        List<OrderData> orderData = jdbcTemplate.query("select id, user_id from baskets where id = ?", new RowMapper<OrderData>() {
            @Override
            public OrderData mapRow(ResultSet resultSet, int i) throws SQLException {
                return new OrderData(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"));
            }
        }, userId);
        if (orderData.size() == 0){
            return 0;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into orders set user_id = ?, order_time = ?, status = ?",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, userId);
                                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                                    ps.setString(3, OrderStatus.ORDERED.name());
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }
}
