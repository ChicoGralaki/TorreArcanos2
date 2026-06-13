// Arquivo: torrearcanos/modelo/Grimorio.java
package torrearcanos.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa o grimório do Mago — coleção de feitiços disponíveis.
 * Mantém uma lista de {@link Feitico} em Agregação com o {@link Mago}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public class Grimorio {

    private final List<Feitico> feiticos;

    /**
     * Cria um grimório vazio.
     */
    public Grimorio() {
        this.feiticos = new ArrayList<>();
    }

    /**
     * Adiciona um feitiço ao grimório.
     *
     * @param feitico o feitiço a adicionar; ignorado se nulo
     */
    public void adicionarFeitico(Feitico feitico) {
        if (feitico != null) {
            feiticos.add(feitico);
        }
    }

    /**
     * Remove um feitiço do grimório.
     *
     * @param feitico o feitiço a ser removido
     */
    public void removerFeitico(Feitico feitico) {
        feiticos.remove(feitico);
    }

    /**
     * Retorna uma visão não-modificável da lista de feitiços.
     *
     * @return lista imutável de {@link Feitico}
     */
    public List<Feitico> listarFeiticos() {
        return Collections.unmodifiableList(feiticos);
    }

    /**
     * Retorna a quantidade de feitiços no grimório.
     *
     * @return número de feitiços
     */
    public int quantidadeFeiticos() {
        return feiticos.size();
    }

    /**
     * Busca um feitiço pelo nome (ignora maiúsculas/minúsculas).
     *
     * @param nome nome do feitiço buscado
     * @return o {@link Feitico} encontrado, ou {@code null} se não existir
     */
    public Feitico buscarPorNome(String nome) {
        return feiticos.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Grimório [\n");
        feiticos.forEach(f -> sb.append("  ").append(f).append("\n"));
        sb.append("]");
        return sb.toString();
    }
}
