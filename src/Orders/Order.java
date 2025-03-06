package Orders;

public abstract class Order {

    String tickerSymbol;
    int quantity;
    float price;

    public Order(String tickerSymbol, int quantity, float price) {
        this.tickerSymbol = tickerSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public abstract boolean checkSortOrder();
}
