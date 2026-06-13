// Arquivo: torrearcanos/modelo/Mago.java
package torrearcanos.modelo;

/**
 * Representa o personagem jogável.
 * Herda de {@link Entidade} e possui um {@link Grimorio} por Composição.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class Mago extends Entidade {

    private int manaMax;
    private int manaAtual;
    private final Grimorio grimorio;

    /**
     * Constrói um Mago completo.
     *
     * @param nome     nome do mago
     * @param vidaMax  pontos de vida máximos
     * @param manaMax  pontos de mana máximos
     * @param grimorio grimório do mago (Composição); não pode ser nulo
     * @throws IllegalArgumentException se grimório for nulo ou manaMax <= 0
     */
    public Mago(String nome, int vidaMax, int manaMax, Grimorio grimorio) {
        super(nome, vidaMax);
        if (grimorio == null) {
            throw new IllegalArgumentException("Grimório não pode ser nulo.");
        }
        if (manaMax <= 0) {
            throw new IllegalArgumentException("Mana máxima deve ser positiva.");
        }
        this.manaMax = manaMax;
        this.manaAtual = manaMax;
        this.grimorio = grimorio;
    }

    /** @return mana máxima do mago */
    public int getManaMax() { return manaMax; }

    /** @return mana atual do mago */
    public int getManaAtual() { return manaAtual; }

    /** @return grimório do mago */
    public Grimorio getGrimorio() { return grimorio; }

    /**
     * Gasta a quantidade especificada de mana.
     *
     * @param quantidade mana a gastar; valores negativos são ignorados
     */
    public void gastarMana(int quantidade) {
        if (quantidade > 0) {
            this.manaAtual = Math.max(0, this.manaAtual - quantidade);
        }
    }

    /**
     * Regenera a quantidade especificada de mana, respeitando o máximo.
     *
     * @param quantidade mana a regenerar; valores negativos são ignorados
     */
    public void regenerarMana(int quantidade) {
        if (quantidade > 0) {
            this.manaAtual = Math.min(manaMax, this.manaAtual + quantidade);
        }
    }

    /**
     * Restaura vida e mana ao máximo (usado entre fases).
     */
    public void curarTotal() {
        setVidaAtual(getVidaMax());
        this.manaAtual = this.manaMax;
    }

    @Override
    public String toString() {
        return String.format("Mago[%s | VP: %d/%d | Mana: %d/%d | Feitiços: %d]",
                getNome(), getVidaAtual(), getVidaMax(),
                manaAtual, manaMax, grimorio.quantidadeFeiticos());
    }
}
