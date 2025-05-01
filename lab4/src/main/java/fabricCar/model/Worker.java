package fabricCar.model;

import fabricCar.model.Storage.AccessoryStorage;
import fabricCar.model.Storage.BodyStorage;
import fabricCar.model.Storage.CarStorage;
import fabricCar.model.Storage.EngineStorage;
import fabricCar.model.details.Accessory;
import fabricCar.model.details.Body;
import fabricCar.model.details.Engine;


public class Worker implements Runnable {
    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final CarStorage carStorage;
    private static int nextId = 0;

    public Worker(BodyStorage bodyStorage, EngineStorage engineStorage,
                  AccessoryStorage accessoryStorage, CarStorage carStorage) {
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {
            Body body = bodyStorage.take();
            Engine engine = engineStorage.take();
            Accessory accessory = accessoryStorage.take();

            Car car = new Car(nextId++, body, engine, accessory);
            carStorage.add(car);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
