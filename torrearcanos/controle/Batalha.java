// Arquivo: torrearcanos/controle/Batalha.java
package torrearcanos.controle;

import torrearcanos.excecoes.FaltaDeManaException;
import torrearcanos.modelo.Feitico;
import torrearcanos.modelo.FeiticoAtaque;
import torrearcanos.modelo.FeiticoDefesa;
import torrearcanos.modelo.Inimigo;
import torrearcanos.modelo.Mago;

/**
 * Gerencia o embate entre um {@link Mago} e um {@link Inimigo}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class Batalha {

    private final Mago mago;
    private final Inimigo inimigo;
    private int turnoAtual;

    /**
     * Cria uma nova batalha entre o mago e o inimigo fornecidos.
     *
     * @param mago    o {@link Mago} controlado pelo jogador
     * @param inimigo o {@link Inimigo} que será enfrentado
     * @throws IllegalArgumentException se qualquer parâmetro for nulo
     */
    public Batalha(Mago mago, Inimigo inimigo) {
        if (mago == null || inimigo == null) {
            throw new IllegalArgumentException("Mago e Inimigo não podem ser nulos.");
        }
        this.mago = mago;
        this.inimigo = inimigo;
        this.turnoAtual = 1;
    }

    /** @return número do turno atual */
    public int getTurnoAtual() { return turnoAtual; }

    /**
     * Informa se a batalha ainda está em andamento.
     *
     * @return true enquanto ambos os combatentes estiverem vivos
     */
    public boolean estaEmAndamento() {
        return mago.estaVivo() && inimigo.estaVivo();
    }

    /**
     * Informa se o mago venceu a batalha.
     *
     * @return true se o inimigo foi derrotado e o mago ainda está vivo
     */
    public boolean magoVenceu() {
        return !inimigo.estaVivo() && mago.estaVivo();
    }

    /**
     * Exibe o cabeçalho do turno com status de ambos os combatentes.
     */
    public void exibirStatusTurno() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf("║           TURNO  %-3d                 ║%n", turnoAtual);
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║  %-15s  VP: %3d / %3d      ║%n",
                mago.getNome(), mago.getVidaAtual(), mago.getVidaMax());
        System.out.printf("║  Mana: %3d                           ║%n", mago.getManaAtual());
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║  %-15s  VP: %3d / %3d      ║%n",
                inimigo.getNome(), inimigo.getVidaAtual(), inimigo.getVidaMax());
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Executa o turno do jogador com o feitiço escolhido.
     *
     * @param feitico o feitiço a ser lançado
     * @throws FaltaDeManaException     se o mago não possuir mana suficiente
     * @throws IllegalArgumentException se feitico for nulo
     */
    public void executarTurnoJogador(Feitico feitico) throws FaltaDeManaException {
        if (feitico == null) {
            throw new IllegalArgumentException("Feitiço não pode ser nulo.");
        }
        if (mago.getManaAtual() < feitico.getCustoMana()) {
            throw new FaltaDeManaException(
                    String.format("Mana insuficiente! '%s' custa %d de mana e você possui apenas %d.",
                            feitico.getNome(), feitico.getCustoMana(), mago.getManaAtual()));
        }
        mago.gastarMana(feitico.getCustoMana());
        feitico.incrementarContador();
        boolean critico = feitico.verificarCritico();

        System.out.printf("%n>>> %s lança '%s'!%n", mago.getNome(), feitico.getNome());
        if (critico) {
            System.out.println("    ★ CRÍTICO! O efeito é DOBRADO! ★");
        }

        if (feitico instanceof FeiticoAtaque) {
            FeiticoAtaque ataque = (FeiticoAtaque) feitico;
            int dano = critico ? ataque.getDano() * 2 : ataque.getDano();
            inimigo.receberDano(dano);
            System.out.printf("    %s recebe %d pontos de dano!%n", inimigo.getNome(), dano);
            if (!inimigo.estaVivo()) {
                System.out.printf("    %s foi derrotado!%n", inimigo.getNome());
            }
        } else if (feitico instanceof FeiticoDefesa) {
            FeiticoDefesa defesa = (FeiticoDefesa) feitico;
            int cura = critico ? defesa.getCura() * 2 : defesa.getCura();
            mago.receberCura(cura);
            System.out.printf("    %s recupera %d pontos de vida!%n", mago.getNome(), cura);
        }
    }

    /**
     * Exibe o próximo ataque que o inimigo irá executar.
     */
    public void anunciarAtaqueInimigo() {
        System.out.printf("%n!!! %s anuncia: %s%n",
                inimigo.getNome(), inimigo.anunciarProximoAtaque());
    }

    /**
     * Executa o turno do inimigo, aplicando seu próximo ataque determinístico ao mago.
     */
    public void executarTurnoInimigo() {
        Inimigo.AcaoInimigo acao = inimigo.executarProximaAcao();
        System.out.printf("%n>>> %s executa '%s'!%n", inimigo.getNome(), acao.getNomeAcao());
        if (acao.getDano() > 0) {
            mago.receberDano(acao.getDano());
            System.out.printf("    %s recebe %d pontos de dano!%n", mago.getNome(), acao.getDano());
            if (!mago.estaVivo()) {
                System.out.printf("    %s foi derrotado...%n", mago.getNome());
            }
        } else {
            System.out.printf("    %s se prepara para o próximo turno.%n", inimigo.getNome());
        }
    }

    /**
     * Avança o contador de turno e regenera mana passiva do mago.
     */
    public void avancarTurno() {
        turnoAtual++;
        mago.regenerarMana(5);
        System.out.printf("    [%s regenera 5 de mana. Mana atual: %d]%n",
                mago.getNome(), mago.getManaAtual());
    }

    /**
     * Exibe a mensagem de encerramento da batalha.
     */
    public void exibirResultado() {
        System.out.println("\n╔══════════════════════════════════════╗");
        if (magoVenceu()) {
            System.out.println("║          ★  VITÓRIA!  ★              ║");
            System.out.printf("║  %s derrotou %s!%n", mago.getNome(), inimigo.getNome());
        } else {
            System.out.println("║          ✖  DERROTA...               ║");
            System.out.printf("║  %s foi derrotado por %s.%n", mago.getNome(), inimigo.getNome());
        }
        System.out.println("╚══════════════════════════════════════╝");
    }
}
