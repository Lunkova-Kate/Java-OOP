package factoryCar.threads.supplier;
import factoryCar.model.details.Engine;
import factoryCar.model.storage.EngineStorage;
import java.util.concurrent.atomic.AtomicInteger;

public class EngineSupplier extends Supplier<Engine> {
    private static final AtomicInteger nextId = new AtomicInteger(0);

    public EngineSupplier(EngineStorage storage, int delay) {
        super(storage, delay);
    }

    @Override
    protected Engine createItem() {
        return new Engine(nextId.incrementAndGet());
    }
}