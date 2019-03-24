package webshop.order;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long id;
    private long userId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private List<OrderItem> orderItems;

    public Order(long id, long userId, LocalDateTime orderTime, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
