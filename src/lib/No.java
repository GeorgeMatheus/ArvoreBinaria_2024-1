package lib;

/**
 *
 * @author victoriocarvalho
 */
public class No<T> {

    private T valor;
    private No<T> filhoDireita;
    private No<T> filhoEsquerda;


    public No(T valor){
        this.valor = valor;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
    }

    /**
     * @return the valor
     */
    public T getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * @return the filhoDireita
     */
    public No<T> getFilhoDireita() {
        return filhoDireita;
    }

    /**
     * @param filhoDireita the filhoDireita to set
     */
    public void setFilhoDireita(No<T> filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    /**
     * @return the filhoEsquerda
     */
    public No<T> getFilhoEsquerda() {
        return filhoEsquerda;
    }

    /**
     * @param filhoEsquerda the filhoEsquerda to set
     */
    public void setFilhoEsquerda(No<T> filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }


    public int altura() {
        return obterAltura(this);
    }

    private int obterAltura(No<T> no){
        // se o nó for nulo a altura é 0
        if (no == null){
            return -1;
        }

        //recursivamente calcula a altura da subarvore esquerda
        int altE = obterAltura(no.getFilhoEsquerda());
        //recursivamente calcula a altura da subarvore direita
        int altD = obterAltura(no.getFilhoDireita());

        if (altE > altD){
            return altE + 1;
        }else {
            return altD + 1;
        }

        //   return Math.max(altE, altD) + 1;

    }

    public int fatorBalanceamento(){
        return obterAltura(this.getFilhoDireita()) - obterAltura(this.getFilhoEsquerda());
    }

}