// Arquivo: torrearcanos/visao/MotorTextual.java
package torrearcanos.visao;

import torrearcanos.controle.Batalha;
import torrearcanos.controle.DefinicaoMagias;
import torrearcanos.excecoes.FaltaDeManaException;
import torrearcanos.io.GerenciadorArquivo;
import torrearcanos.modelo.Feitico;
import torrearcanos.modelo.FeiticoAtaque;
import torrearcanos.modelo.FeiticoDefesa;
import torrearcanos.modelo.Grimorio;
import torrearcanos.modelo.Inimigo;
import torrearcanos.modelo.Mago;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do jogo "A Torre dos Arcanos".
 * Contém o método main e gerencia o loop de fases e a interface textual.
 *
 * @author TorreArcanos
 * @version 1.1
 */
public class MotorTextual {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static GerenciadorArquivo gerenciadorArquivo;
    private static DefinicaoMagias definicaoMagias;

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        exibirTitulo();
        inicializarSistemas();
        Mago mago = criarMago();
        int faseAtual = 1;
        boolean jogando = true;

        while (jogando) {
            System.out.printf("%n%n══════════════════════════════════════%n");
            System.out.printf("            FASE %d — ANDAR %d DA TORRE%n", faseAtual, faseAtual);
            System.out.printf("══════════════════════════════════════%n");
            Inimigo inimigo = gerarInimigo(faseAtual);
            System.out.printf("Um inimigo aparece: %s (VP: %d)%n%n",
                    inimigo.getNome(), inimigo.getVidaMax());
            Batalha batalha = new Batalha(mago, inimigo);

            while (batalha.estaEmAndamento()) {
                batalha.exibirStatusTurno();
                batalha.anunciarAtaqueInimigo();
                menuTurno(batalha, mago);
                if (!batalha.estaEmAndamento()) break;
                batalha.executarTurnoInimigo();
                batalha.avancarTurno();
                pausar();
            }

            batalha.exibirResultado();

            if (batalha.magoVenceu()) {
                faseAtual++;
                mago.curarTotal();
                System.out.println("\n✦ Você sobe mais um andar. Sua vida foi restaurada! ✦");
                System.out.println("Pressione ENTER para continuar...");
                SCANNER.nextLine();
            } else {
                jogando = false;
                salvarRecorde(mago.getNome(), faseAtual);
            }
        }

        System.out.printf("%n%s, obrigado por jogar A Torre dos Arcanos.%n", mago.getNome());
        System.out.println("Até a próxima, Arcano.");
        SCANNER.close();
    }

    /**
     * Inicializa os sistemas de suporte: arquivos e magias.
     */
    private static void inicializarSistemas() {
        gerenciadorArquivo = new GerenciadorArquivo();
        List<Feitico> magiasDisco = new ArrayList<>();
        try {
            magiasDisco = gerenciadorArquivo.lerMagiasDoArquivo("magias.txt");
            System.out.printf("[Sistema] %d magia(s) carregada(s) de 'magias.txt'.%n",
                    magiasDisco.size());
        } catch (IOException e) {
            System.out.println("[Sistema] 'magias.txt' não encontrado. Usando magias padrão.");
        }
        definicaoMagias = new DefinicaoMagias();
        definicaoMagias.registrarMagiasExternas(magiasDisco);
    }

    /**
     * Solicita o nome do jogador e cria o {@link Mago} com grimório inicial.
     *
     * @return o {@link Mago} pronto para jogar
     */
    private static Mago criarMago() {
        System.out.print("\nDigite o nome do seu Arcano: ");
        String nome = SCANNER.nextLine().trim();
        if (nome.isEmpty()) nome = "Arcano";
        Grimorio grimorio = new Grimorio();
        grimorio.adicionarFeitico(definicaoMagias.obterMagia("Bola de Fogo"));
        grimorio.adicionarFeitico(definicaoMagias.obterMagia("Escudo Arcano"));
        grimorio.adicionarFeitico(definicaoMagias.obterMagia("Raio"));
        Mago mago = new Mago(nome, 100, 80, grimorio);
        System.out.printf("%nBem-vindo, %s! Grimório inicial: %d feitiços.%n",
                mago.getNome(), mago.getGrimorio().quantidadeFeiticos());
        return mago;
    }

    /**
     * Gera um {@link Inimigo} escalado para a fase informada.
     *
     * @param fase número da fase atual (maior ou igual a 1)
     * @return novo {@link Inimigo} configurado para a fase
     */
    private static Inimigo gerarInimigo(int fase) {
        String[] nomes = {"Golem de Pedra", "Espectro Sombrio", "Dragão Menor",
                          "Lich Corrompido", "Titã das Ruínas"};
        String nome = nomes[Math.min(fase - 1, nomes.length - 1)];
        int vida = 60 + (fase - 1) * 20;
        int danoBase = 10 + (fase - 1) * 4;
        List<Inimigo.AcaoInimigo> acoes = new ArrayList<>();
        acoes.add(new Inimigo.AcaoInimigo("Golpe Brutal",   danoBase));
        acoes.add(new Inimigo.AcaoInimigo("Preparar Força", 0));
        acoes.add(new Inimigo.AcaoInimigo("Ataque Pesado",  danoBase * 2));
        acoes.add(new Inimigo.AcaoInimigo("Golpe Brutal",   danoBase));
        return new Inimigo(nome, vida, acoes);
    }

    /**
     * Exibe e processa o menu de ações disponíveis para o jogador no turno atual.
     * Opções: [1] Lançar Feitiço, [2] Ver Grimório.
     *
     * @param batalha a {@link Batalha} em andamento
     * @param mago    o {@link Mago} que está agindo
     */
    private static void menuTurno(Batalha batalha, Mago mago) {
        boolean acaoRealizada = false;
        while (!acaoRealizada) {
            System.out.println("\n--- O QUE VOCÊ FAZ? ---");
            System.out.println("  [1] Lançar Feitiço");
            System.out.println("  [2] Ver Grimório");
            System.out.print("Escolha: ");
            String entrada = SCANNER.nextLine().trim();
            switch (entrada) {
                case "1": acaoRealizada = menuLancarFeitico(batalha, mago); break;
                case "2": exibirGrimorio(mago); break;
                default:  System.out.println("[!] Opção inválida. Digite 1 ou 2.");
            }
        }
    }

    /**
     * Permite ao jogador escolher e lançar um feitiço do grimório.
     * Captura {@link FaltaDeManaException} e exibe a mensagem de erro.
     *
     * @param batalha a {@link Batalha} em andamento
     * @param mago    o {@link Mago} que lançará o feitiço
     * @return {@code true} se a ação foi concluída com sucesso
     */
    private static boolean menuLancarFeitico(Batalha batalha, Mago mago) {
        List<Feitico> feiticos = mago.getGrimorio().listarFeiticos();
        if (feiticos.isEmpty()) {
            System.out.println("[!] Seu grimório está vazio!");
            return false;
        }
        System.out.println("\n-- Escolha o Feitiço --");
        for (int i = 0; i < feiticos.size(); i++) {
            Feitico f = feiticos.get(i);
            String tipo = (f instanceof FeiticoAtaque) ? "ATQ" : "DEF";
            System.out.printf("  [%d] %-20s  Mana: %2d  Usos p/Crítico: %d  (%s)%n",
                    i + 1, f.getNome(), f.getCustoMana(), f.getUsosParaCritico(), tipo);
        }
        System.out.print("Número do feitiço (0 = voltar): ");
        try {
            int escolha = Integer.parseInt(SCANNER.nextLine().trim());
            if (escolha == 0) return false;
            if (escolha < 1 || escolha > feiticos.size()) {
                System.out.println("[!] Número inválido.");
                return false;
            }
            try {
                batalha.executarTurnoJogador(feiticos.get(escolha - 1));
                return true;
            } catch (FaltaDeManaException e) {
                System.out.println("[✗] " + e.getMessage());
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Entrada inválida. Digite um número.");
            return false;
        }
    }

    /**
     * Exibe todos os feitiços presentes no grimório do mago com seus atributos.
     *
     * @param mago o {@link Mago} cujo grimório será listado
     */
    private static void exibirGrimorio(Mago mago) {
        System.out.println("\n╔══════════ GRIMÓRIO ══════════╗");
        List<Feitico> lista = mago.getGrimorio().listarFeiticos();
        if (lista.isEmpty()) {
            System.out.println("║  (vazio)                     ║");
        } else {
            for (Feitico f : lista) {
                String tipo   = (f instanceof FeiticoAtaque) ? "Ataque" : "Defesa";
                int efeito    = (f instanceof FeiticoAtaque)
                        ? ((FeiticoAtaque) f).getDano()
                        : ((FeiticoDefesa) f).getCura();
                System.out.printf("║  %-18s [%s]  ║%n", f.getNome(), tipo);
                System.out.printf("║    Mana: %2d | Efeito: %2d     ║%n",
                        f.getCustoMana(), efeito);
                System.out.printf("║    Crítico a cada %d usos     ║%n",
                        f.getUsosParaCritico());
                System.out.printf("║    Usos acumulados: %2d        ║%n",
                        f.getContadorUso());
                System.out.println("║  ————————————————————————    ║");
            }
        }
        System.out.println("╚══════════════════════════════╝");
    }

    /**
     * Salva o recorde do jogador no arquivo recordes.txt.
     *
     * @param nomeMago      nome do mago a ser registrado
     * @param faseAlcancada número da fase em que o jogador foi derrotado
     */
    private static void salvarRecorde(String nomeMago, int faseAlcancada) {
        try {
            gerenciadorArquivo.salvarRecorde(nomeMago, faseAlcancada, "recordes.txt");
            System.out.printf("%n[Sistema] Recorde salvo: %s chegou ao andar %d.%n",
                    nomeMago, faseAlcancada);
        } catch (IOException e) {
            System.out.println("[Sistema] Não foi possível salvar o recorde: " + e.getMessage());
        }
    }

    /**
     * Exibe o título ASCII do jogo na abertura.
     */
    private static void exibirTitulo() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║                                          ║");
        System.out.println("║      A   T O R R E   D O S              ║");
        System.out.println("║          A R C A N O S                  ║");
        System.out.println("║                                          ║");
        System.out.println("║      Roguelike de Turnos — v1.1          ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Aguarda o jogador pressionar ENTER para continuar.
     */
    private static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        SCANNER.nextLine();
    }
}
