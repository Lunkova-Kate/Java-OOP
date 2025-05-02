package factoryCar.threads.another;

import factoryCar.model.details.Car;
import factoryCar.model.storage.CarStorage;
import factoryCar.utilityForCar.Logger;
import factoryCar.threads.StoppableThread;

public class Dealer extends StoppableThread {
    private final long id;
    private final CarStorage carStorage;
    private volatile int delay;
    private final Logger logger;
    private static int nextId = 1;

    public Dealer(CarStorage carStorage, int delay, boolean logSale) {
        this.id = nextId++;
        this.carStorage = carStorage;
        this.delay = delay;
        this.logger = new Logger(logSale, "sales.txt");
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(delay);

                Car car = carStorage.take();  // Блокируется, если машин нет

                logger.logSale(getId(), car);
            }
        } catch (InterruptedException e) {
            // Завершаем работу при прерывании
        } finally {
            logger.close();
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


    public long getId() {
        return id;
    }
}