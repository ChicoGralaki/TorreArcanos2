// Arquivo: torrearcanos/io/GerenciadorArquivo.java
package torrearcanos.io;

import torrearcanos.modelo.Feitico;
import torrearcanos.modelo.FeiticoAtaque;
import torrearcanos.modelo.FeiticoDefesa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia a leitura e escrita de arquivos do jogo.
 *
 * Formato esperado de magias.txt (uma magia por linha):
 * TIPO|NOME|CUSTO_MANA|USOS_CRITICO|EFEITO
 * Exemplo: ATAQUE|Meteoro|25|4|50
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class GerenciadorArquivo {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Cria uma instância do gerenciador de arquivos.
     */
    public GerenciadorArquivo() { }

    /**
     * Lê feitiços de um arquivo texto no formato definido.
     *
     * @param caminhoArquivo caminho do arquivo a ler
     * @return lista de {@link Feitico} parseados com sucesso
     * @throws IOException se o arquivo não puder ser lido
     */
    public List<Feitico> lerMagiasDoArquivo(String caminhoArquivo) throws IOException {
        List<Feitico> magias = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                linha = linha.trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;

                try {
                    Feitico feitico = parsearLinha(linha);
                    if (feitico != null) magias.add(feitico);
                } catch (IllegalArgumentException e) {
                    System.out.printf("[Aviso] Linha %d ignorada em '%s': %s%n",
                            numeroLinha, caminhoArquivo, e.getMessage());
                }
            }
        }
        return magias;
    }

    /**
     * Salva o nome do mago e a fase alcançada em append no arquivo de recordes.
     *
     * @param nomeMago       nome do mago a registrar
     * @param faseAlcancada  andar máximo alcançado na torre
     * @param caminhoArquivo caminho do arquivo de recordes
     * @throws IOException se não for possível escrever no arquivo
     */
    public void salvarRecorde(String nomeMago, int faseAlcancada, String caminhoArquivo)
            throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(caminhoArquivo, true))) {
            String registro = String.format("[%s] %s chegou ao andar %d da Torre dos Arcanos.%n",
                    LocalDateTime.now().format(FORMATO_DATA), nomeMago, faseAlcancada);
            escritor.write(registro);
        }
    }

    /**
     * Parseia uma linha do arquivo de magias no formato TIPO|NOME|CUSTO|CRITICO|EFEITO.
     *
     * @param linha linha de texto a parsear
     * @return {@link Feitico} construído, ou {@code null} se a linha for inválida
     * @throws IllegalArgumentException se o formato ou valores forem inválidos
     */
    private Feitico parsearLinha(String linha) {
        String[] partes = linha.split("\\|");
        if (partes.length != 5) {
            throw new IllegalArgumentException(
                    "Formato inválido. Esperado: TIPO|NOME|CUSTO|CRITICO|EFEITO");
        }
        String tipo  = partes[0].trim().toUpperCase();
        String nome  = partes[1].trim();
        int custo    = Integer.parseInt(partes[2].trim());
        int critico  = Integer.parseInt(partes[3].trim());
        int efeito   = Integer.parseInt(partes[4].trim());

        switch (tipo) {
            case "ATAQUE":  return new FeiticoAtaque(nome, custo, critico, efeito);
            case "DEFESA":  return new FeiticoDefesa(nome, custo, critico, efeito);
            default: throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        }
    }
}
