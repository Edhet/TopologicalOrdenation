
public class OrdenacaoTopologica
{

	public static class Elo
	{
		/* Identificação do elemento. */
		public int chave;
		
		/* Número de predecessores. */
		public int contador;
		
		/* Aponta para o próximo elo da lista. */
		public Elo prox;
		
		/* Aponta para o primeiro elemento da lista de sucessores. */
		public EloSuc listaSuc;
		
		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}

		public Elo(int chave, int contador)
		{
			this.chave = chave;
			this.contador = contador;
		}
	}
	
	public static class EloSuc
	{
		/* Aponta para o elo que é sucessor. */
		public Elo id;
		
		/* Aponta para o próximo elemento. */
		public EloSuc prox;
		
		public EloSuc()
		{
			id = null;
			prox = null;
		}
		
		public EloSuc(Elo id, EloSuc prox)
		{
			this.id = id;
			this.prox = prox;
		}
	}


	/* Ponteiro (referência) para primeiro elemento da lista. */
	public Elo prim;
	
	/* Número de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
	
	/* Método responsável pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada)
	{
		/* Preencher. */
	}
	
	/* Método para impressão do estado atual da estrutura de dados. */
	private void debug()
	{
		/* Preencher. */
	}
	
	/* Método responsável por executar o algoritmo. */
	public boolean executa()
	{
		/* Preencher. */
		
		return false;
	}

	public Elo smallerEloSearch()
	{
		Elo p;
		Elo q = null;

		p = prim;
		prim = null;

		while(p != null)
		{
			q = p;
			p = q.prox;
			if(q.contador == 0)
			{
				q.prox = prim;
				prim = q;
			}
		}
		return q;
	}

	public void topologicalSort()
	{
		Elo q;
		EloSuc aux;

		while(n > 0)
		{
			q = smallerEloSearch();

			System.out.println(q.chave);
			n--;
			q.listaSuc.id.contador--;
			if (q.listaSuc.id.contador == 0)
			{
				q.prox = q.listaSuc.id;
				q.listaSuc.id = q.listaSuc.prox.id;
			}

			aux = q.listaSuc.prox;
			while(aux.prox != null) {
				aux.id.contador--;
				if (aux.id.contador == 0)
					q.prox = aux.id;
				aux.prox = aux.prox.prox;

				aux = aux.prox;
			}

		}
	}

	public void print()
	{
		Elo p;
		for(p = prim; p != null; p = p.prox)
			System.out.println(p.chave);
	}
}
