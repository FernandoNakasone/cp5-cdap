package br.fiap.arvores;

import br.fiap.main.Cliente;

import java.util.ArrayList;
import java.util.List;

public class AbbCliente {
        private class NO {
            Cliente dado;
            NO esq, dir;
        }

        public NO root = null;

        public NO inserir(NO p, Cliente cliente) {
            if(p==null) {
                p = new NO();
                p.dado = cliente;
                p.esq = null;
                p.dir = null;
            } else if(cliente.getSaldo() < p.dado.getSaldo()) {
                p.esq = inserir(p.esq, cliente);
            } else {
                p.dir = inserir(p.dir, cliente);
            }
            return p;
        }

        public void show(NO p) {
            if(p!=null) {
                show(p.esq);
                System.out.println(p.dado);
                show(p.dir);
            }
        }

        public int contaNos(NO p, int cont) {
            if(p != null) {
                cont++;
                cont = contaNos(p.esq, cont);
                cont = contaNos(p.dir, cont);
            }

            return cont;
        }

        public void gerarLista(NO p, double minimo, List<Cliente> lista) {
            if(p!=null) {
                gerarLista(p.dir, minimo, lista);
                if (p.dado.getSaldo() >= minimo) {
                    lista.add(p.dado);
                    System.out.println("Valor adicionado");
                }
                gerarLista(p.esq, minimo, lista);

            }
        }

        public NO removeValor (NO p, int numConta) {
            if (p!=null){
                if(numConta == p.dado.getNumeroConta()){
                    if (p.esq == null && p.dir==null)
                        return null;
                    if (p.esq==null) {
                        return p.dir;
                    }
                    else {
                        if (p.dir==null){
                            return p.esq;
                        }
                        else {
                            NO aux, ref;
                            ref = p.dir;
                            aux = p.dir;
                            while (aux.esq != null)
                                aux = aux.esq;
                            aux.esq = p.esq;
                            return ref;
                        }
                    }
                }
                else {
                    if(numConta<p.dado.getNumeroConta())
                        p.esq = removeValor(p.esq,numConta);
                    else
                        p.dir = removeValor(p.dir,numConta);
                }
            }
            return p;
        }


}
