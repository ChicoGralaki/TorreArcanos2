// Arquivo: torrearcanos/modelo/FeiticoAtaque.java
package torrearcanos.modelo;

/**
 * Feitiço ofensivo que causa dano ao alvo.
 * Demonstra polimorfismo ao implementar {@link Feitico#castar()}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class FeiticoAtaque extends Feitico {

    private final int dano;

    /**
     * Constrói um feitiço de ataque.
     *
     * @param nome            nome do feitiço
     * @param custoMana       custo em mana
     * @param usosParaCritico usos necessários para crítico
     * @param dano            dano base causado ao inimigo
     * @throws IllegalArgumentException se dano for negativo
     */
    public FeiticoAtaque(String nome, int custoMana, int usosParaCritico, int dano) {
        super(nome, custoMana, usosParaCritico);
        if (dano < 0) {
            throw new IllegalArgumentException("Dano não pode ser negativo.");
        }
        this.dano = dano;
    }

    /**
     * Retorna o dano base do feitiço.
     *
     * @return dano base (sem modificadores de crítico)
     */
    public int getDano() { return dano; }

    /**
     * Implementação polimórfica: exibe o efeito de ataque no console.
     * O dano real (incluindo crítico) é aplicado pela classe {@code Batalha}.
     */
    @Override
    public void castar() {
        System.out.printf("  [Ataque] '%s' canalizado — dano base: %d%n", getNome(), dano);
    }

    @Override
    public String toString() {
        return String.format("FeiticoAtaque[%s | Mana: %d | Dano: %d | Crítico a cada %d usos]",
                getNome(), getCustoMana(), dano, getUsosParaCritico());
    }
}
