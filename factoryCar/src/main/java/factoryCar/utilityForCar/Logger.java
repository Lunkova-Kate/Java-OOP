package factoryCar.utilityForCar;

import factoryCar.model.details.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final boolean isEnabled;
    private final PrintWriter writer;

    public Logger(boolean isEnabled, String logFilePath) {
        this.isEnabled = isEnabled;
        if (isEnabled && logFilePath != null) {
            try {
                this.writer = new PrintWriter(new FileWriter(logFilePath, true));
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize logger", e);
            }
        } else {
            this.writer = null;
        }
    }

    public void logSale(long dealerId, Car car) {
        if (!isEnabled) return;

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logMessage = String.format(
                "%s: Dealer %d: Auto %d (Body: %d, Motor: %d, Accessory: %d)",
                timestamp,
                dealerId,
                car.getId(),
                car.getBody().getId(),
                car.getEngine().getId(),
                car.getAccessory().getId()
        );

        if (writer != null) {
            writer.println(logMessage);
            writer.flush();
        } else {
            System.out.println(logMessage);
        }
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}