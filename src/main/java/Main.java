import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
	private static final File log = new File("log.txt");

	public static void main(String[] args) {
		if (true) {
			try {
				FileOutputStream fos = new FileOutputStream(log);
				PrintStream ps = new PrintStream(fos);
				System.setOut(ps);
				System.setErr(ps);
			} catch (FileNotFoundException e) {
				System.out.println("[ERROR]: The log file could not be opened.");
			}
		}


		Timer te = new Timer("Graph generation");
		for (int i = 0; i < 10; i++) {
			GraphGenerator gg = new GraphGenerator(1000, 0.2, true);
			OrdenacaoTopologica list;
			te.start();
			gg.run();
			OrdenacaoTopologica ord = new OrdenacaoTopologica(gg.getResult(), 1000);
			list = ord.topologicalSort();
			list.print();
			te.end();
			System.out.println(te.getTime());
		}
		System.out.println(te.getAverageTime());
	}
}
