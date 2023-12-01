/**
 * Itera de 0 até N criando novos grafos, checando se os anteriores devem ser ligados ao novo com base na porcentagem,
 * se ele está direcionado e se o grafo permanece acíclico.
 *
 * @param n Quantidade de nós no grafo
 * @param p Chance em porcentagem de 0 a 1 da conexão entre um novo grafo e os anteriores
 */
public class GraphGenerator implements Runnable {
    private final int n;
    private final float p;
    

    public GraphGenerator(int n, int p) {
        this.n = n;
        this.p = p;
    }

    @Override
    public void run() {

    }
}
