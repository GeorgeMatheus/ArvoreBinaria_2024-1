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

    // Método público para remover um valor da árvore AVL.

    @Override
    public T remover(T valor) {
        // Chama o método privado remover, que faz a remoção recursiva, passando a raiz da árvore.
        No<T> removido = remover(raiz, valor);
        // Retorna o valor removido se não for null, caso contrário, retorna null.
        return removido != null ? removido.getValor() : null;
    }

    // Método privado que remove de forma recursiva um nó com o valor especificado a partir da raiz dada.

    private No<T> remover(No<T> raiz, T valor) {
        // Se a raiz for null, o valor não está na árvore.
        if (raiz == null) {
            return null;
        }
        // Compara o valor a ser removido com o valor do nó atual
        int comp = comparador.compare(valor, raiz.getValor());

        if (comp < 0) {
            // Se o valor a ser removido for menor que zero, chama recursivamente para a subárvore esquerda
            raiz.setFilhoEsquerda(remover(raiz.getFilhoEsquerda(), valor));
        } else if (comp > 0) {
            // Se o valor a ser removido for maior que zero, chama recursivamente para a subárvore direita.
            raiz.setFilhoDireita(remover(raiz.getFilhoDireita(), valor));
        } else {

            //Se comp == 0, os valores são iguais, o nó a ser removido foi encontrado
            if (raiz.getFilhoEsquerda() == null || raiz.getFilhoDireita() == null) {
                // Caso 1: O nó tem no máximo um filho.
                // Substitui o nó pelo seu único filho ou por null se não tiver filhos.
                raiz = (raiz.getFilhoEsquerda() != null) ? raiz.getFilhoEsquerda() : raiz.getFilhoDireita();
            } else {
                // Caso 2: O nó tem dois filhos.
                // Se o nó a ser removido possui dois filhos, a estratégia é encontrar o sucessor,
                // que é o menor valor na subárvore direita do nó a ser removido
                No<T> sucessor = encontrarSucessor(raiz.getFilhoDireita());
                // o valor do nó a ser removido é substituído pelo valor do sucessor
                raiz.setValor(sucessor.getValor());
                //O sucessor é removido da subárvore direita através da chamada recursiva
                raiz.setFilhoDireita(remover(raiz.getFilhoDireita(), sucessor.getValor()));
            }
        }
        // Após a remoção, se a raiz não for null, balanceia a árvore.
        if (raiz != null) {
            raiz = balancear(raiz);
        }
        // Retorna a nova raiz da subárvore.
        return raiz;
    }

    //Balancear a árvore binária de busca, após uma operação de remoção.
    private No<T> balancear(No<T> raiz) {
        // Chama o fator de balanceamento do nó atual.
        int balanceamento = fatorBalanceamento(raiz);

        //O valor do fator de balanceamento é comparado com os limites 1 e -1
        if (balanceamento > 1) {
            // Verifica o fator de balanceamento do filho direito.
            // Se for menor que 0, significa que é necessário realizar uma rotação dupla.
            if (fatorBalanceamento(raiz.getFilhoDireita()) < 0) {
                // Realiza uma rotação à direita no filho direito para em seguida ocorrer a rotação à esquerda.
                raiz.setFilhoDireita(rotacaoDireita(raiz.getFilhoDireita()));
            }
            //Rotação à esquerda
            raiz = rotacaoEsquerda(raiz);
        } else if (balanceamento < -1) {
            // Verifica o fator de balanceamento do filho esquerdo.
            // Se for maior que 0, significa que é necessário realizar uma rotação dupla.
            if (fatorBalanceamento(raiz.getFilhoEsquerda()) > 0) {
                // Realiza uma rotação à esquerda no filho esquerdo para em seguida ocorrer para a rotação à direita.
                raiz.setFilhoEsquerda(rotacaoEsquerda(raiz.getFilhoEsquerda()));
            }
            // Rotação à direita
            raiz = rotacaoDireita(raiz);
        }
        // Retorna a nova raiz da subárvore balanceada
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

    // Encontra o sucessor de um nó (menor nó na subárvore direita do nó especificado)

    private No<T> encontrarSucessor(No<T> no) {
        //O método inicia um loop while que continua enquanto o nó atual (no) possui um filho à esquerda
        while (no.getFilhoEsquerda() != null) {
            //Em cada iteração, o nó atual é atualizado para ser seu filho à esquerda
            no = no.getFilhoEsquerda();
        }
        //Ao sair do loop, o nó atual estará apontando para o nó mais à esquerda da subárvore direita,
        // que é o sucessor procurado.
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
