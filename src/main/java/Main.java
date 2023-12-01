import Demos.Constants;
import Demos.EratosthenesSieveDemo;

public class Main {

    public static void main(String[] args) {
        OrdenacaoTopologica ord = new OrdenacaoTopologica();
		OrdenacaoTopologica lista = new OrdenacaoTopologica();

		OrdenacaoTopologica.Elo elo7 = new OrdenacaoTopologica.Elo(7,0);
		OrdenacaoTopologica.Elo elo9 = new OrdenacaoTopologica.Elo(9,1);
		OrdenacaoTopologica.Elo elo1 = new OrdenacaoTopologica.Elo(1,0);
		OrdenacaoTopologica.Elo elo2 = new OrdenacaoTopologica.Elo(2,1);
		OrdenacaoTopologica.Elo elo4 = new OrdenacaoTopologica.Elo(4,2);
		OrdenacaoTopologica.Elo elo6 = new OrdenacaoTopologica.Elo(6,1);
		OrdenacaoTopologica.Elo elo3 = new OrdenacaoTopologica.Elo(3,2);
		OrdenacaoTopologica.Elo elo5 = new OrdenacaoTopologica.Elo(5,2);
		OrdenacaoTopologica.Elo elo8 = new OrdenacaoTopologica.Elo(8,2);
		OrdenacaoTopologica.Elo elo10 = new OrdenacaoTopologica.Elo(10,2);

		OrdenacaoTopologica.EloSuc elo7S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo9S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo1S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo2S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo4S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo6S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo3S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo5S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo8S = new OrdenacaoTopologica.EloSuc();
		OrdenacaoTopologica.EloSuc elo10S = new OrdenacaoTopologica.EloSuc();

		ord.prim = elo7;

		elo7.prox = elo9;
		elo7.listaSuc = elo7S;

		elo9.prox = elo1;
		elo9.listaSuc = elo9S;

		elo1.prox = elo2;
		elo1.listaSuc = elo1S;

		elo2.prox = elo4;
		elo2.listaSuc = elo2S;

		elo4.prox = elo6;
		elo4.listaSuc = elo4S;

		elo6.prox = elo3;
		elo6.listaSuc = elo6S;

		elo3.prox = elo5;
		elo3.listaSuc = elo3S;

		elo5.prox = elo8;
		elo5.listaSuc = elo5S;

		elo8.prox = elo10;
		elo8.listaSuc = elo8S;

		elo10.prox = null;
		elo10.listaSuc = elo10S;


		elo7S.id = elo9;
		elo7S.prox = elo5S;

		elo9S.id = elo4;
		elo9S.prox = elo10S;

		elo1S.id = elo2;
		elo1S.prox = elo3S;

		elo2S.id = elo4;
		elo2S.prox = elo10S;

		elo4S.id = elo6;
		elo4S.prox = elo8S;

		elo6S.id = elo3;
		elo6S.prox = null;

		elo3S.id = elo5;
		elo3S.prox = null;

		elo5S.id = elo8;
		elo5S.prox = null;

		elo8S.id = null;
		elo8S.prox = null;

		elo10S.id = null;
		elo10S.prox = null;




		ord.topologicalSort();

		ord.print();
    }
}
