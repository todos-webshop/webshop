package webshop.statics;

public class StatRowSummary {
     private int actPiece;
    private long actAmount;
    private int deliPiece;
    private long deliAmount;
    private int delPiece;
    private long delAmount;

    public void setActPiece(int actPiece) {
        this.actPiece = actPiece;
    }

    public void setActAmount(long actAmount) {
        this.actAmount = actAmount;
    }

    public void setDeliPiece(int deliPiece) {
        this.deliPiece = deliPiece;
    }

    public void setDeliAmount(long deliAmount) {
        this.deliAmount = deliAmount;
    }

    public void setDelPiece(int delPiece) {
        this.delPiece = delPiece;
    }

    public void setDelAmount(long delAmount) {
        this.delAmount = delAmount;
    }

    public int getActPiece() {
        return actPiece;
    }

    public long getActAmount() {
        return actAmount;
    }

    public int getDeliPiece() {
        return deliPiece;
    }

    public long getDeliAmount() {
        return deliAmount;
    }

    public int getDelPiece() {
        return delPiece;
    }

    public long getDelAmount() {
        return delAmount;
    }
}
