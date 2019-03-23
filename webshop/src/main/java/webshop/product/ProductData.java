package webshop.product;

public class ProductData {

    private String productCode;
    private int productPieces;

    // only one piece per product for now
    public ProductData(String productCode, int productPieces) {
        this.productCode = productCode;
        this.productPieces = 1;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getProductPieces() {
        return productPieces;
    }
}