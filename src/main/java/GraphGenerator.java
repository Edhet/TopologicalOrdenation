public class GraphGenerator implements Runnable {
    private final int n;
    private final double p;

    private OrdenacaoTopologica.Elo result;

    private final boolean debug, progress;
    private float generationPercentage = -1.0f;

    public GraphGenerator(int n, double p, boolean debug, boolean progress) {
        this.debug = debug;
        this.progress = progress;
        this.n = n;
        this.p = p;
    }

    public OrdenacaoTopologica.Elo getResult() {
        return result;
    }

    public void debug() {
        if (!debug) return;

        System.out.println("[DEBUG]: Generated Graph {");
        for (var ptr = result; ptr != null; ptr = ptr.prox) {
            System.out.print("Node: " + ptr.chave + " Predecessors: " + ptr.contador + " Sucessors: ");
            for (var sucPtr = ptr.listaSuc; sucPtr != null; sucPtr = sucPtr.prox) {
                System.out.print(sucPtr.id.chave + ((sucPtr.prox == null) ? "" : ", "));
            }
            System.out.println();
        }
        System.out.println("}");
    }

    private void showGenerationProgress(int i) {
        if (!progress) return;

        float percentage = ((float) (i +1) / n) * 100;
        if (percentage > generationPercentage + 1.0f) {
            generationPercentage = percentage;
            System.out.printf("[DEBUG]: Generation %.0f%% done\n", generationPercentage);
        }
    }

    @Override
    public void run() {
        var adjacencyList = new OrdenacaoTopologica.Elo[n];
        populateAdjacencyList(adjacencyList);

        var lastPercentage = -1.0;
        for (int i = 0; i < n; i++) {
            var origin = adjacencyList[i];
            for (int j = 1; j < n; j++) {
                if (j != i && Math.random() < p) {
                    var target = adjacencyList[j];
                    target.contador++;

                    createEdge(origin, new OrdenacaoTopologica.EloSuc(target, null));
                }
            }
            for (int j = 1; j < n; j++) {
                removeCycles(adjacencyList[j]);
            }
            showGenerationProgress(i);
        }
        result = adjacencyList[0];
        debug();
    }

    private void removeCycles(OrdenacaoTopologica.Elo node) {
        var visited = new boolean[n];
        var recStack = new boolean[n];
        recRemoveCycles(node, visited, recStack, null);
    }

    private boolean recRemoveCycles(OrdenacaoTopologica.Elo node, boolean[] visited, boolean[] recStack, OrdenacaoTopologica.Elo lastNode) {
        var key = node.chave;

        if (recStack[key]) return true;
        if (visited[key]) return false;

        visited[key] = true;
        recStack[key] = true;

        for (var ptr = node.listaSuc; ptr != null; ptr = ptr.prox) {
            if (recRemoveCycles(ptr.id, visited, recStack, node)) {
                removeEdge(lastNode, ptr.id.chave);
                return true;
            }
        }
        recStack[key] = false;
        return false;
    }

    private OrdenacaoTopologica.EloSuc createEdge(OrdenacaoTopologica.Elo origin, OrdenacaoTopologica.EloSuc newEdge) {
        if (origin.listaSuc == null) {
            origin.listaSuc = newEdge;
            return null;
        }

        var ptr = origin.listaSuc;
        while (ptr.prox != null) {
            ptr = ptr.prox;
        }
        ptr.prox = newEdge;
        return ptr;
    }

    private void removeEdge(OrdenacaoTopologica.Elo node, int key) {
        if (node == null || node.listaSuc == null) return;

        OrdenacaoTopologica.EloSuc lastPtr = null;
        for (var ptr = node.listaSuc; ptr.prox != null; ptr = ptr.prox) {
            if (ptr.id.chave == key) break;
            lastPtr = ptr;
        }
        if (lastPtr != null) {
            lastPtr.prox.id.contador--;
            lastPtr.prox = null;
        }
        else {
            node.listaSuc.id.contador--;
            node.listaSuc = null;
        }
    }

    private void populateAdjacencyList(OrdenacaoTopologica.Elo[] adjacencyList) {
        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new OrdenacaoTopologica.Elo(i, 0);
            if (i != 0 && adjacencyList[i - 1] != null)
                adjacencyList[i - 1].prox = adjacencyList[i];
        }
    }
}
