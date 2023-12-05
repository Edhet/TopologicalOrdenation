
public class OrdenacaoTopologica
{
	private final boolean debug;

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

	public OrdenacaoTopologica(Elo prim, int n, boolean debug)
	{
		this.debug = debug;
		this.prim = prim;
		this.n = n;
	}
	
	/* Método responsável pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada)
	{
		/* Preencher. */
	}
	
	/* Método para impressão do estado atual da estrutura de dados. */
	private void debug()
	{
		if (!debug) return;
		Elo p;

		System.out.print("[DEBUG]: Sorted List: { ");
		for(p = prim; p != null; p = p.prox)
			System.out.print(p.chave + (p.prox == null ? "" : ", "));
		System.out.println(" }");
	}
	
	/* Método responsável por executar o algoritmo. */
	public boolean executa()
	{
		OrdenacaoTopologica sortedList;
		sortedList = topologicalSort();
		prim = sortedList.prim;

		debug();
		
		return true;
	}

	public OrdenacaoTopologica eloSearch(Elo lastElo, OrdenacaoTopologica list)
	{
		Elo newElo;
		Elo p = prim;
		Elo q;

		while(p != null)
		{
			q = p;
			p = q.prox;
			if(q.contador == 0)
			{
				if(list.prim == null)
				{
					q.contador--;
					newElo = new Elo(q.chave, q.contador, null, q.listaSuc);
					list.prim = newElo;
				}
				else
				{
					q.contador--;
					newElo = new Elo(q.chave, q.contador, null, q.listaSuc);
					lastElo.prox = newElo;
				}

				lastElo = newElo;
			}
		}
		return list;
	}

	public void decreaseSucCont()
	{
		Elo p = prim;

		while(p != null) {
			if(p.contador == -1)
			{
				while(p.listaSuc != null)
				{
					if(p.listaSuc.id != null)
						p.listaSuc.id.contador--;
					p.listaSuc = p.listaSuc.prox;
				}
			}
			p = p.prox;
		}
	}

	public Elo returnLastElo(OrdenacaoTopologica list)
	{
		Elo lastElo = null;
		Elo p;
		for(p = list.prim; p != null; p = p.prox)
		{
			if(p.contador == -1)
				lastElo = p;
		}
		return lastElo;
	}

	public OrdenacaoTopologica topologicalSort()
	{
		OrdenacaoTopologica list = new OrdenacaoTopologica(null, n, false);
		Elo fim = null;

		return topologicalSort(list, fim, n);
	}

	public OrdenacaoTopologica topologicalSort(OrdenacaoTopologica list, Elo fim, int n)
	{
		if(n <= 0)
		{
			return list;
		}
		n--;

		list = eloSearch(fim, list);
		decreaseSucCont();
		fim = returnLastElo(list);

		return topologicalSort(list, fim, n);
	}
}
