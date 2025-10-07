package br.fiap.main;

public class Cliente {
    private int numeroConta;
    private String nome;
    private String cpfCnpj;
    private String tipoConta;
    private double saldo;

    public Cliente(int numeroConta, String nome, String cpfCnpj, String tipoConta, double saldo) {
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void adicionarSaldo(double valor) {
        saldo += valor;
    }

    @Override
    public String toString() {
        return "Cliente " + nome + " - " + numeroConta  + "\n" +
                "Conta " + tipoConta + " - " + cpfCnpj + "\n" +
                "Saldo em aplicações: R$" + saldo;
    }
}
