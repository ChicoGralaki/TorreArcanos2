package torrearcanos.excecoes;

/**
 * Exceção lançada quando dois feitiços não podem ser fundidos.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class FusaoIncompativelException extends Exception {

    /**
     * Cria a exceção com mensagem descritiva.
     *
     * @param mensagem detalhe sobre a fusão incompatível
     */
    public FusaoIncompativelException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria a exceção com mensagem e causa raiz.
     *
     * @param mensagem detalhe sobre a fusão incompatível
     * @param causa    exceção original que motivou este lançamento
     */
    public FusaoIncompativelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
