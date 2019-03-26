package webshop.statics;

import webshop.order.OrderStatus;

public class StatData {
    private int year;
    private int month;
    private OrderStatus orderStatus;
    private int piece;
    private int amount;

    public StatData(int year, int month, OrderStatus orderStatus, int piece, int amount) {
        this.year = year;
        this.month = month;
        this.orderStatus = orderStatus;
        this.piece = piece;
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public int getPiece() {
        return piece;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "StatData{" +
                "year=" + year +
                ", month=" + month +
                ", orderStatus=" + orderStatus +
                ", piece=" + piece +
                ", amount=" + amount +
                '}';
    }
}
