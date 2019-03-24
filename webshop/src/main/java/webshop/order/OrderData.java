package webshop.order;

public class OrderData {
    private long id;
    private long userId;

    public OrderData(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }
}
