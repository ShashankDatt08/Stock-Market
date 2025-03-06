package OrderStorage;

import Orders.BuyOrder;
import Orders.Order;
import Orders.SellOrder;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class OrderBook {

    public AtomicReference<OrderNode> headBuyOrder = new AtomicReference<>(null);
    public AtomicReference<OrderNode> headSellOrder = new AtomicReference<>(null);
    public AtomicInteger totalOrderPlaced = new AtomicInteger(0);
    public AtomicInteger totalOrderDelivered = new AtomicInteger(0);

    public void addOrder(Order order) {
        totalOrderPlaced.incrementAndGet();
        boolean getSortingOrder = order.checkSortOrder();
        AtomicReference<OrderNode> insertNode = order.checkSortOrder() ? headBuyOrder : headSellOrder;
        insertToNode(insertNode, order, !getSortingOrder);
    }

    //Buy price for a particular ticker is greater than or equal to lowest Sell price available then.
    public void matchOrder() {
//        System.out.println(headBuyOrder.get());
//        System.out.println(headSellOrder.get());
        while (headBuyOrder.get() != null && headSellOrder.get() != null) {
            OrderNode buyNode = headBuyOrder.get();
            OrderNode sellNode = headSellOrder.get();

            while (buyNode != null && sellNode != null) {
                if (((BuyOrder) buyNode.order).match((SellOrder) sellNode.order)) {
                    int tradeQuantity = Math.min(buyNode.order.getQuantity(), sellNode.order.getQuantity());
                    totalOrderDelivered.incrementAndGet();

                    reduceQuantity(tradeQuantity, buyNode);
                    reduceQuantity(tradeQuantity, sellNode);

                    if (buyNode.order.getQuantity() == 0) {
                        buyNode = buyNode.next;
                    }
                    if (sellNode.order.getQuantity() == 0) {
                        sellNode = sellNode.next;
                    }

                    headBuyOrder.set(buyNode);
                    headSellOrder.set(sellNode);
                } else {
                    if (buyNode.order.getPrice() < sellNode.order.getPrice()) {
                        buyNode = buyNode.next;
                    } else {
                        sellNode = sellNode.next;
                    }
                }
            }
        }
    }

    public void printOrderBook() {
        System.out.println("Buy Order: ");
        printOrder(headBuyOrder.get());
        System.out.println("\nSell Order: ");
        printOrder(headSellOrder.get());
    }

    private void insertToNode(AtomicReference<OrderNode> head, Order order, boolean isAscending) {
        OrderNode newOrderToInsert = new OrderNode(order);
        OrderNode insertCurrent;
        do {
            insertCurrent = head.get();
            if (insertCurrent == null || (isAscending ? order.getPrice() < insertCurrent.order.getPrice()
                    : order.getPrice() > insertCurrent.order.getPrice())) {
                newOrderToInsert.next = insertCurrent;
                if (head.compareAndSet(insertCurrent, newOrderToInsert)) {
                    return;
                }
            } else {
                OrderNode previous = insertCurrent;
                if (previous.next != null && (isAscending ? order.getPrice() >= previous.next.order.getPrice()
                        : order.getPrice() <= previous.next.order.getPrice())) {
                    previous = previous.next;
                }

                newOrderToInsert.next = previous.next;
                previous.next = newOrderToInsert;
                return;
            }
        } while (true);

    }

    private void reduceQuantity(int minQuantity, OrderNode head) {
        int newQuantity = head.order.getQuantity() - minQuantity;
        head.order.setQuantity(newQuantity);
    }

    private void printOrder(OrderNode head) {
        OrderNode currentHead = head;
        while (currentHead != null) {
            System.out.println("Stock: " + currentHead.order.getTickerSymbol() + " | " + "Quantity: " + currentHead.order.getQuantity() + " | " + "Price: " + currentHead.order.getPrice());
            currentHead = currentHead.next;
        }
    }

}
