Stock Exchange Order Matching System

This project implements a Stock Exchange Order Matching System in Java, designed to process buy and sell orders efficiently using a linked list-based order book. It uses multi-threading for simulating transactions and atomic operations for safe concurrent updates.

Features:
1.Supports Buy and Sell orders with price and quantity matching.
2.Implements an Order Book that maintains separate buy and sell queues.
3.Uses atomic references to ensure thread-safe operations.
4.Matches orders based on price-time priority.
5.Supports multi-threaded order placement and matching.
6.Implements an event-driven matching engine to execute trades automatically.

How It Works

1.A user submits a buy or sell order.
2.Orders are sorted in the OrderBook (highest buy prices at the top, lowest sell prices at the top).
3.The matching engine checks if a buy order's price is greater than or equal to the lowest sell order price.
4.If a match is found, a trade is executed, and order quantities are updated.
5.The system continuously processes orders in a multi-threaded environment.

Technologies Used
-Java 17
-Multithreading (ExecutorService)
-Atomic Variables (AtomicReference, AtomicInteger)
-Data Structures (Linked List for Order Storage)
-OOP Principles (Encapsulation, Inheritance, Polymorphism)
