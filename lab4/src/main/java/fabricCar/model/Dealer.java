package fabricCar.model;

import fabricCar.model.Storage.CarStorage;
import fabricCar.utilitesforcar.Logger;

public class Dealer extends StoppableThread {
    private final int id;
    private final CarStorage carStorage;
    private final Logger logger;

    public Dealer(int id, CarStorage carStorage, int minDelay, int maxDelay, Logger logger) {
        super(minDelay, maxDelay);
        this.id = id;
        this.carStorage = carStorage;
        this.logger = logger;
    }

    @Override
    protected void performAction() throws InterruptedException {
        Car car = carStorage.take();
        logger.logSale(id, car);
    }
}