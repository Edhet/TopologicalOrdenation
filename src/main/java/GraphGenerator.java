public class GraphGenerator implements Runnable {
    private final int n;
    private final double p;
    private final boolean debug;

    private OrdenacaoTopologica.Elo result;

    public GraphGenerator(int n, double p, boolean debug) {
        this.debug = debug;
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
                    if (hasTwoWayEdge(origin, target) || hasCycle(origin)) {
                        undoLastEdge(origin);
                        target.contador--;
                    }
                }
            }

            float percentage = ((float) (i+1) / n) * 100;
            if (percentage > lastPercentage + 1.0f) {
                lastPercentage = percentage;
                System.out.printf("[INFO ]: Generation %.0f%% done\n", lastPercentage);
            }
        }
        result = adjacencyList[0];
        debug();
    }

    private boolean hasCycle(OrdenacaoTopologica.Elo node) {
        return recHasCycle(node.chave, node.listaSuc);
    }

    private boolean recHasCycle(int key, OrdenacaoTopologica.EloSuc sucNode) {
        if (sucNode == null) return false;
        if (sucNode.id.chave == key) return true;

        for (var ptr = sucNode.prox; ptr != null; ptr = ptr.prox) {
            if (recHasCycle(key, ptr))
                return true;
        }

        return recHasCycle(key, sucNode.id.listaSuc);
    }

    private boolean hasTwoWayEdge(OrdenacaoTopologica.Elo origin, OrdenacaoTopologica.Elo target) {
        if (target.listaSuc == null) return false;

        for (var ptr = target.listaSuc; ptr != null; ptr = ptr.prox) {
            if (ptr.id.chave == origin.chave) return true;
        }

        return false;
    }

    private void createEdge(OrdenacaoTopologica.Elo origin, OrdenacaoTopologica.EloSuc newEdge) {
        if (origin.listaSuc == null) {
            origin.listaSuc = newEdge;
            return;
        }

        var ptr = origin.listaSuc;
        while (ptr.prox != null) {
            ptr = ptr.prox;
        }
        ptr.prox = newEdge;
    }

    private void undoLastEdge(OrdenacaoTopologica.Elo node) {
        if (node.listaSuc.prox == null) {
            node.listaSuc = null;
            return;
        }

        OrdenacaoTopologica.EloSuc lastPtr = null;
        for (var ptr = node.listaSuc; ptr.prox != null; ptr = ptr.prox) {
            lastPtr = ptr;
        }
        lastPtr.prox = null;
    }

    private void populateAdjacencyList(OrdenacaoTopologica.Elo[] adjacencyList) {
        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new OrdenacaoTopologica.Elo(i, 0);
            if (i != 0 && adjacencyList[i - 1] != null)
                adjacencyList[i - 1].prox = adjacencyList[i];
        }
    }
}
