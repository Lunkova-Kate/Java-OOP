package factoryCar.threads.supplier;
import factoryCar.model.details.Body;
import factoryCar.model.storage.BodyStorage;
import java.util.concurrent.atomic.AtomicInteger;

public class BodySupplier extends Supplier<Body> {
    private static final AtomicInteger nextId = new AtomicInteger(0);

    public BodySupplier(BodyStorage storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Body createItem() {
        return new Body(nextId.incrementAndGet());
    }
}