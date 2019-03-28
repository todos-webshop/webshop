package webshop.basket;

        import webshop.product.Product;

public class BasketItem {

    private Product product;
    private int pieces;


    public BasketItem(Product product, int pieces) {
        this.product = product;
        this.pieces = pieces;
    }


    public Product getProduct() {
        return product;
    }

    public int getPieces() {
        return pieces;
    }

}
