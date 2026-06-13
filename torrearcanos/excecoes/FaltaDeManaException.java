// Arquivo: torrearcanos/excecoes/FaltaDeManaException.java
package torrearcanos.excecoes;

/**
 * Exceção lançada quando o Mago tenta usar um feitiço sem mana suficiente.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class FaltaDeManaException extends Exception {

    /**
     * Cria a exceção com mensagem descritiva.
     *
     * @param mensagem detalhe sobre o feitiço e os valores de mana envolvidos
     */
    public FaltaDeManaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria a exceção com mensagem e causa raiz.
     *
     * @param mensagem detalhe sobre a falta de mana
     * @param causa    exceção original que motivou este lançamento
     */
    public FaltaDeManaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
