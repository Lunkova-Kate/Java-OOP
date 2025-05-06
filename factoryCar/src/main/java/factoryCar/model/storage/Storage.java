package factoryCar.model.storage;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Storage<T> extends Observable {
    private final BlockingQueue<T> queue;
    private final int maxCapacity;

    public Storage(int capacity) {
        this.queue = new LinkedBlockingQueue<>(capacity);
        this.maxCapacity = capacity;
    }

    public void add(T item) throws InterruptedException {
        synchronized(this) {
            while (queue.size() >= maxCapacity) {
                wait();
            }
            queue.put(item);
            setChanged();
            notifyObservers(queue.size());
            notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized(this) {
            while (queue.isEmpty()) {
                wait();
            }
            T item = queue.take();
            setChanged();
            notifyObservers(queue.size());
            notifyAll();
            return item;
        }
    }

    public int size() {
        return queue.size();
    }

    public int capacity() {
        return maxCapacity;
    }
}