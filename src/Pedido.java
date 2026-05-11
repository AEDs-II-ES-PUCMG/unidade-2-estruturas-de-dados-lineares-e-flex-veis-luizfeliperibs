import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pedido implements Comparable<Pedido> {

	private static int ultimoID = 1;

	/** Quantidade máxima de itens de um pedido */
	private static final int MAX_ITENS_DE_PEDIDO = 10;

	/** Porcentagem de desconto para pagamentos à vista */
	private static final double DESCONTO_PG_A_VISTA = 0.15;

	private int idPedido;

	/** Vetor para armazenar os itens do pedido */
	private Lista<ItemDePedido> itensDePedido = new Lista<>();

	/** Data de criação do pedido */
	private LocalDate dataPedido;

	/** Indica a quantidade total de itens no pedido até o momento */
	private int quantItensDePedido = 0;

	/** Indica a forma de pagamento do pedido sendo: 1, pagamento à vista; 2, pagamento parcelado */
	private int formaDePagamento;

	/** Construtor do pedido.
	 *  Deve criar o vetor de itens do pedido,
	 *  armazenar a data e a forma de pagamento informadas para o pedido.
	 */
	public Pedido(LocalDate dataPedido, int formaDePagamento) {

		idPedido = ultimoID++;
		quantItensDePedido = 0;
		this.dataPedido = dataPedido;
		this.formaDePagamento = formaDePagamento;
	}

	// TODO: Tarefa 4 - Substituir o vetor itensDePedido por uma Lista<ItemDePedido>
	//       e adaptar o construtor para inicializá-la.
	public Lista<ItemDePedido> getItensDoPedido() {
		return itensDePedido;
	}

	public ItemDePedido existeNoPedido(Produto produto) {

		// TODO: Tarefa 4 - Substituir o laço abaixo pelo método buscarPor da Lista<E>.
		ItemDePedido itemDePedidoProcurado = new ItemDePedido(produto, 0, 0.1);
		return itensDePedido.buscarPor(new CriterioDeBuscaPorDescricao(), itemDePedidoProcurado);
	}

	/**
     * Inclui produtos no pedido. Se necessário, aumenta a quantidade de itens armazenados no pedido até o momento.
     * Caso o produto já exista no pedido, sua quantidade é atualizada.
     * Caso contrário, um novo item de pedido é criado, desde que haja espaço disponível.
     */
	public boolean incluirProduto(Produto novo, int quantidade) {

		// TODO: Tarefa 4 - Substituir a lógica de array pelo método inserirFinal da Lista<E>.
		ItemDePedido itemExistente = existeNoPedido(novo);

		if (itemExistente != null) {
			itemExistente.setQuantidade(quantidade + itemExistente.getQuantidade());
			return true;
		} else {
			itensDePedido.inserirFinal(new ItemDePedido(novo, quantidade, novo.valorDeVenda()));;
			return true;
		}

	}

	/**
     * Calcula e retorna o valor final do pedido (soma do valor de venda de todos os itens do pedido).
     * Caso a forma de pagamento do pedido seja à vista, aplica o desconto correspondente.
     */
	public double valorFinal() {

		// TODO: Tarefa 4 - Substituir o laço abaixo pelo método somarMultiplicacoes da Lista<E>.
		if (itensDePedido.vazia()){
			return 0;
		}

		double valorTotal = itensDePedido.somarMultiplicacoes(ItemDePedido :: getPrecoVenda, ItemDePedido :: getQuantidade);

		BigDecimal valorPedidoBD = new BigDecimal(Double.toString(valorTotal));
		valorPedidoBD = valorPedidoBD.setScale(2, RoundingMode.HALF_UP);
		return valorPedidoBD.doubleValue();
	}

	@Override
	public String toString() {

		// TODO: Tarefa 4 - Adaptar a iteração abaixo para percorrer a Lista<ItemDePedido>
		//       e usar tamanho() no lugar de quantItensDePedido.
		StringBuilder stringPedido = new StringBuilder();

		stringPedido.append("==============================\n");
		stringPedido.append("ID do pedido: " + idPedido + "\n");

		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		stringPedido.append("Data do pedido: " + formatoData.format(dataPedido) + "\n");

		stringPedido.append("Pedido com " + itensDePedido.tamanho() + " itens.\n");
		stringPedido.append("Itens de pedido no pedido:\n");
		for (int i = 0; i < itensDePedido.tamanho(); i++) {
			stringPedido.append(itensDePedido.toString() + "\n");
		}

		stringPedido.append("Pedido pago ");
		if (formaDePagamento == 1) {
			stringPedido.append("à vista. Percentual de desconto: " + String.format("%.2f", DESCONTO_PG_A_VISTA * 100) + "%\n");
		} else {
			stringPedido.append("parcelado.\n");
		}

		stringPedido.append("Valor total do pedido: R$ " + String.format("%.2f", valorFinal()));

		return stringPedido.toString();
	}

	@Override
	public int hashCode() {
		return idPedido;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if ((obj == null) || (!(obj instanceof Pedido))) return false;
		Pedido outro = (Pedido) obj;
		return this.hashCode() == outro.hashCode();
	}

	@Override
	public int compareTo(Pedido outroPedido) {
		return (this.hashCode() - outroPedido.hashCode());
	}
}
