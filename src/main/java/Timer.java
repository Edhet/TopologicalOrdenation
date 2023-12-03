import java.util.ArrayList;
import java.util.List;

public class Timer {
    private final String identifier;
    private long start, end;
    private String parsedTime;

    public Timer(String identifier) {
        this.identifier = identifier;
    }

    public void start() {
        start = System.nanoTime();
    }

    public void end() {
        if (start == 0) return;
        end = System.nanoTime();
        parseNanoSeconds(end - start);
    }

    public String getParsedTime() {
        return parsedTime;
    }

    private void parseNanoSeconds(long executionTimeInNanos) {
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
            parsedTime = "[INFO ]: " + identifier + " execution time: < ms";

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String order : timeOrder) {
            if (i == 2) break;
            stringBuilder.append(order);
            i++;
        }
        parsedTime = "[INFO ]: " + identifier + " execution time: " + stringBuilder.toString();
    }
}
