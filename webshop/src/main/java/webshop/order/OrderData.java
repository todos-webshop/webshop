package webshop.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderData {

    private long orderId;
    private String username;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private long sumOrderPrice;
    private int sumOrderPieces;

    public OrderData(long orderId, String username, LocalDateTime orderTime, OrderStatus orderStatus, long sumOrderPrice, int sumOrderPieces) {
        this.orderId = orderId;
        this.username = username;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.sumOrderPrice = sumOrderPrice;
        this.sumOrderPieces = sumOrderPieces;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public long getSumOrderPrice() {
        return sumOrderPrice;
    }

    public int getSumOrderPieces() {
        return sumOrderPieces;
    }
}
