import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Exercício 2 — Sistema de Compra Online
 * Versão básica de console.
 */
public class CompraOnline {

    static final double FRETE_GRATIS_ACIMA_DE = 500.00;
    static final double TAXA_FRETE = 25.00;

    static class Produto {
        String nome;
        double preco;

        Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Produto[] catalogo = {
                new Produto("Mouse", 80.00),
                new Produto("Teclado", 150.00),
                new Produto("Monitor", 700.00),
                new Produto("Headset", 200.00)
        };

        System.out.println("=== LOJA VIRTUAL ===");

        // ENTRADA: carrinho
        List<Produto> carrinho = new ArrayList<>();
        int opcao;
        do {
            System.out.println("\nProdutos:");
            for (int i = 0; i < catalogo.length; i++) {
                System.out.printf("%d - %s (R$ %.2f)%n", i + 1, catalogo[i].nome, catalogo[i].preco);
            }
            System.out.println("0 - Finalizar compra");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(sc.nextLine().trim());

            if (opcao >= 1 && opcao <= catalogo.length) {
                carrinho.add(catalogo[opcao - 1]);
                System.out.println(catalogo[opcao - 1].nome + " adicionado ao carrinho.");
            } else if (opcao != 0) {
                System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        if (carrinho.isEmpty()) {
            System.out.println("Carrinho vazio.");
            System.out.println("Sistema encerrado.");
            return;
        }

        // CÁLCULO: valor total
        double total = 0;
        for (Produto p : carrinho) total += p.preco;
        System.out.printf("%nTotal dos produtos: R$ %.2f%n", total);

        // ENTRADA: dados do cliente
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("Forma de pagamento (cartão de crédito): ");
        String pagamento = sc.nextLine();

        // DECISÃO: frete grátis?
        double frete;
        if (total > FRETE_GRATIS_ACIMA_DE) {
            frete = 0;
            System.out.println("Frete grátis!");
        } else {
            frete = TAXA_FRETE;
            System.out.printf("Taxa de frete: R$ %.2f%n", frete);
        }

        // CÁLCULO: total final
        double totalFinal = total + frete;
        System.out.printf("Total a pagar: R$ %.2f%n", totalFinal);

        // DECISÃO: cartão aprovado?
        System.out.print("O cartão foi aprovado? (s/n): ");
        String resposta = sc.nextLine().trim().toLowerCase();

        if (!resposta.equals("s")) {
            System.out.println("Pagamento não autorizado.");
            System.out.println("Pedido encerrado.");
            return;
        }

        // PROCESSOS AUTOMÁTICOS
        int numeroPedido = 1000 + new Random().nextInt(9000);          // gera nº do pedido
        System.out.println("Registrando compra... (pedido #" + numeroPedido + ")"); // registra compra
        System.out.println("Enviando confirmação para " + nome + " (" + endereco + ")..."); // envia confirmação

        System.out.println("Compra realizada com sucesso.");
        System.out.println("Sistema encerrado.");
    }
}