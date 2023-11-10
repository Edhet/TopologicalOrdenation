import java.util.ArrayList;
import java.util.List;

public class TimedExecution {
    private final Thread executorThread;

    private Runnable function;
    private String lastExecutionTime;

    public TimedExecution() {
        this.executorThread = new Thread(() -> {
            long start = System.nanoTime();
            this.function.run();
            long end = System.nanoTime();
            this.lastExecutionTime = this.parseNanoSeconds(end - start);
        });
    }

    public TimedExecution(Runnable function) {
        this();
        this.function = function;
    }

    public synchronized void execute() throws IllegalStateException {
        if (this.functionIsRunning() || function == null)
            throw new IllegalStateException("Could not execute function.");
        this.executorThread.run();
    }

    public void setFunction(Runnable function) throws IllegalStateException {
        if (this.functionIsRunning())
            throw new IllegalStateException("Could not set function.");
        this.function = function;
    }

    public String getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void awaitExecution(long secondsToSleep) {
        while (executorThread.isAlive()) {
            try {
                Thread.sleep(secondsToSleep * 1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean functionIsRunning() {
        return executorThread.isAlive();
    }

    private String parseNanoSeconds(long executionTimeInNanos) {
        List<String> timeOrder = new ArrayList<>();

        if (executionTimeInNanos / (3600L * 1_000_000_000) > 0) {
            long hour = executionTimeInNanos / (3600L * 1_000_000_000);
            timeOrder.add(hour + "h");
            executionTimeInNanos = executionTimeInNanos / (3600L * 1_000_000_000);
        }
        if (executionTimeInNanos / (60L * 1_000_000_000) > 0) {
            long min = executionTimeInNanos / (60L * 1_000_000_000);
            timeOrder.add(min + "m");
            executionTimeInNanos = executionTimeInNanos % (60L * 1_000_000_000);
        }
        if (executionTimeInNanos / 1_000_000_000 > 0) {
            long secs = executionTimeInNanos / 1_000_000_000;
            timeOrder.add(secs + "s");
            executionTimeInNanos = executionTimeInNanos % 1_000_000_000;
        }
        if (executionTimeInNanos / 1_000_000 > 0) {
            long milis = executionTimeInNanos / 1_000_000;
            timeOrder.add(milis + "ms");
        }

        if (timeOrder.isEmpty())
            return "< ms";

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String order : timeOrder) {
            if (i == 2) break;
            stringBuilder.append(order);
            i++;
        }
        return stringBuilder.toString();
    }
}
