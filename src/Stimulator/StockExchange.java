package Stimulator;

import OrderStorage.OrderBook;
import Orders.BuyOrder;
import Orders.Order;
import Orders.SellOrder;

import java.util.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockExchange {

    public OrderBook orderBook;
    public ExecutorService executor = Executors.newFixedThreadPool(6);
    private Random random = new Random();
    private long startTime;
    List<String> tickerSymbolList = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "NFLX", "FB", "NVDA", "AMD", "INTC");

    public StockExchange() {
        this.orderBook = new OrderBook();
        this.startTime = System.currentTimeMillis();
    }


    public void simulateTranscations(int numOfOrders){
        for (int i = 0; i < numOfOrders; i++) {
            executor.submit(() -> {
                boolean isBuy = random.nextBoolean();
                int quantity = random.nextInt(100);
                float price = random.nextFloat() * 100;
                String ticker = tickerSymbolList.get(random.nextInt(tickerSymbolList.size()));
                Order order = isBuy ? new BuyOrder(ticker , quantity, price) : new SellOrder(ticker , quantity, price);
                orderBook.addOrder(order);
            });
        }

    }

    public void startMatching() {
        new Thread(() -> {
            while (orderBook.headBuyOrder.get() != null && orderBook.headSellOrder.get() != null) {
                //System.out.println("Enter start Match");
                orderBook.matchOrder();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void calculateTranscations(){
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("\nTotal time: " + totalTime + " ms");
        System.out.println("Total no of Orders Place: " + orderBook.totalOrderPlaced.get());
        System.out.println("Total no of Orders Delivered: " + orderBook.totalOrderDelivered.get());
    }
}
