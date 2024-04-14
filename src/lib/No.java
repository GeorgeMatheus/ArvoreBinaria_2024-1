package lib;

/**
 *
 * @author victoriocarvalho
 */

 /*
  * Classe que representa o no da arvore binaria
  */
public class No<T> {

    private T valor; // Valor que esta armazenado no No
    private No<T> filhoDireita; // Referencia para o filho direito
    private No<T> filhoEsquerda; // Referencia para o filho esquerdo

    // Construtor da classe No
    public No(T valor){
        this.valor = valor;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
    }

    // Getter para obter o valor do No
    public T getValor() {
        return valor;
    }

    // Setter para definir o valor do no
    public void setValor(T valor) {
        this.valor = valor;
    }

    // Getter para obter o filho direito do no
    public No<T> getFilhoDireita() {
        return filhoDireita;
    }

    // Setter para definir o filho direito do no
    public void setFilhoDireita(No<T> filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

   // Getter para obter o filho esquerdo do no
    public No<T> getFilhoEsquerda() {
        return filhoEsquerda;
    }

    // Setter para definir o filho esquerdo do no
    public void setFilhoEsquerda(No<T> filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    // Metodo para calcular a altura do no
    public int altura() {
        return obterAltura(this);
    }

    // Método privado auxiliar para calcular a altura de um no
    private int obterAltura(No<T> no){
        // Se o no for nulo a altura eh 0
        if (no == null){
            return -1;
        }

        // Recursivamente calcula a altura da subarvore esquerda
        int altE = obterAltura(no.getFilhoEsquerda());

        // Recursivamente calcula a altura da subarvore direita
        int altD = obterAltura(no.getFilhoDireita());

        // Retorna a altura maxima entre as subarvores + 1
        if (altE > altD){
            return altE + 1;
        }else {
            return altD + 1;
        }

        //   return Math.max(altE, altD) + 1;

    }

    // Metodo para calcular o fator de balanceamento do no
    public int fatorBalanceamento(){
        //Retorna a diferenca entre as alturas das subarvores direita e esquerda
        return obterAltura(this.getFilhoDireita()) - obterAltura(this.getFilhoEsquerda());
    }

}