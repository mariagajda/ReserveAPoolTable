package pl.coderslab.reserveapooltable.stopwatch;

public class StopWatch {
    private long startTime;

    public StopWatch() {
        startTime = System.currentTimeMillis();
    }

    public double getElapsedTime() {
        long endTime = System.currentTimeMillis();
        return (double) endTime - startTime;
    }
}
