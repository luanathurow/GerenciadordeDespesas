import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

public class Conta {
    private int numero;
    private double saldo;
    private boolean ativa;
    private ArrayList<Transacao> transacoes;
    private List<Cofrinho> listaCofrinhos;

    public Conta(int numero, String banco, int numAgencia) {
        this.numero = numero;
        this.saldo = 0;
        this.ativa = true;
        this.transacoes = new ArrayList<>();
        listaCofrinhos = new ArrayList<>();
        
    }

    public Conta() {
        numero = 1;
        saldo = 0;
        ativa = true;
        transacoes = new ArrayList<>();
    }

    public boolean estaAtiva() {
        return ativa;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void ativar() {
        ativa = true;
    }

    public void inativar() {
        ativa = false;
    }

    public String toString() {
        String estado = ativa ? "Conta ativa" : "Conta inativa";
        return "Numero: " + numero + " - Saldo: R$ " + saldo + " - Estado: " + estado;
    }

    public boolean depositar(double valor) {
        if (ativa && valor > 0) {
            saldo += valor;
            return true;
        }
        return false;
    }

    public boolean sacar(double valor) {
        if (ativa && valor > 0 && valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public boolean transferir(Conta destino, double valor) {
        if (ativa && sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public ArrayList<Transacao> getTransacoes() {
        return transacoes;
    }

    public void atualizarSaldo() {
        double novoSaldo = 0.0;
        for (Transacao transacao : transacoes) {
            novoSaldo += transacao.getValor();
        }
        saldo = novoSaldo;
    }

    public void imprimirExtrato() {
        System.out.println("Extrato da conta: " + numero);
        System.out.println("------------------------------------------");
        System.out.println("Data\t\tDescrição\t\tValor\t\tSaldo");

        // Ordenar as transações por data
        Collections.sort(transacoes, Comparator.comparing(Transacao::getData));

        if (transacoes.isEmpty()) {
            System.out.println("Não há transações disponíveis.");
        } else {
            double saldoAtual = saldo;
            for (Transacao transacao : transacoes) {
                saldoAtual += transacao.getValor();

                System.out.printf("%s\t%s\t\t%.2f\t\t%.2f%n", transacao.getData(), transacao.getDescricao(),
                        transacao.getValor(), saldoAtual);
            }
        }

        System.out.println("------------------------------------------");
        System.out.println("Saldo atual: " + saldo);
    }

    public void editarUltimaTransacao(double novoValor, String novaDescricao) {
        if (!transacoes.isEmpty()) {
            Transacao ultimaTransacao = transacoes.get(transacoes.size() - 1);
            ultimaTransacao.setValor(novoValor);
            ultimaTransacao.setDescricao(novaDescricao);
            atualizarSaldo();
            System.out.println("Última transação editada com sucesso.");
        } else {
            System.out.println("Não há transações disponíveis para editar.");
        }
    }

    public double getSaldoMes(LocalDate data) {
        double saldoMes = 0.0;
        for (Transacao transacao : transacoes) {
           LocalDate transacaoDate = transacao.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (transacaoDate.getMonthValue() == data.getMonthValue() && transacaoDate.getYear() == data.getYear()) {
            saldoMes += transacao.getValor();
        }

        }
        return saldoMes;
    }

    public boolean verificarCofrinhoExistente(String apelido) {
        for (Cofrinho cofrinho : listaCofrinhos) {
            if (cofrinho.getApelido().equals(apelido)) {
                return true; // Cofrinho encontrado com o apelido fornecido
            }
        }
        return false; // Cofrinho não encontrado com o apelido fornecido
    }

    public double getSaldoGeralUltimosSeisMeses() {
        double saldoGeral = 0.0;
        LocalDate dataAtual = LocalDate.now();

        for (int i = 0; i < 6; i++) {
            LocalDate dataMes = dataAtual.minusMonths(i);
            saldoGeral += getSaldoMes(dataMes);
        }

        return saldoGeral;
    }

}
