public class GraphGenerator implements Runnable {
    private final int n;
    private final double p;
    private OrdenacaoTopologica.Elo result;

    private final boolean debug;

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
                System.out.print(sucPtr.id.chave + (sucPtr.prox == null ? "" : ", "));
            }
            System.out.println();
        }
        System.out.println("}");
    }

    @Override
    public void run() {
        var adjacencyList = new OrdenacaoTopologica.Elo[n];
        populateAdjacencyList(adjacencyList);

        for (int i = 0; i < n; i++) {
            var origin = adjacencyList[i];
            for (int j = 1; j < n; j++) {
                if (j != i && Math.random() < p) {
                    var target = adjacencyList[j];
                    target.contador++;
                    createEdge(origin, new OrdenacaoTopologica.EloSuc(target, null));
                }
            }
        }
        removeCycles(adjacencyList);

        result = adjacencyList[0];
        debug();
    }

    private void removeCycles(OrdenacaoTopologica.Elo[] adjacencyList) {
        var visited = new boolean[n];
        var stack = new boolean[n];

        for (int j = 0; j < n; j++) {
            if (!visited[j])
                dfs(adjacencyList[j], visited, stack);
        }
    }

    private void dfs(OrdenacaoTopologica.Elo node, boolean[] visited, boolean[] stack) {
        visited[node.chave] = true;
        stack[node.chave] = true;

        for (var ptr = node.listaSuc; ptr != null; ptr = ptr.prox) {
            if (!visited[ptr.id.chave])
                dfs(ptr.id, visited, stack);
            else if (stack[ptr.id.chave]) {
                removeEdge(node, ptr.id.chave);
            }
        }
        stack[node.chave] = false;
    }

    private void createEdge(OrdenacaoTopologica.Elo origin, OrdenacaoTopologica.EloSuc newEdge) {
        if (origin.listaSuc != null)
            newEdge.prox = origin.listaSuc;
        origin.listaSuc = newEdge;
    }

    private void removeEdge(OrdenacaoTopologica.Elo node, int key) {
        if (node == null || node.listaSuc == null) return;

        OrdenacaoTopologica.EloSuc lastPtr = null;
        OrdenacaoTopologica.EloSuc toRemove = node.listaSuc;

        while(toRemove != null) {
            if (toRemove.id.chave == key) break;
            lastPtr = toRemove;
            toRemove = toRemove.prox;
        }

        if (toRemove == null) return;

        if (lastPtr != null) lastPtr.prox = toRemove.prox;
        else node.listaSuc = toRemove.prox;
        toRemove.id.contador--;
    }


    private void populateAdjacencyList(OrdenacaoTopologica.Elo[] adjacencyList) {
        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new OrdenacaoTopologica.Elo(i, 0);
            if (i != 0 && adjacencyList[i - 1] != null)
                adjacencyList[i - 1].prox = adjacencyList[i];
        }
    }
}
