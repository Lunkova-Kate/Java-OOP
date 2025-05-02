package factoryCar.controller;

import factoryCar.model.storage.*;
import factoryCar.threads.*;
import factoryCar.threads.supplier.*;
import factoryCar.threads.another.*;
import factoryCar.utilityForCar.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FactoryController {
    private final List<Supplier<?>> suppliers = new ArrayList<>();
    private final List<Dealer> dealers = new ArrayList<>();
    private ThreadPool workerPool;
    private CarStorageController carStorageController;
    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final CarStorage carStorage;
    private final Config config;


    public FactoryController(Config config){
        this.config = config;
        this.bodyStorage = new BodyStorage(config.getBodyStorageSize());
        this.engineStorage = new EngineStorage(config.getEngineStorageSize());
        this.accessoryStorage = new AccessoryStorage(config.getAccessoryStorageSize());
        this.carStorage = new CarStorage(config.getCarStorageSize());
        this.workerPool = new ThreadPool(config.getWorkers());
    }

    public void start() {
        startSuppliers();
        startWorkerPool();
        startCarStorageController();
        startDealers();

        // Активируем первую проверку
        synchronized (carStorage) {
            carStorage.notifyAll();
        }
    }

    public void stop() {
        suppliers.forEach(Supplier::stopThread);
        dealers.forEach(Dealer::stopThread);
        if (workerPool != null) {
            workerPool.shutdown();
        }
    }

    private void startSuppliers() {
        // Поставщики кузовов
        for (int i = 0; i < config.getBodySuppliers(); i++) {
            BodySupplier supplier = new BodySupplier(bodyStorage, config.getSupplierMinDelay());
            suppliers.add(supplier);
            supplier.start();
        }

        // Поставщики двигателей
        for (int i = 0; i < config.getEngineSuppliers(); i++) {
            EngineSupplier supplier = new EngineSupplier(engineStorage, config.getSupplierMinDelay());
            suppliers.add(supplier);
            supplier.start();
        }

        // Поставщики аксессуаров
        for (int i = 0; i < config.getAccessorySuppliers(); i++) {
            AccessorySupplier supplier = new AccessorySupplier(accessoryStorage, config.getSupplierMinDelay());
            suppliers.add(supplier);
            supplier.start();
        }
    }

    private void startWorkerPool() {
        this.workerPool = new ThreadPool(config.getWorkers());
    }

    private void startCarStorageController() {
        this.carStorageController = new CarStorageController(
                carStorage,
                workerPool,
                bodyStorage,
                engineStorage,
                accessoryStorage,
                0.8
        );

    }

    private void startDealers() {
        for (int i = 0; i < config.getDealers(); i++) {
            Dealer dealer = new Dealer(
                    carStorage,
                    config.getDealerMinDelay(),
                    config.isLogSale()
            );
            dealers.add(dealer);
            dealer.start();
        }
    }

    public void setBodySupplierDelay(int delay) {
        suppliers.stream()
                .filter(s -> s instanceof BodySupplier)
                .forEach(s -> s.setDelay(delay));
    }

    public void setEngineSupplierDelay(int delay) {
        suppliers.stream()
                .filter(s -> s instanceof EngineSupplier)
                .forEach(s -> s.setDelay(delay));
    }

    public void setAccessorySupplierDelay(int delay) {
        suppliers.stream()
                .filter(s -> s instanceof AccessorySupplier)
                .forEach(s -> s.setDelay(delay));
    }

    public void setDealerDelay(int delay) {
        dealers.forEach(d -> d.setDelay(delay));
    }

    public BodyStorage getBodyStorage() { return bodyStorage; }
    public EngineStorage getEngineStorage() { return engineStorage; }
    public AccessoryStorage getAccessoryStorage() { return accessoryStorage; }
    public CarStorage getCarStorage() { return carStorage; }
}