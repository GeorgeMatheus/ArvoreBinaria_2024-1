package lib;

import java.util.Comparator;

/* Esta classe implementa uma árvore binária balanceada (AVL)
   onde a diferença em altura entre as subárvores esquerda e direita
    de cada nó é no máximo um (positivo ou negativo)
  */
public class ArvoreAVL<T> extends ArvoreBinaria<T> {
    /* Construtor que inicializa a árvore com o comparador especificado,
    ou seja, o comparador é usado para comparar elementos na árvore */
    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    // Método recursivo para adicionar um novo nó na árvore
    @Override
    protected No<T> addRecursao(No<T> raiz, No<T> novo){
        // Chama o método de adição da classe pai
        raiz = super.addRecursao(raiz, novo);

        /*Verifica o fator de balanceamento e caso seja necessário
        realiza rotações para o balancear a árvore
        */

        //Verifica do fator de balanceamento maior que 1
        if (raiz.fatorBalanceamento() > 1) {
            //Fator de balanceamento negativo do filho a direita
            if (raiz.getFilhoDireita().fatorBalanceamento() < 0) {
                // Rotação dupla direita-esquerda
                // Faço uma rotação a direita no filho a direita
                raiz.setFilhoDireita(rotacaoDireita(raiz.getFilhoDireita()));
            }
            // Rotação simples à esquerda na raiz
            raiz = rotacaoEsquerda(raiz);
            // Fator de balanceamento menor que -1
        } else if (raiz.fatorBalanceamento() < -1) {
            if (raiz.getFilhoEsquerda().fatorBalanceamento() > 0) {
                // Rotação dupla esquerda-direita
                // Faço uma rotação a esquerda no filho a esquerda
                raiz.setFilhoEsquerda(rotacaoEsquerda(raiz.getFilhoEsquerda()));
            }
            // Rotação simples à direita na raiz
            raiz = rotacaoDireita(raiz);
        }

        return raiz;
    }

    //Metodo que faz a remoção de um valor simples
    @Override
    public T remover(T valor) {
        // Chama o método remover recursivo, começando pela raiz da árvore
        No<T> removido = remover(raiz, valor);
        // Retorna o valor do nó removido se ele foi encontrado, caso contrário retorna null
        return removido != null ? removido.getValor() : null;
    }

    //metodo alternativo para remoção
    private No<T> remover(No<T> raiz, T valor) {
        // Se a raiz é nula, o valor não está presente na árvore
        if (raiz == null) {
            return null;
        }

        // Compara o valor a ser removido com o valor do nó atual
        int comp = comparador.compare(valor, raiz.getValor());

        // Se o valor é menor, continua a busca no filho esquerdo
        if (comp < 0) {
            raiz.setFilhoEsquerda(remover(raiz.getFilhoEsquerda(), valor));
        }
        // Se o valor é maior, continua a busca no filho direito
        else if (comp > 0) {
            raiz.setFilhoDireita(remover(raiz.getFilhoDireita(), valor));
        }
        // Se o valor é igual ao valor do nó atual, encontramos o nó a ser removido
        else {
            // Caso o nó tenha apenas um filho (ou nenhum)
            if (raiz.getFilhoEsquerda() == null || raiz.getFilhoDireita() == null) {
                raiz = (raiz.getFilhoEsquerda() != null) ? raiz.getFilhoEsquerda() : raiz.getFilhoDireita();
            }
            // Caso o nó tenha dois filhos
            else {
                // Encontra o sucessor (menor valor na subárvore direita)
                No<T> sucessor = encontrarSucessor(raiz.getFilhoDireita());
                // Substitui o valor do nó pelo valor do sucessor
                raiz.setValor(sucessor.getValor());
                // Remove o sucessor da subárvore direita
                raiz.setFilhoDireita(remover(raiz.getFilhoDireita(), sucessor.getValor()));
            }
        }

        // Após a remoção, balanceia a árvore se a raiz não for nula
        if (raiz != null) {
            raiz = balancear(raiz);
        }

        // Retorna a raiz (possivelmente atualizada) da subárvore
        return raiz;
    }

    private No<T> balancear(No<T> raiz) {
        // Calcula o fator de balanceamento do nó
        int balanceamento = fatorBalanceamento(raiz);

        // Se o nó está desbalanceado para a direita (fator de balanceamento > 1)
        if (balanceamento > 1) {
            // Se o filho direito está desbalanceado para a esquerda
            if (fatorBalanceamento(raiz.getFilhoDireita()) < 0) {
                // Realiza uma rotação à direita no filho direito
                raiz.setFilhoDireita(rotacaoDireita(raiz.getFilhoDireita()));
            }
            // Realiza uma rotação à esquerda na raiz
            raiz = rotacaoEsquerda(raiz);
        }
        // Se o nó está desbalanceado para a esquerda (fator de balanceamento < -1)
        else if (balanceamento < -1) {
            // Se o filho esquerdo está desbalanceado para a direita
            if (fatorBalanceamento(raiz.getFilhoEsquerda()) > 0) {
                // Realiza uma rotação à esquerda no filho esquerdo
                raiz.setFilhoEsquerda(rotacaoEsquerda(raiz.getFilhoEsquerda()));
            }
            // Realiza uma rotação à direita na raiz
            raiz = rotacaoDireita(raiz);
        }

        // Retorna a raiz (possivelmente atualizada) da subárvore
        return raiz;
    }

    /* Calcula o  fator de balanceamento:
     * hd(altura da subárvore direita) - he( altura da subárvore esquerda)
     */
    private int fatorBalanceamento(No<T> no) {
        if (no == null) {
            return 0;
        }
        return altura(no.getFilhoDireita()) - altura(no.getFilhoEsquerda());
    }

    // Realiza a rotação à esquerda em um nó
    private No<T> rotacaoEsquerda(No<T> r) {
        // Armazena o filho a direita de `r` em `f`
        No<T> f = r.getFilhoDireita();
        // Substitui o filho a direita de `r` pelo filho a esquerda de `f`
        r.setFilhoDireita(f.getFilhoEsquerda());
        //  Faz de `r` o filho a esquerda de `f`
        f.setFilhoEsquerda(r);
        return f;
    }

    // Realiza a rotação à direita em um nó
    private No<T> rotacaoDireita(No<T> r) {
        // Armazena o filho a esquerda de `r` em `f`
        No<T> f = r.getFilhoEsquerda();
        // Substitui o filho a esquerda de `r` pelo filho a direito de `f`
        r.setFilhoEsquerda(f.getFilhoDireita());
        //  Faz de `r` o filho a direita de `f`
        f.setFilhoDireita(r);
        return f;
    }

    // Encontra o sucessor de um nó (menor nó na subárvore direita)
    private No<T> encontrarSucessor(No<T> no) {
        while (no.getFilhoEsquerda() != null) {
            no = no.getFilhoEsquerda();
        }
        return no;
    }

    // Retorna a altura da árvore
    @Override
    public int altura() {
        return altura(raiz);
    }
    // Calcula a altura de um nó recursivamente
    private int altura(No<T> no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(altura(no.getFilhoEsquerda()), altura(no.getFilhoDireita()));
    }
}
