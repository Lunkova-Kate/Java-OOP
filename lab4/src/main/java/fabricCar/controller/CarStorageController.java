package fabricCar.controller;

import fabricCar.ThreadPool;
import fabricCar.model.Car;
import fabricCar.model.Storage.*;
import fabricCar.model.details.Accessory;
import fabricCar.model.details.Body;
import fabricCar.model.details.Engine;

import fabricCar.model.Storage.CarStorage;
import fabricCar.model.Storage.BodyStorage;
import fabricCar.model.Storage.EngineStorage;
import fabricCar.model.Storage.AccessoryStorage;


public class CarStorageController {
    private final CarStorage carStorage;
    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final ThreadPool workers;
    private final int minThreshold;
    private final int maxThreshold;
    private int totalProduced = 0;
    private int totalSold = 0;

    public CarStorageController(CarStorage carStorage,
                                BodyStorage bodyStorage,
                                EngineStorage engineStorage,
                                AccessoryStorage accessoryStorage,
                                ThreadPool workers,
                                int minThreshold,
                                int maxThreshold) {
        this.carStorage = carStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.workers = workers;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;

        // Начальное заполнение склада
        while (carStorage.getSize() < minThreshold) {
            scheduleCarProduction();
        }
    }

    public synchronized void onCarSold() {
        totalSold++;
        if (carStorage.getSize() < minThreshold) {
            int needed = maxThreshold - carStorage.getSize();
            for (int i = 0; i < needed; i++) {
                scheduleCarProduction();
            }
        }
    }

    public synchronized void onCarProduced() {
        totalProduced++;
        if (carStorage.getSize() >= maxThreshold && !workers.isPaused()) {
            workers.pause();
        }
    }

    private void scheduleCarProduction() {
        workers.addTask(() -> {
            try {
                Body body = bodyStorage.take();
                Engine engine = engineStorage.take();
                Accessory accessory = accessoryStorage.take();
                Car car = new Car(totalProduced, body, engine, accessory);
                carStorage.add(car);
                onCarProduced();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public int getTotalProduced() {
        return totalProduced;
    }

    public int getTotalSold() {
        return totalSold;
    }
}