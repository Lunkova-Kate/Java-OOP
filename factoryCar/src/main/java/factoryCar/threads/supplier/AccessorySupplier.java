package factoryCar.threads.supplier;
import factoryCar.model.details.Accessory;
import factoryCar.model.storage.AccessoryStorage;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessorySupplier extends Supplier<Accessory> {
    private static final AtomicInteger nextId = new AtomicInteger(0);

    public AccessorySupplier(AccessoryStorage storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Accessory createItem() {
        return new Accessory(nextId.incrementAndGet());
    }
}