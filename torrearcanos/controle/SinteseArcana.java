// Arquivo: torrearcanos/controle/SinteseArcana.java
package torrearcanos.controle;

import torrearcanos.excecoes.FusaoIncompativelException;
import torrearcanos.modelo.Feitico;
import torrearcanos.modelo.FeiticoAtaque;
import torrearcanos.modelo.FeiticoDefesa;

/**
 * Implementa {@link Combinavel} e realiza a fusão (síntese) entre dois feitiços.
 *
 * Regras de fusão:
 * - Ataque + Ataque: gera novo FeiticoAtaque com dano somado e custo somado.
 * - Defesa + Defesa: gera novo FeiticoDefesa com cura somada e custo somado.
 * - Ataque + Defesa: INCOMPATÍVEL, lança FusaoIncompativelException.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class SinteseArcana implements Combinavel {

    /**
     * Cria uma instância do motor de síntese arcana.
     */
    public SinteseArcana() { }

    /**
     * {@inheritDoc}
     *
     * @throws FusaoIncompativelException se um for de ataque e o outro de defesa,
     *                                    ou se qualquer parâmetro for nulo
     */
    @Override
    public Feitico fundir(Feitico f1, Feitico f2) throws FusaoIncompativelException {
        if (f1 == null || f2 == null) {
            throw new FusaoIncompativelException("Feitiços para fusão não podem ser nulos.");
        }

        boolean ambosAtaque = (f1 instanceof FeiticoAtaque) && (f2 instanceof FeiticoAtaque);
        boolean ambosDefesa = (f1 instanceof FeiticoDefesa) && (f2 instanceof FeiticoDefesa);

        if (ambosAtaque) {
            FeiticoAtaque a1 = (FeiticoAtaque) f1;
            FeiticoAtaque a2 = (FeiticoAtaque) f2;
            String novoNome = a1.getNome() + " & " + a2.getNome();
            int novoCusto = a1.getCustoMana() + a2.getCustoMana();
            int novoDano = a1.getDano() + a2.getDano();
            int novoCritico = Math.min(a1.getUsosParaCritico(), a2.getUsosParaCritico());
            return new FeiticoAtaque(novoNome, novoCusto, novoCritico, novoDano);
        }

        if (ambosDefesa) {
            FeiticoDefesa d1 = (FeiticoDefesa) f1;
            FeiticoDefesa d2 = (FeiticoDefesa) f2;
            String novoNome = d1.getNome() + " & " + d2.getNome();
            int novoCusto = d1.getCustoMana() + d2.getCustoMana();
            int novaCura = d1.getCura() + d2.getCura();
            int novoCritico = Math.min(d1.getUsosParaCritico(), d2.getUsosParaCritico());
            return new FeiticoDefesa(novoNome, novoCusto, novoCritico, novaCura);
        }

        throw new FusaoIncompativelException(
            String.format("Fusão inválida: '%s' (Ataque) e '%s' (Defesa) são incompatíveis. " +
                          "Só é possível fundir feitiços do mesmo tipo.", f1.getNome(), f2.getNome()));
    }
}
