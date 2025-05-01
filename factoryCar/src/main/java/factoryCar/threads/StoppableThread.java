package factoryCar.threads;

public abstract class StoppableThread extends Thread {
    protected volatile boolean running = true;

    public void stopThread() {
        running = false;
        interrupt();
    }
}
