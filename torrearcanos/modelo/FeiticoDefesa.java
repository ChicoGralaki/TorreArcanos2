// Arquivo: torrearcanos/modelo/FeiticoDefesa.java
package torrearcanos.modelo;

/**
 * Feitiço defensivo que cura ou protege o Mago.
 * Demonstra polimorfismo ao implementar {@link Feitico#castar()}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class FeiticoDefesa extends Feitico {

    private final int cura;

    /**
     * Constrói um feitiço de defesa.
     *
     * @param nome            nome do feitiço
     * @param custoMana       custo em mana
     * @param usosParaCritico usos necessários para crítico
     * @param cura            quantidade de pontos de vida restaurados
     * @throws IllegalArgumentException se cura for negativa
     */
    public FeiticoDefesa(String nome, int custoMana, int usosParaCritico, int cura) {
        super(nome, custoMana, usosParaCritico);
        if (cura < 0) {
            throw new IllegalArgumentException("Cura não pode ser negativa.");
        }
        this.cura = cura;
    }

    /**
     * Retorna a quantidade de cura base do feitiço.
     *
     * @return pontos de vida restaurados (sem modificadores de crítico)
     */
    public int getCura() { return cura; }

    /**
     * Implementação polimórfica: exibe o efeito de defesa no console.
     * A cura real (incluindo crítico) é aplicada pela classe {@code Batalha}.
     */
    @Override
    public void castar() {
        System.out.printf("  [Defesa] '%s' ativado — cura base: %d%n", getNome(), cura);
    }

    @Override
    public String toString() {
        return String.format("FeiticoDefesa[%s | Mana: %d | Cura: %d | Crítico a cada %d usos]",
                getNome(), getCustoMana(), cura, getUsosParaCritico());
    }
}
