package webshop.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.basket.BasketItem;
import webshop.product.Product;
import webshop.product.ProductStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insertIntoOrdersFromBasketsByUserId(long userId, long totalOrder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into orders set user_id = ?, order_time = ?, status = ?, total_order = ?",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, userId);
                                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                                    ps.setString(3, OrderStatus.ACTIVE.name());
                                    ps.setLong(4, totalOrder);
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public void insertIntoOrderedItemsFromBasketItemsByOrderId(long orderId, long productId, long totalPrice) {
        jdbcTemplate.update("insert into ordered_items set order_id = ?, product_id = ?, order_price = ?", orderId, productId, totalPrice);
    }

    public List<Order> listOrdersByUserId(long userId) {
        return jdbcTemplate.query("SELECT orders.id, orders.user_id, orders.order_time, orders.status order_status, products" +
                ".id, code, name, address, manufacturer,price, products.status product_status, orders.total_order FROM products JOIN " +
                "ordered_items ON products.id = ordered_items.product_id JOIN orders ON orders.id = ordered_items.order_id " +
                "WHERE orders.user_id = ? order by orders.order_time", new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet resultSet, int i) throws SQLException {
                String price = resultSet.getString("price");
                price = price.split(" ")[0];
                return new Order(
                        resultSet.getLong("orders.id"),
                        resultSet.getLong("user_id"),
                        resultSet.getTimestamp("orders.order_time").toLocalDateTime(),
                        OrderStatus.valueOf(resultSet.getString("order_status")),
                        resultSet.getLong("total_order"),
                        List.of(new OrderItem(new Product(
                                Long.parseLong(resultSet.getString("id")),
                                resultSet.getString("code"),
                                resultSet.getString("name"),
                                resultSet.getString("manufacturer"),
                                Integer.parseInt(price),
                                ProductStatus.valueOf(resultSet.getString("product_status"))),
                                1
                        )));
            }
        }, userId);
    }


    private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (resultSet, i) -> {
        String priceString = resultSet.getString("order_price").split(" ")[0];

        long productId = resultSet.getLong("id");
        String productCode = resultSet.getString("code");
        String productName = resultSet.getString("name");
        String productManufacturer = resultSet.getString("manufacturer");
        int productOrderPrice = Integer.parseInt(priceString);
        ProductStatus productStatus = ProductStatus.valueOf(resultSet.getString("status"));
        int pieces = 1;
        return new OrderItem((new Product(productId, productCode, productName, productManufacturer, productOrderPrice, productStatus)),
                pieces);
    };


    private static final RowMapper<OrderData> ORDER_DATA_ROW_MAPPER = (resultSet, i) -> {
        long orderId = resultSet.getLong("order_id");
        String username = resultSet.getString("username");
        LocalDateTime orderTime = resultSet.getTimestamp("order_time").toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("status"));
        long sumOrderPrice = resultSet.getLong("sum_price");
        int sumOrderPieces = resultSet.getInt("sum_pieces");
        return new OrderData(orderId, username, orderTime, orderStatus, sumOrderPrice, sumOrderPieces);
    };


    public List<OrderData> listAllOrderData() {
        return jdbcTemplate.query("SELECT orders.id order_id, username, order_time, status, SUM(order_price) sum_price, COUNT(orders.id) sum_pieces FROM orders JOIN users ON orders.user_id = users.id JOIN ordered_items ON order_id = orders.id GROUP BY orders.id, username, order_time, status ORDER BY orders.order_time DESC", ORDER_DATA_ROW_MAPPER);
    }

    public List<OrderItem> listOrderItemsByOrderId(long orderId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query(
                "SELECT id, code, name, manufacturer, order_price, status FROM ordered_items JOIN products ON ordered_items" +
                        ".product_id = products.id where order_id = (:order_id) ORDER BY name", Map.of("order_id", orderId),
                ORDER_ITEM_ROW_MAPPER);

    }


    public int logicalDeleteOrderByOrderId(long orderId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update("UPDATE orders SET status = 'DELETED' where " +
                "id = (:order_id);", Map.of("order_id", orderId));
    }

    public int countActiveOrders() {
        return jdbcTemplate.queryForObject("SELECT count(id) FROM `orders` WHERE status = 'ACTIVE'", (rs, i) -> rs.getInt("count(id)"));
    }

    public int countAllOrders() {
        return jdbcTemplate.queryForObject("SELECT count(id) FROM `orders`", (rs, i) -> rs.getInt("count(id)"));
    }

    public int deleteItemFromOrderByProductAddress(long orderId, String productAddress) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update("DELETE FROM ordered_items where order_id = " +
                        "(:order_id) AND product_id = (SELECT id FROM products WHERE address = (:product_address));",
                Map.of("order_id", orderId, "product_address", productAddress));
    }

    public List<OrderData> listFilteredOrderData(String filter) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query("SELECT orders.id order_id, username, " +
                        "order_time, status, SUM(order_price) sum_price, COUNT(orders.id) sum_pieces FROM orders JOIN users ON " +
                        "orders.user_id = users.id JOIN ordered_items ON order_id = orders.id WHERE status = (:status) GROUP BY orders.id, username, " +
                        "order_time, status ORDER BY orders.order_time DESC", Map.of("status", filter),
                ORDER_DATA_ROW_MAPPER);
    }

    public OrderStatus getOrderStatusByOrderId(long orderId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject("SELECT status FROM orders WHERE id = (:id)", Map.of("id", orderId), (resultSet, i) -> OrderStatus.valueOf(resultSet.getString("status")));
    }

    public int updateOrderStatus(long orderId, String newOrderStatus) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update("UPDATE orders SET status = (:new_status) " +
                "where id = (:order_id);", Map.of("order_id", orderId, "new_status", newOrderStatus));
    }
}