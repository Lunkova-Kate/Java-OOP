package fabricCar.controller;

import fabricCar.ThreadPool;
import fabricCar.model.*;
import fabricCar.model.Storage.*;
import fabricCar.model.supplier.*;
import fabricCar.utilitesforcar.Config;
import fabricCar.utilitesforcar.Logger;
import fabricCar.view.FactoryView;
import fabricCar.view.StorageObserver;

import java.util.ArrayList;
import java.util.List;

public class FactoryController {
    private final Config config;
    private final FactoryView view;
    private final Logger logger;

    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final CarStorage carStorage;

    private final ThreadPool workers;
    private final CarStorageController carStorageController;

    private final List<BodySupplier> bodySuppliers = new ArrayList<>();
    private final List<EngineSupplier> engineSuppliers = new ArrayList<>();
    private final List<AccessorySupplier> accessorySuppliers = new ArrayList<>();
    private final List<Dealer> dealers = new ArrayList<>();

    public FactoryController(Config config, FactoryView view) {
        this.config = config;
        this.view = view;
        this.logger = new Logger(config.isLogSale(), "dealer.txt");

        // Инициализация складов
        this.bodyStorage = new BodyStorage(config.getBodyStorageSize());
        this.engineStorage = new EngineStorage(config.getEngineStorageSize());
        this.accessoryStorage = new AccessoryStorage(config.getAccessoryStorageSize());
        this.carStorage = new CarStorage(config.getCarStorageSize());

        // Инициализация пула рабочих
        this.workers = new ThreadPool(config.getWorkers());
        this.carStorageController = new CarStorageController(
                carStorage, bodyStorage, engineStorage, accessoryStorage,
                workers, config.getCarStorageSize() / 4, config.getCarStorageSize() * 3 / 4
        );

        setupObservers();
        setupSuppliers();
        setupDealers();

        view.setStartAction(this::startProduction);
        view.setStopAction(this::stopProduction);
    }

    private void setupObservers() {
        StorageObserver observer = new StorageObserver() {
            @Override public void onBodyCountChanged(int count) { view.onBodyCountChanged(count); }
            @Override public void onEngineCountChanged(int count) { view.onEngineCountChanged(count); }
            @Override public void onAccessoryCountChanged(int count) { view.onAccessoryCountChanged(count); }
            @Override public void onCarCountChanged(int count) { view.onCarCountChanged(count); }
            @Override public void onCarProduced(int totalProduced) {
                view.onCarProduced(totalProduced);
                carStorageController.onCarProduced();
            }
            @Override public void onCarSold(int totalSold) {
                view.onCarSold(totalSold);
                carStorageController.onCarSold();
            }
            @Override public void onTaskQueueChanged(int tasksInQueue) { view.onTaskQueueChanged(tasksInQueue); }
            @Override public void onMessage(String message) { view.onMessage(message); }
        };

        bodyStorage.addObserver(observer);
        engineStorage.addObserver(observer);
        accessoryStorage.addObserver(observer);
        carStorage.addObserver(observer);
        workers.addObserver(observer);
    }

    private void setupSuppliers() {
        // Поставщики кузовов
        for (int i = 0; i < 1; i++) { // Один поставщик кузовов
            BodySupplier supplier = new BodySupplier(bodyStorage,
                    config.getSupplierMinDelay(), config.getSupplierMaxDelay());
            bodySuppliers.add(supplier);
        }

        // Поставщики двигателей
        for (int i = 0; i < 1; i++) { // Один поставщик двигателей
            EngineSupplier supplier = new EngineSupplier(engineStorage,
                    config.getSupplierMinDelay(), config.getSupplierMaxDelay());
            engineSuppliers.add(supplier);
        }

        // Поставщики аксессуаров
        for (int i = 0; i < config.getAccessorySuppliers(); i++) {
            AccessorySupplier supplier = new AccessorySupplier(
                    config.getSupplierMinDelay(),
                    config.getSupplierMaxDelay(),
                    accessoryStorage
            );
            accessorySuppliers.add(supplier);
        }
    }

    private void setupDealers() {
        for (int i = 0; i < config.getDealers(); i++) {
            Dealer dealer = new Dealer(i, carStorage,
                    config.getDealerMinDelay(), config.getDealerMaxDelay(), logger);
            dealers.add(dealer);
        }
    }

    public void startProduction() {
        bodySuppliers.forEach(StoppableThread::start);
        engineSuppliers.forEach(StoppableThread::start);
        accessorySuppliers.forEach(StoppableThread::start);
        dealers.forEach(StoppableThread::start);
        view.onMessage("Production started");
    }

    public void stopProduction() {
        bodySuppliers.forEach(StoppableThread::stop);
        engineSuppliers.forEach(StoppableThread::stop);
        accessorySuppliers.forEach(StoppableThread::stop);
        dealers.forEach(StoppableThread::stop);
        workers.shutdown();
        logger.close();
        view.onMessage("Production stopped");
    }
}

/*public class FactoryController {
    private final BodyStorage bodyStorage;
    private final EngineStorage engineStorage;
    private final AccessoryStorage accessoryStorage;
    private final CarStorage carStorage;
    private final List<StoppableThread> suppliers;
    private final List<Dealer> dealers;
    private final ThreadPool threadPool;

    public void startProduction() { ... }
    public void stopProduction() { ... }
    public void setSupplierDelay(SupplierType type, int delay) { ... }
}*/