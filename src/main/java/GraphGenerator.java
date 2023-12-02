public class GraphGenerator implements Runnable {
    private final int n;
    private final double p;

    private OrdenacaoTopologica.Elo result;

    public GraphGenerator(int n, double p) {
        this.n = n;
        this.p = p;
    }

    public OrdenacaoTopologica.Elo getResult() {
        return result;
    }

    public void debug() {
        for (var ptr = result; ptr != null; ptr = ptr.prox) {
            System.out.print("Node: " + ptr.chave + " Predecessors: " + ptr.contador + " Sucessors: ");
            for (var sucPtr = ptr.listaSuc; sucPtr != null; sucPtr = sucPtr.prox) {
                System.out.print(sucPtr.id.chave + ((sucPtr.prox == null) ? "" : ", "));
            }
            System.out.println();
        }
    }

    @Override
    public void run() {
        var adjacencyList = new OrdenacaoTopologica.Elo[n];
        populateAdjacencyList(adjacencyList);

        for (int i = 0; i < n; i++) {
            var origin = adjacencyList[i];
            for (int j = 0; j < n; j++) {
                if (j != i && Math.random() < p) {
                    var target = adjacencyList[j];
                    target.contador++;

                    createEdge(origin, new OrdenacaoTopologica.EloSuc(target, null));
                    if (hasTwoWayEdge(origin, target) || isCiclical(origin)) {
                        undoLastEdge(origin);
                        target.contador--;
                    }
                }
            }
        }
        result = adjacencyList[0];
        debug();
    }

    private boolean isCiclical(OrdenacaoTopologica.Elo node) {
        return recIsCiclicalImpl(node.chave, node.listaSuc);
    }

     private boolean recIsCiclicalImpl(int key, OrdenacaoTopologica.EloSuc sucessorNode) {
        if (sucessorNode == null) return false;
        if (sucessorNode.id.chave == key) return true;

        for (var ptr = sucessorNode; ptr.prox != null; ptr = ptr.prox) {
            if (recIsCiclicalImpl(key, ptr.id.listaSuc))
                return true;
        }

        return recIsCiclicalImpl(key, sucessorNode.prox);
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
