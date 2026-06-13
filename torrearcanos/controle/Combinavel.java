// Arquivo: torrearcanos/controle/Combinavel.java
package torrearcanos.controle;

import torrearcanos.excecoes.FusaoIncompativelException;
import torrearcanos.modelo.Feitico;

/**
 * Interface que define o contrato de fusão entre dois feitiços.
 * Implementada por {@link SinteseArcana}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public interface Combinavel {

    /**
     * Funde dois feitiços em um novo feitiço resultante.
     *
     * @param f1 primeiro feitiço a ser fundido; não pode ser nulo
     * @param f2 segundo feitiço a ser fundido; não pode ser nulo
     * @return novo {@link Feitico} resultante da fusão
     * @throws FusaoIncompativelException se os feitiços não puderem ser combinados
     */
    Feitico fundir(Feitico f1, Feitico f2) throws FusaoIncompativelException;
}
