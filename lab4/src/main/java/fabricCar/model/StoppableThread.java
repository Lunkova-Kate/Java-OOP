package fabricCar.model;

public abstract class StoppableThread implements Runnable {
    private Thread workerThread;  // Поток, в котором выполняется задача
    protected volatile boolean isRunning;  // Флаг работы потока
    protected final int minDelayMs;  // Минимальная задержка между действиями (мс)
    protected final int maxDelayMs;  // Максимальная задержка между действиями (мс)

    public StoppableThread(int minDelayMs, int maxDelayMs) {
        this.minDelayMs = minDelayMs;
        this.maxDelayMs = maxDelayMs;
        this.isRunning = false;
    }

    // Запуск потока
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            workerThread = new Thread(this);
            workerThread.start();
        }
    }

    // Остановка потока
    public synchronized void stop() {
        isRunning = false;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    // Основная логика потока
    @Override
    public void run() {
        try {
            while (isRunning) {
                performAction();  // Основная работа (реализуется в наследниках)

                // Случайная задержка в заданном диапазоне
                int delay = minDelayMs + (int)(Math.random() * (maxDelayMs - minDelayMs));
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            // Восстанавливаем статус прерывания
            Thread.currentThread().interrupt();
        } finally {
            onThreadStop();  // Завершающие действия
        }
    }

    // Абстрактный метод - основная работа потока
    protected abstract void performAction() throws InterruptedException;

    // Дополнительные действия при остановке (можно переопределить)
    protected void onThreadStop() {
        // По умолчанию - ничего не делать
    }
}
