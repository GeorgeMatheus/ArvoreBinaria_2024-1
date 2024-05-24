package lib;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected No<T> addRecursao(No<T> raiz, No<T> novo){
        raiz = super.addRecursao(raiz, novo);

        if(raiz.fatorBalanceamento() > 1){
            if(raiz.getFilhoDireita().fatorBalanceamento() > 0){
                raiz = this.rotacaoEsquerda(raiz);
            } else {
                raiz = this.rotacaoEsquerda(raiz);
            }
        } else if (raiz.fatorBalanceamento() < -1){
            if(raiz.getFilhoEsquerda().fatorBalanceamento() < 0){
                raiz = this.rotacaoDireita(raiz);
            } else {
                raiz = this.rotacaoDireita(raiz);
            }
        }

        return raiz;
    }

    @Override
    public T remover(T valor) {
        No<T> removido = remover(raiz, valor);
        return removido != null ? removido.getValor() : null;
    }

    private No<T> remover(No<T> raiz, T valor) {
        if (raiz == null) {
            return null;
        }

        int comp = comparador.compare(valor, raiz.getValor());
        if (comp < 0) {
            raiz.setFilhoEsquerda(remover(raiz.getFilhoEsquerda(), valor));
        } else if (comp > 0) {
            raiz.setFilhoDireita(remover(raiz.getFilhoDireita(), valor));
        } else {
            if (raiz.getFilhoEsquerda() == null || raiz.getFilhoDireita() == null) {
                raiz = (raiz.getFilhoEsquerda() != null) ? raiz.getFilhoEsquerda() : raiz.getFilhoDireita();
            } else {
                No<T> sucessor = encontrarSucessor(raiz.getFilhoDireita());
                raiz.setValor(sucessor.getValor());
                raiz.setFilhoDireita(remover(raiz.getFilhoDireita(), sucessor.getValor()));
            }
        }

        if (raiz != null) {
            raiz = balancear(raiz);
        }

        return raiz;
    }

    private No<T> balancear(No<T> raiz) {
        int balanceamento = fatorBalanceamento(raiz);

        if (balanceamento > 1) {
            if (fatorBalanceamento(raiz.getFilhoDireita()) < 0) {
                raiz.setFilhoDireita(rotacaoDireita(raiz.getFilhoDireita()));
            }
            raiz = rotacaoEsquerda(raiz);
        } else if (balanceamento < -1) {
            if (fatorBalanceamento(raiz.getFilhoEsquerda()) > 0) {
                raiz.setFilhoEsquerda(rotacaoEsquerda(raiz.getFilhoEsquerda()));
            }
            raiz = rotacaoDireita(raiz);
        }

        return raiz;
    }

    private int fatorBalanceamento(No<T> no) {
        if (no == null) {
            return 0;
        }
        return altura(no.getFilhoDireita()) - altura(no.getFilhoEsquerda());
    }
}
