package webshop.order;

import webshop.product.Product;

public class OrderItem {

    private Product product;
    private int pieces;

    public OrderItem (Product product, int pieces) {
        this.product = product;
        this.pieces = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getPieces() {
        return pieces;
    }

}
