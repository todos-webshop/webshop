package webshop.order;

import java.time.LocalDateTime;

public class OrderData {

    private long orderId;
    private String username;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private long sumOrderPrice;
    private int sumOrderPieces;

    public OrderData(long orderId, String username, LocalDateTime orderTime, OrderStatus orderStatus, String shippingAddress, long sumOrderPrice, int sumOrderPieces) {
        this.orderId = orderId;
        this.username = username;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
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

    public String getShippingAddress() {
        return shippingAddress;
    }
}
