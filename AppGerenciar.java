import java.util.Scanner;

public class AppGerenciar {
    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            Gerenciar agencia1 = new Gerenciar("Bem-vindo ao seu gerenciador de contas pessoais", 7);
            int op;
            do {
                System.out.println("\n\n\n---- MENU ----");
                System.out.println("1 - Cadastrar conta");
                System.out.println("2 - Excluir conta");
                System.out.println("3 - Mesclar Contas");
                System.out.println("4 - Extrato da conta");
                System.out.println("5 - Incluir transação");
                System.out.println("6 - Editar a última transação");
                System.out.println("7 - Transferir fundos");
                System.out.println("8 - Resumo das contas");
                System.out.println("9 - Resumo de receitas e despesas do mês");
                System.out.println("10 - Saldo geral dos últimos 6 meses");
                System.out.println("11 - Crie ou gerencie seu cofrinho");
                System.out.println("0 - Sair");
                System.out.print("Informe a opção: ");
                op = in.nextInt();
                switch (op) {
                    case 0:
                        System.out.println("Fim");
                        break;
                    case 1:
                        System.out.println("\n\n\nCadastrar conta\n");
                        agencia1.cadastrarConta(in);
                        break;
                    case 2:
                        System.out.println("\n\n\nExcluir conta\n");
                        System.out.print("Informe o número da conta: ");
                        int num = in.nextInt();
                        boolean result = agencia1.excluiLogico(num);
                        if (result) {
                            System.out.println("Conta excluída com sucesso.");
                        } else {
                            System.out.println("Conta não encontrada ou não pode ser excluída.");
                        }
                        break;
                    case 3:
                        System.out.println("\n\n\nMesclar Contas\n");
                        System.out.print("Informe o número da primeira conta: ");
                        int numConta1 = in.nextInt();
                        System.out.print("Informe o número da segunda conta: ");
                        int numConta2 = in.nextInt();
                        agencia1.mesclarContas(numConta1, numConta2);
                        break;
                    case 4:
                        System.out.println("\n\n\nExtrato da conta\n");
                        System.out.print("Informe o número da conta para imprimir o extrato: ");
                        int extratoConta = in.nextInt();
                        agencia1.imprimirExtrato(extratoConta);
                        break;
                    case 5:
                        System.out.println("\n\n\nIncluir transação\n");
                        agencia1.incluirTransacao(in);
                        break;
                    case 6:
                        System.out.println("\n\n\nEditar a última transação\n");
                        agencia1.editarUltimaTransacao(in);
                        break;
                    case 7:
                        System.out.println("\n\n\nTransferir fundos\n");  
                        agencia1.transferirFundos(in);  
                        break;
                    case 8:
                        System.out.println("\n\n\nResumos das contas\n");  
                        agencia1.resumoContas();
                        break;
                    case 9:
                        System.out.println("\n\n\nResumos de receitas e despesas do mês\n");
                        agencia1.exibirResumoReceitasDespesasMes();   
                        break;
                    case 10:
                        System.out.println("\n\n\nSaldo geral dos últimos 6 meses\n");    
                        agencia1.saldoGeralUltimosSeisMeses();
                        break;            
                    case 11:
                    System.out.println("\n\n\nCofrinho\n");
                    Scanner scanner = new Scanner(System.in); // Crie uma instância do Scanner
                    agencia1.cofrinho(scanner);
                    break;
                    default:
                        System.out.println("Opção inválida");
                }
            } while (op != 0);
        }
    }
}
