
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

	public void print()
	{
		Elo p;
		for(p = prim; p != null; p = p.prox)
			System.out.println(p.chave);
	}

	public OrdenacaoTopologica smallerEloSearch(Elo novo, Elo fim, OrdenacaoTopologica lista)
	{
		Elo p = prim;
		Elo q;

		while(p != null)
		{
			q = p;
			p = q.prox;
			if(q.contador == 0)
			{
				if(lista.prim == null)
				{
					q.contador--;
					novo = new Elo(q.chave, q.contador, null, q.listaSuc);
					lista.prim = novo;
				}
				else
				{
					q.contador--;
					novo = new Elo(q.chave, q.contador, null, q.listaSuc);
					fim.prox = novo;
				}

				fim = novo;
			}
		}
		return lista;
	}

	public void reduzSuc()
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

	public Elo retornaUlt(OrdenacaoTopologica lista)
	{
		Elo ult = null;
		Elo p;
		for(p = lista.prim; p != null; p = p.prox)
		{
			if(p.contador == -1)
				ult = p;

		}

		return ult;
	}

	public OrdenacaoTopologica topologicalSort()
	{
		OrdenacaoTopologica lista = new OrdenacaoTopologica();
		Elo novo = prim;
		Elo fim = null;
		int n = 10;


		return topologicalSort(lista, novo, fim, n);
	}

	public OrdenacaoTopologica topologicalSort(OrdenacaoTopologica lista, Elo novo, Elo fim, int n)
	{

		if(n <= 0)
		{
			return lista;
		}
		n--;

		lista = smallerEloSearch(novo, fim, lista);
		reduzSuc();

		fim = retornaUlt(lista);

		return topologicalSort(lista, novo, fim, n);
	}
}
