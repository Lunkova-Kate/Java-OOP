package bombSquad.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    @Test
    void testStartAndElapsedTimeTrue() throws InterruptedException {
        GameTimer timer = new GameTimer();
        timer.start();
        Thread.sleep(100); // Ждем 100 мс
        long elapsedTime = timer.getElapsedTime();
        assertTrue(elapsedTime >= 100 && elapsedTime < 200);
    }

    @Test
    void testPauseAndResumeTrue() throws InterruptedException {
        GameTimer timer = new GameTimer();
        timer.start();
        Thread.sleep(100);
        timer.pause();
        Thread.sleep(100);
        timer.start();
        Thread.sleep(100);

        long elapsedTime = timer.getElapsedTime();
        assertTrue(elapsedTime >= 200 && elapsedTime < 300);
    }

    @Test
    void testResetEquals() {
        GameTimer timer = new GameTimer();
        timer.start();
        timer.reset();
        assertEquals(0, timer.getElapsedTime());
    }

    @Test
    void testFormattedTimeEquals() throws InterruptedException {
        GameTimer timer = new GameTimer();
        timer.start();
        Thread.sleep(6100); // ждем 6 секунд
        assertEquals("00:06", timer.getFormattedTime());
    }
}