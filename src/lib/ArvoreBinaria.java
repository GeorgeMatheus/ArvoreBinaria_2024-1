package lib;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

// A classe ArvoreBinaria implementa a interface IArvoreBinaria
public class ArvoreBinaria<T> implements IArvoreBinaria<T> {

    // A raiz da árvore binária
    protected No<T> raiz = null;
    // O comparador para comparar os elementos da árvore
    protected Comparator<T> comparador;

    // Construtor da árvore binária
    public ArvoreBinaria(Comparator<T> comp) {
        comparador = comp;
    }

    // Método para adicionar um novo valor à árvore
    @Override
    public void adicionar(T novoValor) {
        No<T> novoNo = new No<>(novoValor);

        // verifica se a árvore está vazia
        if (this.raiz == null){
            // Se estiver vazia ele vai define o novo nó como raiz
            this.raiz = novoNo;
        }else {
            this.raiz = addRecursao(this.raiz, novoNo);
        }
    }
    protected No<T> addRecursao(No<T> atual, No<T> novo){
        //Se o novo elemento for menor do que o atual vou para a esquerda
        int comp = this.comparador.compare(novo.getValor(), atual.getValor());

        if(comp < 0){
            if(atual.getFilhoEsquerda() == null){
                atual.setFilhoEsquerda(novo);
            }else{
                atual.setFilhoEsquerda(addRecursao(atual.getFilhoEsquerda(), novo));
            }
        }else {
            if(atual.getFilhoDireita() == null) {
                atual.setFilhoDireita(novo);
            }else {
                atual.setFilhoDireita(addRecursao(atual.getFilhoDireita(), novo));
            }
        }

        return atual;
    }

    @Override
    public T pesquisar(T valor, Comparator comparador) {
        // Chamando o método recursivo
        return pesquisarRec(raiz, valor, comparador);
    }

    // Método recursivo para pesquisar o valor na árvore
        private T pesquisarRec(No<T> r, T valor, Comparator comp ) {
            if( r == null) {
                return null; // / Retorna o valor encontrado na árvore
            }
            if(comp.compare(valor, r.getValor()) == 0) {
                return r.getValor(); //Retorna o valor encontrado na árvore
            }
            else {
                T rd = pesquisarRec(r.getFilhoDireita(), valor, comp);
                T re = pesquisarRec(r.getFilhoEsquerda(), valor, comp);

                // Verificando se o valor foi encontrado em alguma das sub-árvores
                if( rd != null){
                    return rd;
                }
                else if( re != null){
                    return re;
                }
                else return null;
            }
        }


    // Método para pesquisar um valor na árvore
    @Override
    public T pesquisar(T valor) {
        No<T> atual = this.raiz;

        while (atual != null){
            int comp = comparador.compare(valor, atual.getValor());

            if(comp == 0){
                return atual.getValor(); //Retorna o valor encontrado na arvore
            } else if (comp < 0) {
                atual = atual.getFilhoEsquerda(); // Procura na sub-árvore esquerda
            }else {
                atual = atual.getFilhoDireita(); // Procura na sub-árvore direita
            }
        }
        return null; // Valor não encontrado
    }

    // Método para remover um valor da árvore
    @Override
    public T remover(T valor) {
        No<T> atual = raiz;
        No<T> pai = null;
        //Entra em loop enquanto o nó atual for nulo
        while(atual != null){
            //compara o valor a ser removido com o valor atual
            int comp = comparador. compare(valor, atual.getValor());
            //compara se os valores são iguais
            if (comp == 0){
                T valorRemovido = atual.getValor();
                //verifica os casos de remoção
                if(atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null){
                    // 1: O nó não tem filhos
                    if (pai == null){
                        raiz = null;
                    } else if (pai.getFilhoEsquerda() == atual) {
                        pai.setFilhoEsquerda(null);
                    }else {
                        pai.setFilhoDireita(null);
                    }
                } else if (atual.getFilhoEsquerda() != null && atual.getFilhoDireita() == null) {
                    // 2: O nó tem um filho à esquerda
                    if (pai == null){
                        raiz = atual.getFilhoEsquerda();
                    } else if (pai.getFilhoEsquerda() == atual) {
                        pai.setFilhoEsquerda(atual.getFilhoEsquerda());
                    }else{
                        pai.setFilhoDireita(atual.getFilhoEsquerda());
                    }
                } else if (atual.getFilhoEsquerda() == null && atual.getFilhoDireita() != null) {
                    //3: O nó tem um filho à direita
                    if (pai == null){
                        raiz = atual.getFilhoDireita();

                    } else if (pai.getFilhoEsquerda()==atual) {
                        pai.setFilhoEsquerda(atual.getFilhoDireita());
                    }else{
                        pai.setFilhoDireita(atual.getFilhoDireita());
                    }
                }else{
                    //4: O nó tem dois filhos
                    //encontra um sucessor do nó atual na subarvore direita
                    No<T> sucessor = encontrarSucessor(atual.getFilhoDireita());
                    //define o valor do sucessor do no atual
                    atual.setValor(sucessor.getValor());
                    //remove o sucessor
                    remover(sucessor.getValor());
                }
                //retorna o valor removido
                return valorRemovido;
            }else if (comp < 0){
                //se o valor for menor, move para a esquerda
                pai = atual;
                atual = atual.getFilhoEsquerda();
            }else {
                //se o valor for maior, move para a direita na arvore
                pai = atual;
                atual = atual.getFilhoDireita();
            }
        }
        return null;
    }

    // Método auxiliar para encontrar o sucessor de um nó
    private No<T> encontrarSucessor(No<T> atual){
        //Entra em um loop enquanto houver um filho à esquerda do no atual
        while (atual.getFilhoEsquerda() != null){
            //move para o filho à esquerda
            atual = atual.getFilhoEsquerda();
        }
        //quando não exixtir mais filhos a esquerda ele retorna o valor do no atual
        return atual;
    }

    // Método para calcular a altura da árvore
    public int altura(){
        return this.raiz.altura();
    }

    // Método para contar a quantidade de nós na árvore
    @Override
    public int quantidadeNos() {
        return contarNos(raiz);
    }

    // Método auxiliar para contar os nós da árvore
    private int contarNos(No<T> no){
        // se o nó for nulo a altura é 0
        if (no == null){
            return 0;
        }
        //recursivamente conta os nós da subarvore esquerda
        int nosD = contarNos(no.getFilhoDireita());
        //recursivamente conta os nós da subarvore direita
        int nosE = contarNos(no.getFilhoEsquerda());
        //retorna 1 mais a quantidade de nós nas subarvore esquerda e direita
        return 1 + nosE + nosD;
    }

    // Método para percorrer a árvore em nível
    @Override
    public String caminharEmNivel() {
        if (raiz == null){
            return "[]";
        }
        //cria um StringBuilder para construir uma representação de travessia
        StringBuilder result = new StringBuilder("[");
        ArrayList<No<T>> pilhaNavegacao = new ArrayList<>();
        pilhaNavegacao.add(raiz);
        //inicia um loop até que a fila esteja vazia
        while (!pilhaNavegacao.isEmpty()){
            //remove o primeiro nó da fila
            No<T> no = pilhaNavegacao.remove(0);
            //adiciona o valor do nó a repressentação da travesssia
            result.append(no.getValor());
            //Se houver um filho a esquerda e adicione à fila
            if(no.getFilhoEsquerda() != null){
                pilhaNavegacao.add(no.getFilhoEsquerda());
            }
            //Se houver um filho à direita e adicione à fila
            if (no.getFilhoDireita() != null){
                pilhaNavegacao.add(no.getFilhoDireita());
            }
            //Se a fila não estiver vazia, adicione um espaçopara separar os valores dos nos
            if (!pilhaNavegacao.isEmpty()){
                result.append(" ");
            }
        }
        //finaliza a respresentação
        result.append("]");
        //retorna a respresentação com uma string
        return result.toString();
    }

    // Método para percorrer a árvore em ordem
    @Override
    public String caminharEmOrdem() {
        if (raiz == null){
            return "[]";
        }

        StringBuilder result = new StringBuilder("[");
        Stack<No<T>> pilha = new Stack<>();
        No<T> atual = raiz;
        while (true){
            //move para a esquerda enquanto houver nós à esquerda
            while (atual != null){
                pilha.push(atual);
                atual=atual.getFilhoEsquerda();
            }
            //se a pilha estiver vazia, todos os nós foram visitados
            if (pilha.isEmpty()){
                break;
            }
            //romove o nó do topo da pilha e adicione seu valor
            atual= pilha.pop();
            result.append(atual.getValor()).append("\n");
            atual = atual.getFilhoDireita();
        }
        //finaliza a reprensentação
        result.append("]");
        return result.toString();
    }
}
