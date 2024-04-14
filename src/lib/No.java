package lib;

/**
 * Classe que representa um nó em uma árvore binária.
 *
 * @param <T> Tipo genérico do valor armazenado no nó.
 */
public class No<T> {

    // Valor armazenado no nó
    private T valor;
    // Referência para o filho direita do nó
    private No<T> filhoDireita;
    // Referência para o filho esquerda do nó
    private No<T> filhoEsquerda;

    // Construtor que inicializa o nó com um valor
    public No(T valor){
        this.valor = valor;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
    }

    /**
     * @return O valor armazenado no nó.
     */
    public T getValor() {
        return valor;
    }

    /**
     * @param valor O valor a ser atribuído ao nó.
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * @return O filho à direita do nó.
     */
    public No<T> getFilhoDireita() {
        return filhoDireita;
    }

    /**
     * @param filhoDireita O nó a ser atribuído como filho direita.
     */
    public void setFilhoDireita(No<T> filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    /**
     * @return O filho à esquerda do nó.
     */
    public No<T> getFilhoEsquerda() {
        return filhoEsquerda;
    }

    /**
     * @param filhoEsquerda O nó a ser atribuído como filho esquerda.
     */
    public void setFilhoEsquerda(No<T> filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    /**
     * Método para calcular a altura do nó na árvore.
     *
     * @return A altura do nó.
     */
    public int altura() {
        return obterAltura(this);
    }

    // Método auxiliar para calcular a altura de um nó recursivamente
    private int obterAltura(No<T> no){
        if (no == null){
            return -1; // Se o nó for nulo, a altura é -1
        }

        int altE = obterAltura(no.getFilhoEsquerda()); // Altura da subárvore esquerda
        int altD = obterAltura(no.getFilhoDireita());  // Altura da subárvore direita

        // Retorna a altura máxima entre as subárvores mais a altura do próprio nó
        return Math.max(altE, altD) + 1;
    }

    /**
     * Método para calcular o fator de balanceamento do nó.
     *
     * @return O fator de balanceamento do nó.
     */
    public int fatorBalanceamento(){
        return obterAltura(this.getFilhoDireita()) - obterAltura(this.getFilhoEsquerda());
    }
}
