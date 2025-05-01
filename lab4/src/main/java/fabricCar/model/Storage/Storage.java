package fabricCar.model.Storage;

import fabricCar.view.StorageObserver;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public abstract class Storage<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();
    private final Object lock = new Object();
    private final List<StorageObserver> observers = new ArrayList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public void addObserver(StorageObserver observer) {
        synchronized (lock) {
            observers.add(observer);
        }
    }

    public void removeObserver(StorageObserver observer) {
        synchronized (lock) {
            observers.remove(observer);
        }
    }

    protected void notifyCountChanged(int count) {
        synchronized (lock) {
            for (StorageObserver observer : observers) {
                if (this instanceof BodyStorage) {
                    observer.onBodyCountChanged(count);
                } else if (this instanceof EngineStorage) {
                    observer.onEngineCountChanged(count);
                } else if (this instanceof AccessoryStorage) {
                    observer.onAccessoryCountChanged(count);
                } else if (this instanceof CarStorage) {
                    observer.onCarCountChanged(count);
                }
            }
        }
    }

    public void add(T item) throws InterruptedException {
        synchronized (lock) {
            while (items.size() >= capacity) {
                lock.wait();
            }
            items.add(item);
            notifyCountChanged(items.size());
            lock.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (lock) {
            while (items.isEmpty()) {
                lock.wait();
            }
            T item = items.poll();
            notifyCountChanged(items.size());
            lock.notifyAll();
            return item;
        }
    }

    public int getSize() {
        synchronized (lock) {
            return items.size();
        }
    }
}