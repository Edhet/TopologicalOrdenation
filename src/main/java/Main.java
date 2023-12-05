import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    private static final File LOG_FILE = new File("log.txt");
    private static final int MAX_ITERATIONS = 1;
    private static final double EDGE_CHANCE = 0.2;
//    private static final int[] GRAPH_SIZES = {10, 20, 30, 50, 100, 200, 500, 1_000, 5_000, 10_000, 20_000, 30_000, 50_000, 100_000};
    private static final int[] GRAPH_SIZES = {50000};

    public static void main(String[] args) {
        if (false) {
            try {
                FileOutputStream fos = new FileOutputStream(LOG_FILE);
                PrintStream ps = new PrintStream(fos);
                System.setOut(ps);
                System.setErr(ps);
            } catch (FileNotFoundException e) {
                System.out.println("[ERROR]: The log file could not be opened.");
            }
        }

        Timer generationTimer = new Timer();
        Timer sortingTimer = new Timer();
        Timer processTimer = new Timer();

        for (int size : GRAPH_SIZES) {
            System.out.println("[INFO ]: Started " + MAX_ITERATIONS + " generations and sorting of " + size + " nodes graphs.");
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                processTimer.start();

                System.out.println("[INFO ]: Generation started");
                generationTimer.start();
                GraphGenerator generator = new GraphGenerator(size, EDGE_CHANCE, true, true);
                generator.run();
                generationTimer.end();

                System.out.println("[INFO ]: Sorting started");
                sortingTimer.start();
                OrdenacaoTopologica sortedList = new OrdenacaoTopologica(generator.getResult(), size);
                sortedList.executa();
                sortingTimer.end();

                processTimer.end();
                System.out.println("[INFO ]: Graph number " + (i + 1) + " finished generation in " + generationTimer.getTime() +
                        ", and sorting in " + sortingTimer.getTime() + ". Full time: " + processTimer.getTime());
            }
            System.out.println("[INFO ]: Process have finished with an average time of: " + processTimer.getAverageTime());
            System.out.println("[INFO ]: Average time of graph generation: " + generationTimer.getAverageTime());
            System.out.println("[INFO ]: Average time of graph sorting: " + sortingTimer.getAverageTime());

            processTimer.resetAverage();
            generationTimer.resetAverage();
            sortingTimer.resetAverage();

            System.out.println();
        }
    }

}
