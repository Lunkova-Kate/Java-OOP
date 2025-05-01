package fabricCar.model.supplier;

import fabricCar.model.StoppableThread;
import fabricCar.model.Storage.Storage;
import fabricCar.model.details.Accessory;


public class AccessorySupplier extends StoppableThread{
    private final Storage<Accessory> storage;
    private static int nextId = 0;

    public AccessorySupplier(int minDelayMs, int maxDelayMs, Storage<Accessory> storage) {
        super(minDelayMs, maxDelayMs);
        this.storage = storage;
    }

    @Override
    protected void performAction() throws InterruptedException {
        Accessory accessory = new Accessory(nextId++);
        storage.add(accessory);
    }
}

