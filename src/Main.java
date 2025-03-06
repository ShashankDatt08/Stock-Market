import Stimulator.StockExchange;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        StockExchange stockExchange = new StockExchange();
        stockExchange.simulateTranscations(1024);
        stockExchange.executor.shutdown();

        try {
            stockExchange.executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        stockExchange.startMatching();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stockExchange.executor.shutdown();
        try {
            stockExchange.executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stockExchange.orderBook.printOrderBook();
        stockExchange.calculateTranscations();
    }

}