package br.fiap.main;

import br.fiap.arvores.AbbCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DivulgaOfertas {
    /*
     * NOMES E RM dos alunos que compõem o grupo
     */
    public static void main(String[] args) {
        Scanner le = new Scanner(System.in);
        AbbCliente abbCPF = new AbbCliente();
        AbbCliente abbCNPJ = new AbbCliente();
        /*
         * Cria a uma árvore de busca binária para cada tipo de conta
         * (pessoa física ou jurídica)
         */
        int opcao, op, numeroConta;
        String nome, cpfCnpj;
        String tipoConta = null;
        double saldo;
        do {
            System.out.println(" 0 - Encerrar o programa");
            System.out.println(" 1 - Inscrição cliente");
            System.out.println(" 2 - Oferta de novo serviço e/ou aplicação");
            System.out.println(" 3 – Entrar no Submenu ");
            System.out.print("Opção:");
            opcao = le.nextInt();
            switch (opcao) {
                case 1:
                    System.out.print("Digite nome: ");
                    nome = le.next();
                    System.out.print("Digite cpf: ");
                    cpfCnpj = le.next();
                    System.out.print("Digite numero da conta: ");
                    numeroConta = le.nextInt();
                    do {
                        System.out.print("Digite 1- Pessoa Física 2- Pessoa Jurídica: ");
                        op = le.nextInt();
                        switch (op) {
                            case 1:
                                tipoConta = "Física";
                                break;
                            case 2:
                                tipoConta = "Jurídica";
                                break;
                            default:
                                System.out.println("Opção inválida ");
                                op = -1;
                        }
                    } while (op == -1);
                    System.out.print("Informe saldo em aplicações R$: ");
                    saldo = le.nextDouble();

                    Cliente cliente = new Cliente(numeroConta, nome, cpfCnpj, tipoConta, saldo);
                    if (tipoConta.equals("Física")) {
                        abbCPF.root = abbCPF.inserir(abbCPF.root, cliente);
                        System.out.println("Cliente inserido");
                        abbCPF.show(abbCPF.root);  // Mostrar clientes para garantir que estão inseridos
                        abbCNPJ.show(abbCNPJ.root);  // Mostrar clientes para garantir que estão inseridos
                    } else {
                        abbCNPJ.root = abbCNPJ.inserir(abbCNPJ.root, cliente);
                        System.out.println("Cliente inserido");
                        abbCPF.show(abbCPF.root);  // Mostrar clientes para garantir que estão inseridos
                        abbCNPJ.show(abbCNPJ.root);  // Mostrar clientes para garantir que estão inseridos
                    }
                    break;
                case 2:
                    System.out.print("Qual tipo de conta a oferta se destina? ");
                    do {
                        System.out.print("Digite 1- Pessoa Física 2- Pessoa Jurídica: ");
                        op = le.nextInt();
                        switch (op) {
                            case 1:
                                tipoConta = "Física";
                                break;
                            case 2:
                                tipoConta = "Jurídica";
                                break;
                            default:
                                System.out.println("Opção inválida ");
                                op = -1;
                        }
                    } while (op == -1);
                    System.out.print("Qual o valor de saldo mínimo exigido: R$ ");
                    saldo = le.nextDouble();

                    List<Cliente> listaOferta = new ArrayList<>();
                    if (tipoConta.equals("Física")) {
                        abbCPF.gerarLista(abbCPF.root, saldo, listaOferta);
                    } else {
                        abbCNPJ.gerarLista(abbCNPJ.root, saldo, listaOferta);
                    }

                    if (listaOferta.isEmpty()) {
                        System.out.println("Nenhum cliente corresponde ao saldo mínimo!");
                        break;
                    }

                    System.out.println(listaOferta.size() + " clientes encontrados!\n" +
                            "Entrando em contato.");
                    for (int i=0; i < listaOferta.size(); i++) {
                        cliente = listaOferta.get(i);
                        System.out.println("Cliente " + cliente.getNome() + " - " + cliente.getNumeroConta() + "\n" +
                                "Aceitou a oferta? (S/N)");
                        String resp = le.next();

                        while(!resp.equalsIgnoreCase("S") && !resp.equalsIgnoreCase("N")){
                            System.out.println("Resposta inválida");
                            System.out.println("Cliente " + cliente.getNome() + " - " + cliente.getNumeroConta() + "\n" +
                                    "Aceitou a oferta? (S/N)");
                            resp = le.next();
                        }

                        if(resp.equalsIgnoreCase("S")) {

                            if (tipoConta.equals("Física")) {
                                abbCPF.removeValor(abbCPF.root, cliente.getNumeroConta());
                            } else {
                                abbCNPJ.removeValor(abbCNPJ.root, cliente.getNumeroConta());
                            }

                            System.out.println("Cliente aceitou a oferta!");
                        } else {
                            System.out.println("Cliente recusou a oferta.");
                        }
                    }
                    break;

                case 3:
                    int subOpcao;
                    do {
                        System.out.println(" 1 - Consultar por CPF/CNPJ");
                        System.out.println(" 2 - Atualizar saldo");
                        System.out.println(" 3 - Apresentar quantidade total de clientes");
                        System.out.println(" 4 – Apresentar quantidade de clientes acima de um valor");
                        System.out.println(" 5 - Voltar ao menu principal");

                        subOpcao = le.nextInt();

                    } while (subOpcao != 5);
                    break;
            }
        } while (opcao != 0);
        System.out.println("Clientes que não aceitaram ou não estavam adequados para a oferta");
        abbCPF.show(abbCPF.root);
        abbCNPJ.show(abbCNPJ.root);
        /*
         * Esvazia as ABBs apresentando todos os clientes que aguardam nova portunidade
         */
    }
}