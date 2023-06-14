import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Gerenciar {
    Scanner scanner = new Scanner(System.in);
    private String nome;
    private Conta[] listaContas;
    private int cv; // controla o preenchimento do vetor
    private List<Conta> contas; 


    public Gerenciar(String nomeAgencia, int quantidade) {
    if (nomeAgencia == null) nome = "Nome Indefinido";
    else nome = nomeAgencia;
    if (quantidade <= 0) quantidade = 5;
    listaContas = new Conta[quantidade];
    cv = 0;
    contas = new ArrayList<>(); // Inicializa a lista de contas
}

    public void cadastrarConta(Scanner in) {
        System.out.print("Informe o número da conta: ");
        int numConta = lerValorInt(in, "Número da conta inválido. O cadastro não pode ser realizado.");

        System.out.print("Informe o banco: ");
        String banco =  in.next();

        System.out.print("Informe o número da agência: ");
        int numAgencia = lerValorInt(in, "Número da agência inválido. O cadastro não pode ser realizado.");

        // Verificar se a conta já existe
        if (buscarConta(numConta) != null) {
            System.out.println("A conta já está cadastrada. O cadastro não pode ser realizado.");
            return;
        }

        // Criar uma nova conta
        Conta novaConta = new Conta(numConta, banco, numAgencia);

        // Adicionar a nova conta à lista de contas
        if (cv < listaContas.length) {
            listaContas[cv] = novaConta;
            cv++;
            System.out.println("Conta cadastrada com sucesso!");
        } else {
            System.out.println("Não é possível cadastrar mais contas. Limite de contas atingido.");
        }
    }

    

    public Conta buscarConta(int numConta) {
        for (Conta conta : listaContas) {
            if (conta != null && conta.getNumero() == numConta) {
                return conta;
            }
        }
        return null; // Conta não encontrada
    }

    public void mesclarContas(int numeroConta1, int numeroConta2) {
    Conta conta1 = buscarConta(numeroConta1);
    Conta conta2 = buscarConta(numeroConta2);
    
    if (conta1 != null && conta2 != null) {
        List<Transacao> transacoesConta2 = conta2.getTransacoes();
        for (Transacao transacao : transacoesConta2) {
            conta1.adicionarTransacao(transacao);
        }
        
        excluiLogico(numeroConta2);
        
        System.out.println("Contas mescladas com sucesso.");
    } else {
        System.out.println("Não foi possível mesclar as contas. Verifique os números informados.");
    }
}

    public Conta busca(int numero) {
        for (int i = 0; i < cv; i++) {
            if (listaContas[i].estaAtiva() && listaContas[i].getNumero() == numero) return listaContas[i];
        }
        return null;
    }

    public int buscaPosicao(int numero) {
        for (int i = 0; i < cv; i++) {
            if (listaContas[i].getNumero() == numero) return i;
        }
        return -1;
    }

    public boolean excluiLogico(int numero) {
        int posicao = buscaPosicao(numero);
        if (posicao == -1) return false;
        if (listaContas[posicao].estaAtiva() == false) return false;
        listaContas[posicao].inativar();
        return true;
    }

    public Conta buscaConta(int numero) {
        for (Conta conta : listaContas) {
            if (conta != null && conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }

    public void imprimirExtrato(int numeroConta) {
        Conta conta = buscaConta(numeroConta);
        if (conta != null) {
            List<Transacao> transacoes = conta.getTransacoes();
            System.out.println("Extrato da Conta " + conta.getNumero() + ":");
            if (transacoes.isEmpty()) {
                System.out.println("Não há transações disponíveis.");
            } else {
                for (Transacao transacao : transacoes) {
                    System.out.println(transacao);
                }
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

      public void incluirTransacao(Scanner in) {
        System.out.print("Informe o número da conta: ");
        int numeroConta = in.nextInt();
        Conta conta = buscaConta(numeroConta);
        
        if (conta != null) {
            System.out.print("Informe a data da transação (dd/MM/yyyy): ");
            String dataString = in.next();
            System.out.print("Informe o valor da transação: ");
            double valor = in.nextDouble();
            in.nextLine(); // Limpar o buffer do scanner
            System.out.print("Informe a descrição da transação: ");
            String descricao = in.nextLine();
            
            // Converter a string de data para um objeto Date
            Date data = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                data = dateFormat.parse(dataString);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido.");
                return;
            }
            
            Transacao transacao = new Transacao(data, valor, descricao);
            conta.adicionarTransacao(transacao);
            conta.atualizarSaldo();
            
            System.out.println("Transação incluída com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }  

     public void editarUltimaTransacao(Scanner in) {
        System.out.print("Informe o número da conta: ");
        int numeroConta = in.nextInt();
        Conta conta = buscaConta(numeroConta);
        
        if (conta != null) {
            List<Transacao> transacoes = conta.getTransacoes();
            
            if (!transacoes.isEmpty()) {
                Transacao ultimaTransacao = transacoes.get(transacoes.size() - 1);
                
                System.out.print("Informe o novo valor da transação: ");
                double novoValor = in.nextDouble();
                in.nextLine(); // Limpar o buffer do scanner
                
                System.out.print("Informe a nova descrição da transação: ");
                String novaDescricao = in.nextLine();
                
                ultimaTransacao.setValor(novoValor);
                ultimaTransacao.setDescricao(novaDescricao);
                
                conta.atualizarSaldo();
                System.out.println("Última transação editada com sucesso.");
            } else {
                System.out.println("Não há transações disponíveis para editar.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
        public void transferirFundos(Scanner in) {
        System.out.print("Informe o número da conta de origem: ");
        int numContaOrigem = in.nextInt();
        Conta contaOrigem = buscaConta(numContaOrigem);
        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }
        
        System.out.print("Informe o número da conta de destino: ");
        int numContaDestino = in.nextInt();
        Conta contaDestino = buscaConta(numContaDestino);
        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }
        
        System.out.print("Informe o valor a ser transferido: ");
        double valorTransferencia = in.nextDouble();
        
        if (contaOrigem.getSaldo() < valorTransferencia) {
            System.out.println("Saldo insuficiente na conta de origem.");
            return;
        }
        
        Transacao saque = new Transacao(new Date(), -valorTransferencia, "Transferência para conta " + numContaDestino);
        Transacao deposito = new Transacao(new Date(), valorTransferencia, "Transferência da conta " + numContaOrigem);
        
        contaOrigem.adicionarTransacao(saque);
        contaOrigem.atualizarSaldo();
        
        contaDestino.adicionarTransacao(deposito);
        contaDestino.atualizarSaldo();
        
        System.out.println("Transferência realizada com sucesso.");
    }

    public void resumoContas() {
    double saldoTotal = 0;
    
    System.out.println("\nResumo das contas:");
    System.out.println("-------------------");
    
    for (Conta conta : contas) {
        System.out.println("Número da conta: " + conta.getNumero());
        System.out.println("Saldo atual: " + conta.getSaldo());
        System.out.println("-------------------");
        
        saldoTotal += conta.getSaldo();
    }
    
    System.out.println("Saldo total do usuário: " + saldoTotal);
}

    public void exibirResumoReceitasDespesasMes() {
        // Obter o mês e ano atual
        Calendar calendar = Calendar.getInstance();
        int mesAtual = calendar.get(Calendar.MONTH) + 1;
        int anoAtual = calendar.get(Calendar.YEAR);
        
        double totalReceitas = 0.0;
        double totalDespesas = 0.0;
        
        // Iterar sobre todas as contas e somar as receitas e despesas do mês atual
        for (Conta conta : contas) {
            List<Transacao> transacoes = conta.getTransacoes();
            
            for (Transacao transacao : transacoes) {
                Calendar calendarTransacao = Calendar.getInstance();
                calendarTransacao.setTime(transacao.getData());
                int mesTransacao = calendarTransacao.get(Calendar.MONTH) + 1;
                int anoTransacao = calendarTransacao.get(Calendar.YEAR);
                
                if (mesTransacao == mesAtual && anoTransacao == anoAtual) {
                    if (transacao.getValor() >= 0) {
                        totalReceitas += transacao.getValor();
                    } else {
                        totalDespesas += transacao.getValor();
                    }
                }
            }
        }
        
        System.out.println("Resumo de receitas e despesas do mês atual:");
        System.out.println("Total de receitas: " + totalReceitas);
        System.out.println("Total de despesas: " + totalDespesas);
    }

            public void saldoGeralUltimosSeisMeses() {
            System.out.println("Saldo geral dos últimos 6 meses");

            // Obtém a data atual
            LocalDate dataAtual = LocalDate.now();

            // Percorre os últimos 6 meses
            for (int i = 0; i < 6; i++) {
                // Obtém a data do mês atual menos o número de meses
                LocalDate dataMesAnterior = dataAtual.minusMonths(i);

                // Obtém o saldo total do mês atual
                double saldoTotalMes = 0;

                // Percorre todas as contas
                for (Conta conta : contas) {
                    // Obtém o saldo da conta para o mês atual
                    double saldoMes = conta.getSaldoMes(dataMesAnterior);
                    
                    // Soma o saldo da conta ao saldo total do mês
                    saldoTotalMes += saldoMes;
                }

                // Exibe o saldo do mês atual
                System.out.println("Mês: " + dataMesAnterior.getMonth().toString() + " - Saldo: " + saldoTotalMes);
            }
        }

    public void cofrinho(Scanner scanner) {
    System.out.println("Digite o número da conta: ");
    int numeroConta = scanner.nextInt();
    scanner.nextLine(); // Limpar o buffer do scanner
    
    Conta conta = buscarConta(numeroConta); // Verificar se a conta existe

    if (conta != null) {
        System.out.println("Deseja criar um novo cofrinho ou adicionar dinheiro em um já existente?");
        System.out.println("1. Novo Cofrinho");
        System.out.println("2. Cofrinho Existente");
        System.out.print("Escolha a opção (1 ou 2): ");
        int opcao = lerValorInt(scanner, "Opção inválida. Por favor, digite novamente.");

        if (opcao == 1) {
            System.out.print("Digite o apelido do novo cofrinho: ");
            String apelido = scanner.next();
            // Criar um novo cofrinho com o apelido fornecido
            // ...
            System.out.println("Novo cofrinho criado com o apelido: " + apelido);
        } else if (opcao == 2) {
            System.out.print("Digite o apelido do cofrinho existente: ");
            String apelido = scanner.next();

            if (verificarCofrinhoExistente(apelido)) {
                System.out.print("Digite o valor a ser adicionado ao cofrinho: ");
                double valor = lerValorDouble(scanner, "Valor inválido. Por favor, digite novamente.");

                if (conta.getSaldo() >= valor) {
                    conta.adicionarTransacao(new Transacao(valor, "Dinheiro adicionado ao cofrinho"));
                    System.out.println("Dinheiro adicionado ao cofrinho com o apelido: " + apelido);
                } else {
                    System.out.println("Saldo insuficiente na conta. Não é possível adicionar dinheiro ao cofrinho.");
                }
            } else {
                System.out.println("Cofrinho com o apelido " + apelido + " não encontrado.");
            }
        } else {
            System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    } else {
        System.out.println("Conta com o número " + numeroConta + " não encontrada.");
    }
}


    private boolean verificarCofrinhoExistente(String apelido) {
    Cofrinho[] listaCofrinhos = new Cofrinho[2]; // Substitua o tamanho do array pelo tamanho real da lista

    // Popule o array com os cofrinhos existentes
    listaCofrinhos[0] = new Cofrinho("Cofrinho 1");
    listaCofrinhos[1] = new Cofrinho("Cofrinho 2");

    for (Cofrinho cofrinho : listaCofrinhos) {
        if (cofrinho.getApelido().equals(apelido)) {
            return true; // Cofrinho encontrado com o apelido fornecido
        }
    }
    return false; // Cofrinho não encontrado com o apelido fornecido
}



    private int lerValorInt(Scanner in, String mensagemErro) {
        while (!in.hasNextInt()) {
            System.out.println(mensagemErro);
            in.next();
        }
        return in.nextInt();
    }

    public double lerValorDouble(Scanner scanner, String mensagemErro) {
    while (!scanner.hasNextDouble()) {
        System.out.println(mensagemErro);
        scanner.nextLine(); // Limpar o buffer do scanner
    }
    return scanner.nextDouble();
    }


    public String toString() {
        String msg = "Nome da Agência: " + nome + "\n";
        if (cv == 0) msg = msg + "Contas não cadastradas";
        else {
            for (int i = 0; i < cv; i++) {
                msg = msg + listaContas[i].toString() + "\n";
            }
        }
        return msg;
    }
}
