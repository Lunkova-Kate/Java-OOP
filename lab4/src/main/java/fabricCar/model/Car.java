package fabricCar.model;

import fabricCar.model.details.Accessory;
import fabricCar.model.details.Body;
import fabricCar.model.details.Engine;

public class Car {
    private final int id;
    private final Body body;
    private final Engine engine;
    private final Accessory accessory;

    public Car(int id, Body body, Engine engine, Accessory accessory) {
        this.id = id;
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    public int getId() { return id; }
    public Body getBody() { return body; }
    public Engine getEngine() { return engine; }
    public Accessory getAccessory() { return accessory; }
}
