package lib;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void adicionar(T novoValor) {
        raiz = adicionar(raiz, novoValor);
    }

    private No<T> adicionar(No<T> raiz, T novoValor) {
        if (raiz == null) {
            return new No<>(novoValor);
        }

        int comp = comparador.compare(novoValor, raiz.getValor());
        if (comp < 0) {
            raiz.setFilhoEsquerda(adicionar(raiz.getFilhoEsquerda(), novoValor));
        } else if (comp > 0) {
            raiz.setFilhoDireita(adicionar(raiz.getFilhoDireita(), novoValor));
        } else {
            return raiz; // Valor duplicado, não adiciona
        }

        return balancear(raiz);
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
