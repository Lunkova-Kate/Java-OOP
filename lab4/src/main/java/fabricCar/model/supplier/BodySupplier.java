package fabricCar.model.supplier;

import fabricCar.model.StoppableThread;
import fabricCar.model.Storage.Storage;
import fabricCar.model.details.Body;


public class BodySupplier extends StoppableThread {
    private final Storage<Body> storage;
    private static int nextId = 0;

    public BodySupplier(Storage<Body> storage, int minDelay, int maxDelay) {
        super(minDelay, maxDelay);
        this.storage = storage;
    }

    @Override
    protected void performAction() throws InterruptedException {
        Body body = new Body(nextId++);
        storage.add(body);
    }
}