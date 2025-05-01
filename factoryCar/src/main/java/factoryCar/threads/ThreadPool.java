package factoryCar.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] workerThreads;
    private volatile boolean isRunning = true;
    private int nextWorkerId = 1;

    public ThreadPool(int poolSize) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workerThreads = new Thread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workerThreads[i] = new WorkerThread();
            workerThreads[i].start();
        }
    }

    public synchronized int getNextWorkerId() {
        return nextWorkerId++;
    }

    public void addTask(Runnable task) {
        if (isRunning) {
            taskQueue.add(task);
        }
    }

    public void shutdown() {
        isRunning = false;
        for (Thread workerThread : workerThreads) {
            workerThread.interrupt();
        }
    }

    public int getQueueSize() {
        return taskQueue.size();
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    if (!isRunning) {
                        return;
                    }
                }
            }
        }
    }
}