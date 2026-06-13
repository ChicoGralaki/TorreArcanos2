// Arquivo: torrearcanos/modelo/Inimigo.java
package torrearcanos.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um inimigo com padrão de ataque determinístico.
 * Herda de {@link Entidade} e possui uma fila circular de {@link AcaoInimigo}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class Inimigo extends Entidade {

    private final List<AcaoInimigo> padraoAtaque;
    private int indiceAcaoAtual;

    /**
     * Constrói um inimigo com padrão de ataque predefinido.
     *
     * @param nome         nome do inimigo
     * @param vidaMax      pontos de vida máximos
     * @param padraoAtaque lista de ações a executar em sequência circular
     * @throws IllegalArgumentException se padraoAtaque for nulo ou vazio
     */
    public Inimigo(String nome, int vidaMax, List<AcaoInimigo> padraoAtaque) {
        super(nome, vidaMax);
        if (padraoAtaque == null || padraoAtaque.isEmpty()) {
            throw new IllegalArgumentException("Padrão de ataque não pode ser nulo ou vazio.");
        }
        this.padraoAtaque = new ArrayList<>(padraoAtaque);
        this.indiceAcaoAtual = 0;
    }

    /**
     * Anuncia a próxima ação que o inimigo executará (sem avançar o índice).
     *
     * @return descrição textual da próxima ação
     */
    public String anunciarProximoAtaque() {
        AcaoInimigo proxima = padraoAtaque.get(indiceAcaoAtual);
        if (proxima.getDano() > 0) {
            return String.format("'%s' causará %d de dano!", proxima.getNomeAcao(), proxima.getDano());
        }
        return String.format("'%s' — está se preparando...", proxima.getNomeAcao());
    }

    /**
     * Executa a próxima ação do padrão determinístico e avança o índice.
     *
     * @return a {@link AcaoInimigo} executada neste turno
     */
    public AcaoInimigo executarProximaAcao() {
        AcaoInimigo acao = padraoAtaque.get(indiceAcaoAtual);
        indiceAcaoAtual = (indiceAcaoAtual + 1) % padraoAtaque.size();
        return acao;
    }

    /**
     * Representa uma ação individual do inimigo no padrão determinístico.
     */
    public static class AcaoInimigo {

        private final String nomeAcao;
        private final int dano;

        /**
         * Cria uma ação do inimigo.
         *
         * @param nomeAcao nome descritivo da ação
         * @param dano     dano causado (0 para ações de preparação)
         */
        public AcaoInimigo(String nomeAcao, int dano) {
            this.nomeAcao = nomeAcao;
            this.dano = Math.max(0, dano);
        }

        /**
         * @return nome da ação
         */
        public String getNomeAcao() { return nomeAcao; }

        /**
         * @return dano da ação (0 se for preparação)
         */
        public int getDano() { return dano; }

        @Override
        public String toString() {
            return String.format("Ação[%s | Dano: %d]", nomeAcao, dano);
        }
    }
}
