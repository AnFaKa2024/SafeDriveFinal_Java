package org.SafeDrive.Operacional;

import org.SafeDrive.Modelo.Oficina;
import org.SafeDrive.Modelo.Endereco;
import org.SafeDrive.Modelo.Orcamento;
import org.SafeDrive.Repositorio.*;

import java.util.List;
import java.util.Scanner;

public class MenuOficina {

    private static final Scanner scanner = new Scanner(System.in);
    private static final RepositorioOficina repositorioOficina = new RepositorioOficina();
    private static final RepositorioEndereco repositorioEndereco = new RepositorioEndereco();
    private static final RepositorioOrcamento repositorioOrcamento = new RepositorioOrcamento();

    public void iniciarMenuOficina() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> atualizarOficina();
                case 2 -> removerOficina();
                case 3 -> adicionarEndereco();
                case 4 -> atualizarEndereco();
                case 5 -> listarEnderecos();
                case 6 -> removerEndereco();
                case 7 -> definirMaoDeObra();
                case 8 -> atualizarMaoDeObra();
                case 9 -> listarMaoDeObra();
                case 10 -> removerMaoDeObra();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== MENU OFICINA =====");
        System.out.println("1. Atualizar Oficina");
        System.out.println("2. Remover Oficina");
        System.out.println("3. Adicionar Endereço");
        System.out.println("4. Atualizar Endereço");
        System.out.println("5. Listar Endereços");
        System.out.println("6. Remover Endereço");
        System.out.println("7. Definir Mão-de-Obra");
        System.out.println("8. Atualizar Mão-de-Obra");
        System.out.println("9. Listar Mão-de-Obra");
        System.out.println("10. Remover Mão-de-Obra");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); // Limpa o scanner
            return -1;
        }
    }

    private void atualizarOficina() {
        System.out.print("ID da Oficina a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Oficina oficina = repositorioOficina.buscarPorId(id);
        if (oficina != null) {
            System.out.print("Novo nome da oficina: ");
            oficina.setNomeOficina(scanner.nextLine());

            System.out.print("Nova especialidade: ");
            oficina.setEspecialidade(scanner.nextLine());

            System.out.print("Novo telefone: ");
            oficina.setTelefone(scanner.nextLine());

            repositorioOficina.atualizar(oficina);
            System.out.println("Oficina atualizada com sucesso!");
        } else {
            System.out.println("Oficina não encontrada.");
        }
    }

    private void removerOficina() {
        System.out.print("ID da Oficina a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repositorioOficina.remover(id);
        System.out.println("Oficina removida com sucesso!");
    }

    private void adicionarEndereco() {
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);
        repositorioEndereco.adicionar(endereco);
        System.out.println("Endereço adicionado com sucesso!");
    }

    private void atualizarEndereco() {
        System.out.print("ID do Endereço a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Endereco endereco = repositorioEndereco.buscarPorId(id);
        if (endereco != null) {
            System.out.print("Novo logradouro: ");
            endereco.setLogradouro(scanner.nextLine());

            System.out.print("Novo número: ");
            endereco.setNumero(scanner.nextLine());

            System.out.print("Novo complemento: ");
            endereco.setComplemento(scanner.nextLine());

            System.out.print("Novo bairro: ");
            endereco.setBairro(scanner.nextLine());

            System.out.print("Nova cidade: ");
            endereco.setCidade(scanner.nextLine());

            System.out.print("Novo estado: ");
            endereco.setEstado(scanner.nextLine());

            System.out.print("Novo CEP: ");
            endereco.setCep(scanner.nextLine());

            repositorioEndereco.atualizar(endereco);
            System.out.println("Endereço atualizado com sucesso!");
        } else {
            System.out.println("Endereço não encontrado.");
        }
    }

    private void listarEnderecos() {
        List<Endereco> enderecos = repositorioEndereco.listar();
        if (!enderecos.isEmpty()) {
            System.out.println("--- Lista de Endereços ---");
            for (Endereco endereco : enderecos) {
                System.out.println(endereco);
            }
        } else {
            System.out.println("Nenhum endereço cadastrado.");
        }
    }

    private void removerEndereco() {
        System.out.print("ID do Endereço a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repositorioEndereco.remover(id);
        System.out.println("Endereço removido com sucesso!");
    }

    private void definirMaoDeObra() {
        System.out.print("Digite o valor da mão-de-obra: ");
        double maoDeObra = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o tipo de orçamento: ");
        String tipo = scanner.nextLine();

        Orcamento orcamento = new Orcamento();
        orcamento.setMaoDeObra(maoDeObra);
        orcamento.setPecas(0.0); // Valor da peça sempre zero, será definido em Python
        orcamento.setTipo(tipo);

        repositorioOrcamento.adicionar(orcamento);
        System.out.println("Mão-de-obra definida com sucesso!");
    }

    private void atualizarMaoDeObra() {
        System.out.print("ID do orçamento para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Orcamento orcamento = repositorioOrcamento.buscarPorId(id);
        if (orcamento != null) {
            System.out.print("Novo valor da mão-de-obra: ");
            double novaMaoDeObra = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Novo tipo de orçamento: ");
            String novoTipo = scanner.nextLine();

            orcamento.setMaoDeObra(novaMaoDeObra);
            orcamento.setPecas(0.0); // Valor da peça sempre zero, será definido em Python
            orcamento.setTipo(novoTipo);

            repositorioOrcamento.atualizar(orcamento);
            System.out.println("Mão-de-obra atualizada com sucesso!");
        } else {
            System.out.println("Orçamento não encontrado.");
        }
    }

    private void listarMaoDeObra() {
        List<Orcamento> orcamentos = repositorioOrcamento.listar();
        if (!orcamentos.isEmpty()) {
            System.out.println("--- Lista de Mão-de-Obra ---");
            for (Orcamento orcamento : orcamentos) {
                System.out.println(orcamento);
            }
        } else {
            System.out.println("Nenhum orçamento cadastrado.");
        }
    }

    private void removerMaoDeObra() {
        System.out.print("ID do orçamento a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repositorioOrcamento.remover(id);
        System.out.println("Mão-de-obra removida com sucesso!");
    }
}
