package bombSquad.model;

public class GameTimer {

    private volatile long startTime;
    private volatile long pauseTime;
    private volatile long totalPausedTime;
    private volatile boolean isRunning;
    private volatile boolean isPaused;


    public GameTimer() {
        reset();
    }

    public synchronized void reset() {
        startTime = 0;
        pauseTime = 0;
        totalPausedTime = 0;
        isRunning = false;
        isPaused = false;
    }

    public synchronized void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            totalPausedTime = 0;
            isRunning = true;
            isPaused = false;
        } else if (isPaused) {
            totalPausedTime += System.currentTimeMillis() - pauseTime;
            isPaused = false;
        }
    }

    public synchronized void pause() {
        if (isRunning && !isPaused) {
            pauseTime = System.currentTimeMillis();
            isPaused = true;
        }
    }
    public synchronized long getElapsedTime() {
        if (!isRunning) {
            return 0;
        }

        if (isPaused) {
            return pauseTime - startTime - totalPausedTime;
        }

        return System.currentTimeMillis() - startTime - totalPausedTime;
    }

    public String getFormattedTime() {
        long elapsed = getElapsedTime() / 1000;
        long minutes = elapsed / 60;
        long seconds = elapsed % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
/*GameTimer.java
Поля :
startTime, pauseTime, totalPausedTime: Временные метки.
isRunning, isPaused: Состояние таймера.
Методы :
start(): Запускает таймер.
pause(): Приостанавливает таймер.
resume(): Возобновляет таймер.
reset(): Сбрасывает таймер.
getElapsedTime(): Возвращает прошедшее время.
getFormattedTime(): Возвращает форматированное время.
Геттеры для состояний.*/