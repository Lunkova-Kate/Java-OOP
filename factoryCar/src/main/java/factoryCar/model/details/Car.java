package factoryCar.model.details;

public class Car {
    private final int id;
    private final Body body;
    private final Engine engine;
    private final Accessory accessory;
    private static int nextId = 1;

    public Car(Body body, Engine engine, Accessory accessory) {
        this.id = nextId++;
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    public int getId() { return id; }
    public Body getBody() { return body; }
    public Engine getEngine() { return engine; }
    public Accessory getAccessory() { return accessory; }
}