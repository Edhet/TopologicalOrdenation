import java.util.ArrayList;
import java.util.List;

public class Timer {
    private long start, end;

    private List<Long> executionTimes = new ArrayList<>();

    private String parsedTime;

    public Timer() { }

    public void start() {
        start = System.nanoTime();
    }

    public void end() {
        if (start == 0)
            return;
        end = System.nanoTime();
        var time = end - start;
        executionTimes.add(time);
        parsedTime = parseNanoSeconds(time);
    }

    public void resetAverage() {
        executionTimes = new ArrayList<>();
    }

    public String getAverageTime() {
        long sum = 0;
        for (var time : executionTimes)
            sum += time;
        return parseNanoSeconds((sum / executionTimes.size()));
    }

    public String getTime() {
        return parsedTime;
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
            return "less than a ms";

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
