package homework3.task6;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StockMarketImpl implements StockMarket {
    private PriorityQueue<Stock> stockQueue;

    public StockMarketImpl() {
        stockQueue = new PriorityQueue<>(Comparator.comparingDouble(Stock::getPrice).reversed());
    }

    @Override
    public void add(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("null");
        }
        stockQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("null");
        }
        stockQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        if (stockQueue.isEmpty()) {
            throw new IllegalStateException("Нет доступных акций");
        }
        return stockQueue.peek();
    }
}