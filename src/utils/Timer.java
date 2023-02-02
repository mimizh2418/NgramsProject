package utils;

public class Timer {

    private long startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }

    public long getTimeElapsed() {
        return System.currentTimeMillis() - startTime;
    }
}
