import java.util.NoSuchElementException;

public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;

	public Pilha() {

		Celula<E> sentinela = new Celula<E>();
		fundo = sentinela;
		topo = sentinela;

	}

	public boolean vazia() {
		return fundo == topo;
	}

	public void empilhar(E item) {

		topo = new Celula<E>(item, topo);
	}

	public E desempilhar() {

		E desempilhado = consultarTopo();
		topo = topo.getProximo();
		return desempilhado;

	}

	public E consultarTopo() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na pilha!");
		}

		return topo.getItem();

	}

	public void imprimePilha(Pilha<E> pilha){

		Celula<E> aux = topo;
		
		while(aux != fundo){
			System.out.println(aux.getItem());
			aux = aux.getProximo();
		}

	}

	void imprimeCerto(){
		Celula<E> atual = topo;
		certo(atual);
	}

	void certo(Celula <E> atual){
		if (atual != fundo) {
			certo(atual.getProximo());
			System.out.println(atual.getItem());
		}
	}

	public Pilha<E> subPilha(int numItens) {

		int total = 0;
		Celula<E> temp = topo;

		while (temp != null) {
			total++;
			temp = temp.getProximo();
		}
		if (numItens > total) {
			throw new IllegalArgumentException("A pilha não contém elementos suficientes.");
		}

		Celula<E> atual = topo;
		int count = 0;

		Pilha<E> pilhaAux = new Pilha<>();
		Pilha<E> novaPilha = new Pilha<>();

		while (count < numItens) {
			pilhaAux.empilhar(atual.getItem());
			atual = atual.getProximo();
			count++;
		}
		
		while (!pilhaAux.vazia()) {
			novaPilha.empilhar(pilhaAux.desempilhar());
		}

		return novaPilha;
	}
}