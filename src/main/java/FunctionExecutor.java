import java.util.List;
import java.util.function.Consumer;

class FunctionExecutor<T> implements Runnable {
    Consumer<List<T>> function;
    List<T> parameters;

    public FunctionExecutor() {
    }

    public void setFunctionAndParameters(Consumer<List<T>> function, List<T> parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    @Override
    public void run() {
        if (parameters == null || function == null)
            throw new IllegalThreadStateException("No function or parameter list was supplied to started thread");
        this.function.accept(parameters);
    }
}
