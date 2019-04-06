package webshop.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.product.Product;
import webshop.product.ProductStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    public static final String USER_ID = "user_id";
    public static final String STATUS = "status";
    public static final String SHIPPING_ADDRESS = "shipping_address";
    public static final String ORDER_ID = "order_id";

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insertIntoOrdersFromBasketsByUserId(long userId, String shippingAddress) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into orders set user_id = ?, order_time = ?, " +
                                                            "status = ?, shipping_address = ?",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, userId);
                                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                                    ps.setString(3, OrderStatus.ACTIVE.name());
                                    ps.setString(4, shippingAddress);
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public void insertIntoOrderedItemsFromBasketItemsByOrderId(long orderId, long productId, int quantity, long totalPrice) {
        jdbcTemplate.update("insert into ordered_items set order_id = ?, product_id = ?, quantity = ?, order_price = ?", orderId,
                productId, quantity, totalPrice);
    }

    public List<Order> listOrdersByUserId(long userId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query("SELECT id, user_id, order_time, status, " +
                "shipping_address, (SELECT SUM(order_price) from ordered_items WHERE orders.id = ordered_items.order_id) " +
                "total_price, shipping_address from orders where user_id = (:user_id) ORDER BY order_time DESC;", Map.of(USER_ID,
                userId), ORDER_ROW_MAPPER);
    }

    public List<Order> listAllOrders() {
        return jdbcTemplate.query("SELECT id, user_id, order_time, status, shipping_address, (SELECT SUM(order_price) from " +
                        "ordered_items WHERE orders.id = ordered_items.order_id) total_price, " +
                        "shipping_address from orders ORDER BY order_time DESC;",
                ORDER_ROW_MAPPER);
    }

    private static final RowMapper<Order> ORDER_ROW_MAPPER = (resultSet, i) -> {

        long orderId = resultSet.getLong("id");
        long userId = resultSet.getLong(USER_ID);
        LocalDateTime orderTime = resultSet.getTimestamp("order_time").toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(STATUS));
        long totalOrderPrice = resultSet.getLong("total_price");
        String shippingAddress = resultSet.getString(SHIPPING_ADDRESS);

        return new Order(orderId, userId, orderTime, orderStatus, totalOrderPrice, shippingAddress);
    };

    private static final RowMapper<Order> ORDER_ROW_MAPPER_WITH_SHIPPING_ADDRESS_ONLY = (resultSet, i) -> {

        String shippingAddress = resultSet.getString(SHIPPING_ADDRESS);

        return new Order(0, 0, null, null, 0, shippingAddress);
    };

    private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (resultSet, i) -> {
        String priceString = resultSet.getString("order_price").split(" ")[0];

        long productId = resultSet.getLong("id");
        String productCode = resultSet.getString("code");
        String productName = resultSet.getString("name");
        String productManufacturer = resultSet.getString("manufacturer");
        int productOrderPrice = Integer.parseInt(priceString);
        ProductStatus productStatus = ProductStatus.valueOf(resultSet.getString(STATUS));
        int pieces = resultSet.getInt("quantity");
        return new OrderItem((new Product(productId, productCode, productName, productManufacturer, productOrderPrice, productStatus)),
                pieces);
    };


    private static final RowMapper<OrderData> ORDER_DATA_ROW_MAPPER = (resultSet, i) -> {
        long orderId = resultSet.getLong(ORDER_ID);
        String username = resultSet.getString("username");
        LocalDateTime orderTime = resultSet.getTimestamp("order_time").toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(STATUS));
        long sumOrderPrice = resultSet.getLong("sum_price");
        String shippingAddress = resultSet.getString(SHIPPING_ADDRESS);
        int sumOrderPieces = resultSet.getInt("sum_pieces");
        return new OrderData(orderId, username, orderTime, orderStatus, shippingAddress, sumOrderPrice, sumOrderPieces);
    };


    public List<OrderData> listAllOrderData() {
        return jdbcTemplate.query("SELECT orders.id order_id, username, order_time, status, shipping_address, SUM(order_price) " +
                "sum_price, " +
                "SUM(quantity) sum_pieces FROM orders JOIN users ON orders.user_id = users.id JOIN ordered_items ON order_id = " +
                "orders.id GROUP BY orders.id, username, order_time, status ORDER BY orders.order_time DESC", ORDER_DATA_ROW_MAPPER);
    }

    public List<OrderItem> listOrderItemsByOrderId(long orderId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query(
                "SELECT id, code, name, manufacturer, order_price, status, quantity FROM ordered_items JOIN" +
                        " products ON ordered_items" +
                        ".product_id = products.id where order_id = (:order_id) ORDER BY name", Map.of(ORDER_ID, orderId),
                ORDER_ITEM_ROW_MAPPER);
    }


    public int logicalDeleteOrderByOrderId(long orderId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update("UPDATE orders SET status = 'DELETED' where " +
                "id = (:order_id);", Map.of(ORDER_ID, orderId));
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
                Map.of(ORDER_ID, orderId, "product_address", productAddress));
    }

    public List<OrderData> listFilteredOrderData(String filter) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query("SELECT orders.id order_id, username, " +
                        "order_time, status, shipping_address, SUM(order_price) sum_price, SUM(quantity) sum_pieces FROM orders" +
                        " " +
                        "JOIN users ON " +
                        "orders.user_id = users.id JOIN ordered_items ON order_id = orders.id WHERE status = (:status) GROUP BY orders.id, username, " +
                        "order_time, status ORDER BY orders.order_time DESC", Map.of(STATUS, filter),
                ORDER_DATA_ROW_MAPPER);
    }

    public OrderStatus getOrderStatusByOrderId(long orderId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject("SELECT status FROM orders WHERE id " +
                "= (:id)", Map.of("id", orderId), (resultSet, i) -> OrderStatus.valueOf(resultSet.getString(STATUS)));
    }

    public int updateOrderStatus(long orderId, String newOrderStatus) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update("UPDATE orders SET status = (:new_status) " +
                "where id = (:order_id);", Map.of(ORDER_ID, orderId, "new_status", newOrderStatus));
    }

    public List<Order> getOrderListByUserIdWithFormerShippingAddressesOnly(long userId) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).query(
                "SELECT DISTINCT TRIM(shipping_address) shipping_address FROM orders where user_id = (:user_id) AND shipping_address IS NOT " +
                        "NULL AND shipping_address <>'' ORDER BY shipping_address", Map.of(USER_ID,
                        userId),
                ORDER_ROW_MAPPER_WITH_SHIPPING_ADDRESS_ONLY);
    }
}