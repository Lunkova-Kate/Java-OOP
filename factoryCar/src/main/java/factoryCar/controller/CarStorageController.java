package factoryCar.controller;

import factoryCar.model.details.Accessory;
import factoryCar.model.details.Body;
import factoryCar.model.details.Car;
import factoryCar.model.details.Engine;
import factoryCar.model.storage.*;
import factoryCar.threads.ThreadPool;

public class CarStorageController {
    private final CarStorage carStorage;
    private final ThreadPool workerPool;
    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final double refillThreshold;

    public CarStorageController(CarStorage carStorage, ThreadPool workerPool,
                                BodyStorage bodyStorage, EngineStorage engineStorage,
                                AccessoryStorage accessoryStorage, double refillThreshold) {
        this.carStorage = carStorage;
        this.workerPool = workerPool;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.refillThreshold = refillThreshold;

        startMonitoring();
    }

    private void startMonitoring() {
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (carStorage) {
                        // Проверяем сразу при старте и после каждого изменения
                        if (needMoreCars()) {
                            requestMoreCars();
                        }
                        carStorage.wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    private boolean needMoreCars() {
        double fillRatio = (double) carStorage.size() / carStorage.capacity();
        return fillRatio < refillThreshold;
    }

    void requestMoreCars() {
        int needed = (int) (carStorage.capacity() * refillThreshold) - carStorage.size();
        for (int i = 0; i < needed; i++) {
            workerPool.addTask(() -> {
                try {
                    Body body = bodyStorage.take();
                    Engine engine = engineStorage.take();
                    Accessory accessory = accessoryStorage.take();
                    Car car = new Car(body, engine, accessory);
                    carStorage.add(car);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}