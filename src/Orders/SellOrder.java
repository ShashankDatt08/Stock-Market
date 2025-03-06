package Orders;

public class SellOrder extends Order {

    public SellOrder(String tickerSymbol, int quantity, float price) {
        super(tickerSymbol, quantity, price);
    }

    @Override
    public boolean checkSortOrder() {
        return false;
    }

    public boolean match(BuyOrder buyOrder) {
        return this.getPrice() <= buyOrder.getPrice();
   }
}
