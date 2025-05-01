package factoryCar.threads.supplier;

import factoryCar.model.storage.Storage;
import factoryCar.threads.StoppableThread;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Supplier<T> extends StoppableThread {
    protected final Storage<T> storage;
    protected final int id;
    protected int delay;
    private static final AtomicInteger nextId = new AtomicInteger(0);

    public Supplier(Storage<T> storage, int delay) {
        this.storage = storage;
        this.id = nextId.incrementAndGet();
        this.delay = delay;
    }

    @Override
    public void run() {
        System.out.println(getClass().getSimpleName() + " " + id + " started");
        try {
            while (running) {
                Thread.sleep(delay);
                T item = createItem();
                storage.add(item);
                System.out.println(getClass().getSimpleName() + " " + id +
                        " added item " + item);
            }
        } catch (InterruptedException e) {
            System.out.println(getClass().getSimpleName() + " " + id + " interrupted");
        } finally {
            System.out.println(getClass().getSimpleName() + " " + id + " stopped");
        }
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }

    protected abstract T createItem();
}