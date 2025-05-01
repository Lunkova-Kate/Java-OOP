package fabricCar.utilitesforcar;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties properties;

    // Параметры конфигурации с default-значениями
    private int bodyStorageSize = 100;
    private int engineStorageSize = 100;
    private int accessoryStorageSize = 100;
    private int carStorageSize = 100;

    private int accessorySuppliers = 10;
    private int workers = 10;
    private int dealers = 10;

    private boolean logSale = false;

    private int supplierMinDelay = 500; //милисекунды!!
    private int supplierMaxDelay = 1000;
    private int dealerMinDelay = 1000;
    private int dealerMaxDelay = 2000;


    public Config(String configPath) throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
            loadProperties();
        }
    }

    private void loadProperties() {
        bodyStorageSize = getIntProperty("StorageBodySize", bodyStorageSize);
        engineStorageSize = getIntProperty("StorageMotorSize", engineStorageSize);
        accessoryStorageSize = getIntProperty("StorageAccessorySize", accessoryStorageSize);
        carStorageSize = getIntProperty("StorageAutoSize", carStorageSize);
        accessorySuppliers = getIntProperty("AccessorySuppliers", accessorySuppliers);
        workers = getIntProperty("Workers", workers);
        dealers = getIntProperty("Dealers", dealers);
        logSale = getBooleanProperty("LogSale", logSale);
        supplierMinDelay = getIntProperty("SupplierMinDelay", supplierMinDelay);
        supplierMaxDelay = getIntProperty("SupplierMaxDelay", supplierMaxDelay);
        dealerMinDelay = getIntProperty("DealerMinDelay", dealerMinDelay);
        dealerMaxDelay = getIntProperty("DealerMaxDelay", dealerMaxDelay);
    }

    private int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    private boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public int getBodyStorageSize() {
        return bodyStorageSize;
    }

    public int getEngineStorageSize() {
        return engineStorageSize;
    }

    public int getAccessoryStorageSize() {
        return accessoryStorageSize;
    }

    public int getCarStorageSize() {
        return carStorageSize;
    }

    public int getAccessorySuppliers() {
        return accessorySuppliers;
    }

    public int getWorkers() {
        return workers;
    }

    public int getDealers() {
        return dealers;
    }

    public boolean isLogSale() {
        return logSale;
    }

    public int getSupplierMinDelay() {
        return supplierMinDelay;
    }

    public int getSupplierMaxDelay() {
        return supplierMaxDelay;
    }

    public int getDealerMinDelay() {
        return dealerMinDelay;
    }

    public int getDealerMaxDelay() {
        return dealerMaxDelay;
    }
}
