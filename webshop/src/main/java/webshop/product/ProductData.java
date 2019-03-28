package webshop.product;

public class ProductData {

    private String productCode;
    private int productPieces;

    public ProductData(String productCode, int productPieces) {
        this.productCode = productCode;
        this.productPieces = productPieces;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getProductPieces() {
        return productPieces;
    }
}