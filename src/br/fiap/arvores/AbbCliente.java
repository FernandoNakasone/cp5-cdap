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

    public int contaNosAcima(NO p, int cont, double valor) {
        if (p != null) {
            if (p.dado.getSaldo() > valor) {
                cont++;
            }
            cont = contaNosAcima(p.esq, cont, valor);
            cont = contaNosAcima(p.dir, cont, valor);
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

    public NO removeValor(NO p, int numConta) {
        if (p == null) return null;

        if (p.dado.getNumeroConta() == numConta) {
            if (p.esq == null && p.dir == null) {
                return null;
            }
            if (p.esq == null) {
                return p.dir;
            }
            if (p.dir == null) {
                return p.esq;
            }

            NO aux = p.dir;
            while (aux.esq != null) {
                aux = aux.esq;
            }
            p.dado = aux.dado;
            p.dir = removeValor(p.dir, aux.dado.getNumeroConta());
            return p;
        }

        p.esq = removeValor(p.esq, numConta);
        p.dir = removeValor(p.dir, numConta);
        return p;
    }

    public Cliente consulta(NO p, String cpfCNPJ) {
        if (p == null) return null;
        if (p.dado.getCpfCnpj().equalsIgnoreCase(cpfCNPJ)) {
            return p.dado;
        }
        Cliente achado = consulta(p.esq, cpfCNPJ);
        if (achado != null) return achado;
        return consulta(p.dir, cpfCNPJ);
    }

    public Cliente atualizaSaldo(NO p, int numConta, double saldo) {
        Cliente c = buscaPorNumeroConta(p, numConta);
        if (c == null) return null;

        root = removeValor(root, numConta);
        c.setSaldo(saldo);
        root = inserir(root, c);

        return c;
    }

    private Cliente buscaPorNumeroConta(NO p, int numConta) {
        if (p == null) return null;
        if (p.dado.getNumeroConta() == numConta) return p.dado;
        Cliente achado = buscaPorNumeroConta(p.esq, numConta);
        if (achado != null) return achado;
        return buscaPorNumeroConta(p.dir, numConta);
    }
}
