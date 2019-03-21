package webshop.basket;

        import webshop.product.Product;

public class BasketItem {

    private Product product;
    private int pieces;


    // pieces default 1 for now - explicitly given here for Spring/Jackson
    public BasketItem(Product product, int pieces) {
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
