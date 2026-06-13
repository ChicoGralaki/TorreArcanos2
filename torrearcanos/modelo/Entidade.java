// Arquivo: torrearcanos/modelo/Entidade.java
package torrearcanos.modelo;

/**
 * Classe abstrata que representa qualquer ser vivo no jogo.
 * Define os atributos e comportamentos comuns a Mago e Inimigo.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public abstract class Entidade {

    private final String nome;
    private final int vidaMax;
    private int vidaAtual;

    /**
     * Constrói uma Entidade com nome e pontos de vida.
     *
     * @param nome    nome da entidade; não pode ser nulo ou vazio
     * @param vidaMax pontos de vida máximos; deve ser positivo
     * @throws IllegalArgumentException se nome for nulo/vazio ou vidaMax <= 0
     */
    public Entidade(String nome, int vidaMax) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (vidaMax <= 0) {
            throw new IllegalArgumentException("Vida máxima deve ser positiva.");
        }
        this.nome = nome;
        this.vidaMax = vidaMax;
        this.vidaAtual = vidaMax;
    }

    /**
     * Retorna o nome da entidade.
     *
     * @return nome da entidade
     */
    public String getNome() { return nome; }

    /**
     * Retorna os pontos de vida máximos.
     *
     * @return vida máxima
     */
    public int getVidaMax() { return vidaMax; }

    /**
     * Retorna os pontos de vida atuais.
     *
     * @return vida atual
     */
    public int getVidaAtual() { return vidaAtual; }

    /**
     * Define os pontos de vida atuais, limitando entre 0 e vidaMax.
     *
     * @param vidaAtual novo valor de vida atual
     */
    protected void setVidaAtual(int vidaAtual) {
        this.vidaAtual = Math.max(0, Math.min(vidaMax, vidaAtual));
    }

    /**
     * Aplica dano à entidade, reduzindo a vida atual.
     *
     * @param dano quantidade de dano a receber; valores negativos são ignorados
     */
    public void receberDano(int dano) {
        if (dano > 0) {
            setVidaAtual(this.vidaAtual - dano);
        }
    }

    /**
     * Aplica cura à entidade, aumentando a vida atual até o máximo.
     *
     * @param cura quantidade de cura a receber; valores negativos são ignorados
     */
    public void receberCura(int cura) {
        if (cura > 0) {
            setVidaAtual(this.vidaAtual + cura);
        }
    }

    /**
     * Verifica se a entidade ainda está viva.
     *
     * @return {@code true} se vidaAtual > 0
     */
    public boolean estaVivo() { return this.vidaAtual > 0; }

    /**
     * Retorna uma representação textual da entidade.
     *
     * @return string com nome e status de vida
     */
    @Override
    public String toString() {
        return String.format("%s [VP: %d/%d]", nome, vidaAtual, vidaMax);
    }
}
