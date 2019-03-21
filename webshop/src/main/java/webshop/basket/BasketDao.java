package webshop.basket;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    public BasketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> getAllBasketOwnerIds() {
        return jdbcTemplate.query("select user_id from baskets",
                (resultSet, i) -> resultSet.getLong("user_id"));
    }


    public long createBasketForUserIdAndReturnBasketId(long userId) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into baskets" +
                                                            "(user_id) values (?)",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, userId);
                                    return ps;
                                }
                            }, keyHolder
        );

        return keyHolder.getKey().longValue();
    }




}
