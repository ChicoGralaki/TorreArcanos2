// Arquivo: torrearcanos/modelo/Feitico.java
package torrearcanos.modelo;

/**
 * Classe abstrata que representa um feitiço no grimório do Mago.
 * Define nome, custo de mana, sistema de crítico e o contrato
 * polimórfico do método {@link #castar()}.
 *
 * @author TorreArcanos
 * @version 1.0
 */
public abstract class Feitico {

    private final String nome;
    private final int custoMana;
    private final int usosParaCritico;
    private int contadorUso;

    /**
     * Constrói um feitiço com seus atributos base.
     *
     * @param nome           nome do feitiço
     * @param custoMana      custo em mana para lançar o feitiço
     * @param usosParaCritico a cada quantos usos o feitiço causa efeito crítico
     * @throws IllegalArgumentException se nome for nulo/vazio ou custoMana < 0
     */
    public Feitico(String nome, int custoMana, int usosParaCritico) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do feitiço não pode ser nulo ou vazio.");
        }
        if (custoMana < 0) {
            throw new IllegalArgumentException("Custo de mana não pode ser negativo.");
        }
        this.nome = nome;
        this.custoMana = custoMana;
        this.usosParaCritico = Math.max(1, usosParaCritico);
        this.contadorUso = 0;
    }

    /** @return nome do feitiço */
    public String getNome() { return nome; }

    /** @return custo em mana para lançar */
    public int getCustoMana() { return custoMana; }

    /** @return número de usos necessários para acionar o crítico */
    public int getUsosParaCritico() { return usosParaCritico; }

    /** @return número de vezes que o feitiço foi lançado */
    public int getContadorUso() { return contadorUso; }

    /**
     * Incrementa o contador de uso do feitiço em 1.
     */
    public void incrementarContador() { this.contadorUso++; }

    /**
     * Verifica se o uso atual é um crítico.
     * O crítico ocorre quando contadorUso é múltiplo de usosParaCritico.
     *
     * @return {@code true} se este uso for crítico
     */
    public boolean verificarCritico() {
        return contadorUso > 0 && (contadorUso % usosParaCritico == 0);
    }

    /**
     * Método polimórfico que define o efeito do feitiço ao ser lançado.
     * Subclasses devem implementar a lógica específica de ataque ou defesa.
     */
    public abstract void castar();

    @Override
    public String toString() {
        return String.format("Feitiço[%s | Mana: %d | Crítico a cada %d usos | Usos: %d]",
                nome, custoMana, usosParaCritico, contadorUso);
    }
}
