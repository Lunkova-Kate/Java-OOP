package fabricCar.view;

public interface StorageObserver {
    void onBodyCountChanged(int count);
    void onEngineCountChanged(int count);
    void onAccessoryCountChanged(int count);
    void onCarCountChanged(int count);
    void onCarProduced(int totalProduced);
    void onCarSold(int totalSold);
    void onTaskQueueChanged(int tasksInQueue);
    void onMessage(String message);
}