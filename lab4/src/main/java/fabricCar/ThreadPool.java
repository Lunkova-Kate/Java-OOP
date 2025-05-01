package fabricCar;


import fabricCar.controller.ThreadPoolObserver;
import fabricCar.view.StorageObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool implements ThreadPoolObserver {
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final List<WorkerThread> workers = new ArrayList<>();
    private volatile boolean isRunning = true;
    private volatile boolean isPaused = false;
    private final Object pauseLock = new Object();
    private final List<StorageObserver> observers = new ArrayList<>();
    private int totalTasksProcessed = 0;

    public ThreadPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
            worker.start();
        }
    }

    public void addTask(Runnable task) {
        if (isRunning) {
            taskQueue.add(task);
            notifyTaskQueueChanged();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void shutdown() {
        isRunning = false;
        workers.forEach(Thread::interrupt);
    }

    public int getQueueSize() {
        return taskQueue.size();
    }

    public int getTotalTasksProcessed() {
        return totalTasksProcessed;
    }

    public void addObserver(StorageObserver observer) {
        observers.add(observer);
    }

    private void notifyTaskQueueChanged() {
        int size = getQueueSize();
        observers.forEach(obs -> obs.onTaskQueueChanged(size));
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    checkPaused();

                    Runnable task = taskQueue.take();
                    task.run();
                    totalTasksProcessed++;
                    notifyTaskQueueChanged();

                } catch (InterruptedException e) {
                    if (!isRunning) {
                        break;
                    }
                }
            }
        }

        private void checkPaused() throws InterruptedException {
            synchronized (pauseLock) {
                while (isPaused && isRunning) {
                    pauseLock.wait();
                }
            }
        }
    }

    @Override
    public void onTaskCompleted() {
        totalTasksProcessed++;
        notifyTaskQueueChanged();
    }
}