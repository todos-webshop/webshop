package webshop.basket;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.product.Product;
import webshop.product.ProductStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

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


    public long getBasketIdByUserId(long userId) {
        Long basketId =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject(
                        "SELECT id FROM baskets WHERE user_id = (:user_id)", Map.of("user_id", userId),
                        (rs, i) -> rs.getLong("id"));
        if (basketId != null) {
            return basketId;
        }
        return 0;
    }

    private static final RowMapper<BasketItem> BASKET_ITEM_ROW_MAPPER = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String code = resultSet.getString("code");
        String name = resultSet.getString("name");
        String manufacturer = resultSet.getString("manufacturer");
        int price = resultSet.getInt("price");
        int quantity = 1;
        ProductStatus productStatus = ProductStatus.valueOf(resultSet.getString("status"));
        return new BasketItem(new Product(id, code, name, manufacturer, price, productStatus),
                quantity);
    };

    public List<BasketItem> getBasketItemsInBasketByBasketId(long basketId) {

        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query(
                "SELECT id, code, name, manufacturer, price, status FROM basket_items " +
                        "JOIN products ON basket_items.product_id = products.id where basket_id =" +
                        " (:basket_id) ORDER BY name", Map.of("basket_id", basketId),
                BASKET_ITEM_ROW_MAPPER);

    }


    public int sumProductPiecesInBasketByBasketId(long basketId) {
        Integer sumProductPieces =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject(
                        "SELECT count(products.id) as sum_pieces FROM basket_items JOIN products " +
                                "ON " +
                                "basket_items.product_id = products.id where basket_id = " +
                                "(:basket_id)", Map.of("basket_id",
                                basketId),
                        (rs, i) -> rs.getInt("sum_pieces"));
        if (sumProductPieces != null) {
            return sumProductPieces;
        }
        return 0;
    }



    public int sumProductPriceInBasketByBasketId(long basketId) {
        Integer sumProductPrice =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject(
                        "SELECT sum(price) as sum_price FROM basket_items JOIN products ON " +
                                "basket_items.product_id = products.id where basket_id = " +
                                "(:basket_id)", Map.of("basket_id",
                                basketId),
                        (rs, i) -> rs.getInt("sum_price"));
        if (sumProductPrice != null) {
            return sumProductPrice;
        }
        return 0;
    }
}
