package OrderStorage;

import Orders.Order;

public class OrderNode {

    Order order;
    volatile OrderNode next;

    public OrderNode(Order order) {
        this.order = order;
        this.next = null;
    }
}
