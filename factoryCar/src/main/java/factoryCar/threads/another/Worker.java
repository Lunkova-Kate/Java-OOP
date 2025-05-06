package factoryCar.threads.another;

import factoryCar.model.details.*;
import factoryCar.model.storage.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Worker implements Runnable {
    private final int id;
    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final CarStorage carStorage;
    private static final AtomicInteger nextId = new AtomicInteger(1);

    public Worker(BodyStorage bodyStorage, EngineStorage engineStorage,
                  AccessoryStorage accessoryStorage, CarStorage carStorage) {
        this.id = nextId.getAndIncrement();
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {
            System.out.println("Worker " + id + " started assembling car");

            Body body = bodyStorage.take();
            Engine engine = engineStorage.take();
            Accessory accessory = accessoryStorage.take();

            System.out.println("Worker " + id + " got parts: Body-" + body.getId() +
                    ", Engine-" + engine.getId() +
                    ", Accessory-" + accessory.getId());

            Car car = new Car(body, engine, accessory);
            carStorage.add(car);

            System.out.println("Worker " + id + " assembled Car-" + car.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Worker " + id + " interrupted");
        }
    }
}