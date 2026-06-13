// Arquivo: torrearcanos/controle/DefinicaoMagias.java
package torrearcanos.controle;

import torrearcanos.modelo.Feitico;
import torrearcanos.modelo.FeiticoAtaque;
import torrearcanos.modelo.FeiticoDefesa;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Banco de dados em memória de todos os feitiços disponíveis no jogo.
 * Utiliza um {@link HashMap} com o nome do feitiço como chave.
 *
 * @author TorreArcanos
 * @version 1.1
 */
public class DefinicaoMagias {

    private final Map<String, Feitico> banco;

    /**
     * Inicializa o banco de magias com todos os feitiços padrão do jogo.
     */
    public DefinicaoMagias() {
        banco = new HashMap<>();
        registrarMagiasPadrao();
    }

    /**
     * Registra no banco todos os feitiços padrão do jogo.
     */
    private void registrarMagiasPadrao() {
        registrar(new FeiticoAtaque("Bola de Fogo",      10, 3, 20));
        registrar(new FeiticoAtaque("Raio",              12, 4, 25));
        registrar(new FeiticoAtaque("Gelo Afiado",        8, 2, 15));
        registrar(new FeiticoAtaque("Tempestade Arcana",  20, 5, 40));
        registrar(new FeiticoAtaque("Flecha Mágica",       5, 3, 10));
        registrar(new FeiticoDefesa("Escudo Arcano",      10, 3, 20));
        registrar(new FeiticoDefesa("Cura Menor",          8, 2, 15));
        registrar(new FeiticoDefesa("Regeneração",        15, 4, 30));
        registrar(new FeiticoDefesa("Barreira de Luz",    12, 3, 25));
    }

    /**
     * Adiciona um feitiço ao banco pelo seu nome como chave.
     *
     * @param feitico feitiço a registrar; ignorado se nulo
     */
    private void registrar(Feitico feitico) {
        if (feitico != null) {
            banco.put(feitico.getNome(), feitico);
        }
    }

    /**
     * Registra feitiços carregados de fonte externa (ex.: arquivo),
     * sem sobrescrever entradas já existentes.
     *
     * @param magias coleção de {@link Feitico} lidos do disco
     */
    public void registrarMagiasExternas(Collection<Feitico> magias) {
        if (magias == null) return;
        for (Feitico f : magias) {
            if (f != null) banco.putIfAbsent(f.getNome(), f);
        }
    }

    /**
     * Obtém um feitiço pelo nome.
     *
     * @param nome nome do feitiço (case-sensitive)
     * @return o {@link Feitico} correspondente, ou {@code null} se não existir
     */
    public Feitico obterMagia(String nome) {
        return banco.get(nome);
    }

    /**
     * Retorna uma visão não-modificável de todos os feitiços cadastrados.
     *
     * @return coleção imutável de {@link Feitico}
     */
    public Collection<Feitico> listarTodas() {
        return Collections.unmodifiableCollection(banco.values());
    }

    /**
     * Verifica se um feitiço está cadastrado.
     *
     * @param nome nome do feitiço
     * @return {@code true} se existir no banco
     */
    public boolean existe(String nome) {
        return banco.containsKey(nome);
    }
}
