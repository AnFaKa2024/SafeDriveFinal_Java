package org.SafeDrive.Operacional;

import org.SafeDrive.Modelo.Endereco;
import org.SafeDrive.Modelo.Usuario;
import org.SafeDrive.Modelo.Veiculo;
import org.SafeDrive.Repositorio.RepositorioEndereco;
import org.SafeDrive.Repositorio.RepositorioUsuario;
import org.SafeDrive.Repositorio.RepositorioVeiculo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {

    private static final Logger logger = LogManager.getLogger(MenuUsuario.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    private static final RepositorioEndereco repositorioEndereco = new RepositorioEndereco();
    private static final RepositorioVeiculo repositorioVeiculo = new RepositorioVeiculo();

    public static void MenuUsuarioPrincipal() {
        int opcao;
        do {
            System.out.println("\n===== MENU USUÁRIO =====");
            System.out.println("1. Atualizar Usuário");
            System.out.println("2. Remover Usuário");
            System.out.println("3. Adicionar Endereço");
            System.out.println("4. Atualizar Endereço");
            System.out.println("5. Listar Endereços");
            System.out.println("6. Remover Endereço");
            System.out.println("7. Adicionar Veículo");
            System.out.println("8. Atualizar Veículo");
            System.out.println("9. Listar Veículos");
            System.out.println("10. Remover Veículo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    atualizarUsuario();
                    break;
                case 2:
                    removerUsuario();
                    break;
                case 3:
                    adicionarEndereco();
                    break;
                case 4:
                    atualizarEndereco();
                    break;
                case 5:
                    listarEnderecos();
                    break;
                case 6:
                    removerEndereco();
                    break;
                case 7:
                    adicionarVeiculo();
                    break;
                case 8:
                    atualizarVeiculo();
                    break;
                case 9:
                    listarVeiculos();
                    break;
                case 10:
                    removerVeiculo();
                    break;
                case 0:
                    System.out.println("Saindo do menu...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void atualizarUsuario() {
        System.out.print("Digite o ID do usuário a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = repositorioUsuario.buscarPorId(id);
        if (usuario != null) {
            System.out.print("Novo CPF: ");
            usuario.setCpf(scanner.nextLine());
            System.out.print("Novo Telefone: ");
            usuario.setTelefone(scanner.nextLine());
            System.out.print("Novo Nome Completo: ");
            usuario.setNomeUsuario(scanner.nextLine());
            System.out.print("Nova Data de Nascimento (YYYY-MM-DD): ");
            usuario.setDataNascimento(Date.valueOf(LocalDate.parse(scanner.nextLine())).toLocalDate());
            System.out.print("Nova CNH: ");
            usuario.setCnh(scanner.nextLine());

            repositorioUsuario.atualizar(usuario);
            logger.info("Usuário atualizado com sucesso: ID " + id);
        } else {
            System.out.println("Usuário não encontrado.");
            logger.warn("Tentativa de atualizar usuário não encontrado: ID " + id);
        }
    }

    private static void removerUsuario() {
        System.out.print("Digite o ID do usuário a ser removido: ");
        int id = scanner.nextInt();
        repositorioUsuario.remover(id);
        logger.info("Usuário removido: ID " + id);
    }

    private static void adicionarEndereco() {
        Endereco endereco = new Endereco();
        System.out.print("Número: ");
        endereco.setNumero(scanner.nextLine());
        System.out.print("Complemento: ");
        endereco.setComplemento(scanner.nextLine());
        System.out.print("Bairro: ");
        endereco.setBairro(scanner.nextLine());
        System.out.print("Cidade: ");
        endereco.setCidade(scanner.nextLine());
        System.out.print("Estado: ");
        endereco.setEstado(scanner.nextLine());
        System.out.print("CEP: ");
        endereco.setCep(scanner.nextLine());

        repositorioEndereco.adicionar(endereco);
        logger.info("Endereço adicionado: " + endereco);
    }

    private static void atualizarEndereco() {
        System.out.print("Digite o ID do endereço a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Endereco endereco = repositorioEndereco.buscarPorId(id);
        if (endereco != null) {
            System.out.print("Novo Número: ");
            endereco.setNumero(scanner.nextLine());
            System.out.print("Novo Complemento: ");
            endereco.setComplemento(scanner.nextLine());
            System.out.print("Novo Bairro: ");
            endereco.setBairro(scanner.nextLine());
            System.out.print("Nova Cidade: ");
            endereco.setCidade(scanner.nextLine());
            System.out.print("Novo Estado: ");
            endereco.setEstado(scanner.nextLine());
            System.out.print("Novo CEP: ");
            endereco.setCep(scanner.nextLine());

            repositorioEndereco.atualizar(endereco);
            logger.info("Endereço atualizado: ID " + id);
        } else {
            System.out.println("Endereço não encontrado.");
            logger.warn("Tentativa de atualizar endereço não encontrado: ID " + id);
        }
    }

    private static void listarEnderecos() {
        System.out.println("Listando endereços:");
        List<Endereco> enderecos = repositorioEndereco.listar();
        for (Endereco endereco : enderecos) {
            System.out.println(endereco);
        }
        logger.info("Listagem de endereços realizada com sucesso.");
    }

    private static void removerEndereco() {
        System.out.print("Digite o ID do endereço a ser removido: ");
        int id = scanner.nextInt();
        repositorioEndereco.remover(id);
        logger.info("Endereço removido: ID " + id);
    }

    private static void adicionarVeiculo() {
        System.out.print("Digite o tipo do veículo: ");
        String tipoVeiculo = scanner.nextLine();
        System.out.print("Digite a marca do veículo: ");
        String marca = scanner.nextLine();
        System.out.print("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine();
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();
        System.out.print("Digite o ano de fabricação (AAAA-MM-DD): ");
        String anoFabricacao = scanner.nextLine();
        System.out.print("Digite a quantidade de eixos: ");
        int qtdEixo = scanner.nextInt();
        System.out.print("O veículo tem seguro? (true/false): ");
        boolean temSeguro = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Digite o número do seguro: ");
        String numeroSeguro = scanner.nextLine();

        Veiculo veiculo = new Veiculo(0, false, tipoVeiculo, marca, modelo, placa, LocalDate.parse(anoFabricacao), qtdEixo, temSeguro, numeroSeguro);
        repositorioVeiculo.adicionar(veiculo);
        logger.info("Veículo adicionado: " + veiculo);
    }

    private static void atualizarVeiculo() {
        System.out.print("Digite o ID do veículo a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Veiculo veiculoExistente = repositorioVeiculo.buscarPorId(id);
        if (veiculoExistente == null) {
            System.out.println("Veículo não encontrado!");
            logger.warn("Tentativa de atualizar veículo não encontrado: ID " + id);
            return;
        }

        System.out.print("Digite o novo tipo do veículo (atual: " + veiculoExistente.getTipoVeiculo() + "): ");
        String tipoVeiculo = scanner.nextLine();
        System.out.print("Digite a nova marca do veículo (atual: " + veiculoExistente.getMarca() + "): ");
        String marca = scanner.nextLine();
        System.out.print("Digite o novo modelo do veículo (atual: " + veiculoExistente.getModelo() + "): ");
        String modelo = scanner.nextLine();
        System.out.print("Digite a nova placa do veículo (atual: " + veiculoExistente.getPlaca() + "): ");
        String placa = scanner.nextLine();
        System.out.print("Digite o novo ano de fabricação (AAAA-MM-DD): ");
        String anoFabricacao = scanner.nextLine();
        System.out.print("Digite a nova quantidade de eixos: ");
        int qtdEixo = scanner.nextInt();
        System.out.print("O veículo tem seguro? (true/false): ");
        boolean temSeguro = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Digite o novo número do seguro: ");
        String numeroSeguro = scanner.nextLine();

        veiculoExistente.setTipoVeiculo(tipoVeiculo);
        veiculoExistente.setMarca(marca);
        veiculoExistente.setModelo(modelo);
        veiculoExistente.setPlaca(placa);
        veiculoExistente.setAnoFabricacao(LocalDate.parse(anoFabricacao));
        veiculoExistente.setQtdEixo(qtdEixo);
        veiculoExistente.setTemSeguro(temSeguro);
        veiculoExistente.setNumeroSeguro(numeroSeguro);

        repositorioVeiculo.atualizar(veiculoExistente);
        logger.info("Veículo atualizado: ID " + id);
    }

    private static void listarVeiculos() {
        System.out.println("Listando veículos:");
        List<Veiculo> veiculos = repositorioVeiculo.listar();
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }
        logger.info("Listagem de veículos realizada com sucesso.");
    }

    private static void removerVeiculo() {
        System.out.print("Digite o ID do veículo a ser removido: ");
        int id = scanner.nextInt();
        repositorioVeiculo.remover(id);
        logger.info("Veículo removido: ID " + id);
    }
}
