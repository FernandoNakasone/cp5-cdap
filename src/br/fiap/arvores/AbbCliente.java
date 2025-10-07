package br.fiap.arvores;

import br.fiap.main.Cliente;

import java.util.List;

public class AbbCliente {
    private class NO {
        Cliente dado;
        NO esq, dir;
    }

    public NO root = null;

    public NO inserir(NO p, Cliente cliente) {
        if (p == null) {
            p = new NO();
            p.dado = cliente;
            p.esq = null;
            p.dir = null;
        } else if (cliente.getSaldo() < p.dado.getSaldo()) {
            p.esq = inserir(p.esq, cliente);
        } else {
            p.dir = inserir(p.dir, cliente);
        }
        return p;
    }

    public void show(NO p) {
        if (p != null) {
            show(p.esq);
            System.out.println(p.dado);
            show(p.dir);
        }
    }

    public int contaNos(NO p, int cont) {
        if (p != null) {
            cont++;
            cont = contaNos(p.esq, cont);
            cont = contaNos(p.dir, cont);
        }

        return cont;
    }

    public void gerarLista(NO p, double minimo, List<Cliente> lista) {
        if (p != null) {
            gerarLista(p.dir, minimo, lista);
            if (p.dado.getSaldo() >= minimo) {
                lista.add(p.dado);
            }
            gerarLista(p.esq, minimo, lista);
        }
    }

    /**
     * Remove o nó cuja conta é numConta.
     * Como a ABB está ordenada por saldo, não podemos navegar por numConta.
     * Então precisamos procurar (varrer) até encontrar e então remover usando remoção BST
     * baseada no saldo (sucessor inorder se houver 2 filhos).
     */
    public NO removeValor(NO p, int numConta) {
        if (p == null) return null;

        // primeiro, se este nó é o que queremos remover
        if (p.dado.getNumeroConta() == numConta) {
            // caso 0 ou 1 filho
            if (p.esq == null && p.dir == null) {
                return null;
            }
            if (p.esq == null) {
                return p.dir;
            }
            if (p.dir == null) {
                return p.esq;
            }

            // caso 2 filhos: encontrar o menor da subárvore direita (sucessor inorder)
            NO aux = p.dir;
            while (aux.esq != null) {
                aux = aux.esq;
            }
            // copiar dado do sucessor para o nó atual
            p.dado = aux.dado;
            // remover o nó sucessor (que tem a conta que acabamos de copiar)
            p.dir = removeValor(p.dir, aux.dado.getNumeroConta());
            return p;
        }

        // se não for este nó, precisamos buscar em ambos os lados (não podemos decidir direção por numConta)
        p.esq = removeValor(p.esq, numConta);
        p.dir = removeValor(p.dir, numConta);
        return p;
    }

    /**
     * Consulta por CPF/CNPJ — varre a árvore (preorder-ish) até encontrar e retorna o Cliente ou null.
     */
    public Cliente consulta(NO p, String cpfCNPJ) {
        if (p == null) return null;
        if (p.dado.getCpfCnpj().equalsIgnoreCase(cpfCNPJ)) {
            return p.dado;
        }
        Cliente achado = consulta(p.esq, cpfCNPJ);
        if (achado != null) return achado;
        return consulta(p.dir, cpfCNPJ);
    }

    /**
     * Atualiza o saldo do cliente identificado por numConta somando 'saldo' (valor a adicionar).
     * Para manter a propriedade da ABB (ordenada por saldo), removemos o nó e o reinsere com o novo saldo.
     * Retorna o cliente atualizado (com novo saldo) ou null se não encontrado.
     */
    public Cliente atualizaSaldo(NO p, int numConta, double saldo) {
        // busca o cliente pelo numeroConta (varredura)
        Cliente c = buscaPorNumeroConta(p, numConta);
        if (c == null) return null;

        // remove o nó da árvore (atualiza root)
        root = removeValor(root, numConta);

        // atualiza saldo no objeto cliente
        c.setSaldo(c.getSaldo() + saldo);

        // reinsere o cliente com o novo saldo (atualiza root)
        root = inserir(root, c);

        return c;
    }

    // helper para buscar Cliente por numeroConta (varredura completa)
    private Cliente buscaPorNumeroConta(NO p, int numConta) {
        if (p == null) return null;
        if (p.dado.getNumeroConta() == numConta) return p.dado;
        Cliente achado = buscaPorNumeroConta(p.esq, numConta);
        if (achado != null) return achado;
        return buscaPorNumeroConta(p.dir, numConta);
    }
}
