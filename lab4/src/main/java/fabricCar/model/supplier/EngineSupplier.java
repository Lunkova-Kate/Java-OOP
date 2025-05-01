package fabricCar.model.supplier;

import fabricCar.model.StoppableThread;
import fabricCar.model.Storage.Storage;
import fabricCar.model.details.Engine;

public class EngineSupplier extends StoppableThread {
    private final Storage<Engine> storage;
    private static int nextId = 0;

    public EngineSupplier(Storage<Engine> storage, int minDelay, int maxDelay) {
        super(minDelay, maxDelay);
        this.storage = storage;
    }

    @Override
    protected void performAction() throws InterruptedException {
       Engine engine = new Engine(nextId++);
        storage.add(engine);
    }

}
