package Orders;

public class BuyOrder extends Order {


    public BuyOrder(String tickerSymbol, int quantity, float price) {
        super(tickerSymbol, quantity, price);
    }

    @Override
    public boolean checkSortOrder() {
        return true;
    }

    public boolean match(SellOrder sellOrder) {
        return this.getPrice() >= sellOrder.getPrice();
    }
}
