import java.util.function.Predicate;

public class CondicaoFiltrarPedido implements Predicate<Pedido> {

    private String  descricaoProcurada;

    public CondicaoFiltrarPedido(String descricao){
        this.descricaoProcurada = descricao;
    }

    public boolean test(Pedido pedido) {
        return pedido.existeNoPedido(new ProdutoNaoPerecivel(descricaoProcurada, 01, 1)) != null;
    }
}
