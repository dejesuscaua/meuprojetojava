import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


class  Produto {
    String nome;
    double precoproduto;
    int quantidade;
    double precoOriginal;
    double desconto;

    double lucroFinal;


    Produto(String nome, double precoproduto, int quantidade, double precoOriginal, double desconto) {
        this.nome = nome;
        this.precoproduto = precoproduto;
        this.quantidade = quantidade;
        this.precoOriginal = precoOriginal;
        this.desconto = desconto;
    }
}

public class Main {

    public static void salvarDados(List<Produto> listaProdutos) throws IOException {

        // Criando um novo arquivo "produtos.txt"
        File arquivo = new File("produtos.txt");

        // Verificando se o arquivo existe, se não, cria um novo
        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }

        // Criando um PrintWriter para escrever no arquivo
        PrintWriter escritor = new PrintWriter(new FileWriter(arquivo));

        // Escrevendo cada produto no arquivo
        for (Produto produto : listaProdutos) {
            escritor.println(produto.nome + "," + produto.precoproduto + "," + produto.quantidade + "," + produto.precoOriginal + "," + produto.desconto);
        }

        // Fechando o PrintWriter
        escritor.close();

        System.out.println("Dados salvos com sucesso!");
    }


    public static List<Produto> lerDados() throws IOException {

        // Criando um novo arquivo "produtos.txt"
        File arquivo = new File("produtos.txt");

        // Verificando se o arquivo existe
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        // Criando um Scanner para ler o arquivo
        Scanner scanner = new Scanner(arquivo);

        // Criando uma lista vazia para armazenar os produtos
        List<Produto> listaProdutos = new ArrayList<>();

        // Lendo cada linha do arquivo e criando um novo produto
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String[] dados = linha.split(",");

            // Criando um novo produto a partir dos dados da linha
            Produto produto = new Produto(dados[0], Double.parseDouble(dados[1]), Integer.parseInt(dados[2]), Double.parseDouble(dados[3]), Double.parseDouble(dados[4]));

            // Adicionando o produto à lista
            listaProdutos.add(produto);
        }

        // Fechando o Scanner
        scanner.close();

        return listaProdutos;
    }

    public static String input(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(msg);
        return scanner.nextLine();
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        List<Produto> listaProdutos = lerDados();

        int decisaoMenu = 0;

        while (decisaoMenu != 5) {
            System.out.println("Menu:");
            System.out.println("1: Adicionar um novo produto");
            System.out.println("2: Ver os produtos já adicionados");
            System.out.println("3: Modificar algum produto");
            System.out.println("4: remover um produto");
            System.out.println("5: Sair do programa");
            decisaoMenu = Integer.parseInt(input("Escolha uma opção: "));

            switch (decisaoMenu) {
                case 1:
                    adicionarProduto(listaProdutos);
                    salvarDados(listaProdutos);
                    break;
                case 2:
                    verProdutos(listaProdutos);
                    break;
                case 3:
                    modificarProduto(listaProdutos);
                    salvarDados(listaProdutos);
                    break;
                case 4:
                    removerProduto(listaProdutos);
                    salvarDados(listaProdutos);
                    break;
                case 5:
                    salvarDados(listaProdutos);
                    System.out.println("Saindo do programa...");
                    break;

                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        }

        salvarDados(listaProdutos);
        scanner.close();
    }

    public static void adicionarProduto(List<Produto> listaProdutos) {
        String nomeProduto = input("Digite o nome do produto: ");
        double precoproduto = Double.parseDouble(input("Digite o preço do produto: "));
        int quantidade = Integer.parseInt(input("Digite a quantidade do produto: "));
        double precoOriginal = Double.parseDouble(input("Digite o preço original do produto: "));
        double desconto = Double.parseDouble(input("Digite o desconto do produto: "));

        Produto produto = new Produto(nomeProduto, precoproduto, quantidade, precoOriginal, desconto);
        listaProdutos.add(produto);

        System.out.println("Produto adicionado com sucesso!");
    }

    public static void verProdutos(List<Produto> listaProdutos) {

        if (listaProdutos.isEmpty()) {
            System.out.println("Não há produtos cadastrados.");
        } else {
            System.out.println("Produtos cadastrados:");
            for (Produto produto : listaProdutos) {

                double valorDesconto = produto.precoproduto * (produto.desconto / 100);

                double valorFinal = produto.precoproduto - valorDesconto;

                double calcularLucro = valorFinal - produto.precoOriginal;

                produto.lucroFinal = calcularLucro * produto.quantidade;

                System.out.println();
                System.out.println("*******************************************");
                System.out.println();
                System.out.println("Nome do produto: " + produto.nome);
                System.out.println("Preço do produto: $" + produto.precoproduto);
                System.out.println("Quantidade: " + produto.quantidade);
                System.out.println("Preço original: $" + produto.precoOriginal);
                System.out.println("Desconto: " + produto.desconto + "%");
                System.out.println(String.format("O valor final será: $%.2f", valorFinal));
                System.out.println(String.format("Seu lucro será: $%.2f", produto.lucroFinal));
                System.out.println();
                System.out.println("*******************************************");
                System.out.println();
            }
        }
    }

    public static void modificarProduto(List<Produto> listaProdutos) {
        String nomeProduto = input("Digite o nome do produto que deseja modificar: ");
        for (Produto produto : listaProdutos) {
            if (produto.nome.equals(nomeProduto)) {
                System.out.println(
                        "                    1 = preço do produto" +
                                "                    2 = quantidade do produto" +
                                "                    3 = preço original" +
                                "                    4 = desconto" +
                                "                    ");

                int mudar = Integer.parseInt(input("Digite o que você quer mudar: "));
                switch (mudar) {
                    case 1:
                        double mudarprecoproduto = Double.parseDouble(input("Digite o novo preço do produto: "));
                        produto.precoproduto = mudarprecoproduto;
                        System.out.println("O novo preço foi: " + produto.precoproduto);
                        break;
                    case 2:
                        int mudarquantidade = Integer.parseInt(input("Digite a nova quantidade do produto: "));
                        produto.quantidade = mudarquantidade;
                        System.out.println("A nova quantidade foi: " + produto.quantidade);
                        break;
                    case 3:
                        double mudarprecoOriginal = Double.parseDouble(input("Digite o novo preço original do produto: "));
                        produto.precoOriginal = mudarprecoOriginal;
                        System.out.println("O novo preço original é: " + produto.precoOriginal);
                        break;
                    case 4:
                        double mudardesconto = Double.parseDouble(input("Digite o novo desconto do produto: "));
                        produto.desconto = mudardesconto;
                        int novatransformar = (int) (produto.desconto * 100);
                        System.out.println("O novo desconto é de: " + novatransformar + "%");
                        double mudarcalculardesconto = produto.precoproduto * produto.desconto;
                        System.out.println("O novo desconto foi de: " + mudarcalculardesconto);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }
        }

    }

    public static void removerProduto(List<Produto> listaProdutos) {
        String removerNome = input("Digite o nome do produto que deseja remover: ");
        Produto produtoParaRemover = null;

        for (Produto produto : listaProdutos) {
            if (produto.nome.equals(removerNome)) {
                produtoParaRemover = produto;
                break;
            }
        }

        if (produtoParaRemover != null) {
            listaProdutos.remove(produtoParaRemover);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Produto não encontrado na lista.");
        }
    }

}
