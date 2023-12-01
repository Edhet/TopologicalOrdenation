import Demos.Constants;
import Demos.EratosthenesSieveDemo;

public class Main {

    public static void main(String[] args) {
        TimedExecution timedExecution = new TimedExecution();

        timedExecution.setFunction(new EratosthenesSieveDemo((int)(Integer.MAX_VALUE/1.5)));
        timedExecution.execute();
        timedExecution.awaitExecution(2);
        System.out.println(timedExecution.getLastExecutionTime());
    }
}
