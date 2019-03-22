package webshop.basket;

public class BasketData {

    private int sumPieces;
    private int sumPrice;
    private Basket basket;

    public BasketData(int sumPieces, int sumPrice, Basket basket) {
        this.sumPieces = sumPieces;
        this.sumPrice = sumPrice;
        this.basket = basket;
    }

    public int getSumPieces() {
        return sumPieces;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public Basket getBasket() {
        return basket;
    }
}
