import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Exercício 1 — Sistema de Controle de Biblioteca Escolar
 * Versão básica de console (sem banco de dados: os dados ficam em memória).
 */
public class Biblioteca {

    static final int LIMITE_EMPRESTIMOS = 3;

    // ---------- "Banco de dados" em memória ----------
    static class Aluno {
        String matricula;
        String nome;
        boolean multaPendente;
        int livrosEmprestados;

        Aluno(String matricula, String nome, boolean multaPendente, int livrosEmprestados) {
            this.matricula = matricula;
            this.nome = nome;
            this.multaPendente = multaPendente;
            this.livrosEmprestados = livrosEmprestados;
        }
    }

    static class Livro {
        String codigo;
        String titulo;
        int estoque;

        Livro(String codigo, String titulo, int estoque) {
            this.codigo = codigo;
            this.titulo = titulo;
            this.estoque = estoque;
        }
    }

    static class Emprestimo {
        String matricula;
        String codigoLivro;

        Emprestimo(String matricula, String codigoLivro) {
            this.matricula = matricula;
            this.codigoLivro = codigoLivro;
        }
    }

    static List<Aluno> alunos = new ArrayList<>();
    static List<Livro> livros = new ArrayList<>();
    static List<Emprestimo> emprestimos = new ArrayList<>();

    static void carregarDadosDeExemplo() {
        alunos.add(new Aluno("1001", "Ana", false, 0));
        alunos.add(new Aluno("1002", "Bruno", true, 1));   // tem multa
        alunos.add(new Aluno("1003", "Carla", false, 3));  // já tem 3 livros

        livros.add(new Livro("L01", "Java: Como Programar", 2));
        livros.add(new Livro("L02", "Banco de Dados com MySQL", 1));
        livros.add(new Livro("L03", "Algoritmos e Lógica", 0)); // indisponível
    }

    static Aluno buscarAluno(String matricula) {
        for (Aluno a : alunos) {
            if (a.matricula.equals(matricula)) return a;
        }
        return null;
    }

    static Livro buscarLivro(String codigo) {
        for (Livro l : livros) {
            if (l.codigo.equalsIgnoreCase(codigo)) return l;
        }
        return null;
    }

    // ---------- Fluxo do sistema ----------
    public static void main(String[] args) {
        carregarDadosDeExemplo();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== BIBLIOTECA ESCOLAR ===");
        System.out.println("(matrículas de teste: 1001 = ok | 1002 = multa | 1003 = limite)");

        // ENTRADA: matrícula
        System.out.print("Informe sua matrícula: ");
        String matricula = sc.nextLine().trim();

        // DECISÃO 1: matrícula existe?
        Aluno aluno = buscarAluno(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            System.out.println("Sistema encerrado.");
            return;
        }

        // DECISÃO 2: multa pendente?
        if (aluno.multaPendente) {
            System.out.println("Empréstimo bloqueado por multa.");
            System.out.println("Sistema encerrado.");
            return;
        }

        // DECISÃO 3: limite de 3 livros?
        if (aluno.livrosEmprestados >= LIMITE_EMPRESTIMOS) {
            System.out.println("Limite de empréstimos atingido.");
            System.out.println("Sistema encerrado.");
            return;
        }

        // ENTRADA: código do livro
        System.out.print("Informe o código do livro (L01, L02, L03): ");
        String codigo = sc.nextLine().trim();

        // DECISÃO 4: livro existe e está disponível?
        Livro livro = buscarLivro(codigo);
        if (livro == null || livro.estoque <= 0) {
            System.out.println("Livro indisponível.");
            System.out.println("Sistema encerrado.");
            return;
        }

        // PROCESSAMENTO: registra empréstimo e atualiza estoque
        emprestimos.add(new Emprestimo(aluno.matricula, livro.codigo));
        livro.estoque--;
        aluno.livrosEmprestados++;

        System.out.println("Empréstimo realizado com sucesso.");
        System.out.println("Sistema encerrado.");
    }
}