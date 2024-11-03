package org.SafeDrive.Operacional;

import org.SafeDrive.Modelo.Endereco;
import org.SafeDrive.Modelo.Oficina;
import org.SafeDrive.Modelo.Usuario;
import org.SafeDrive.Modelo.Login;
import org.SafeDrive.Repositorio.RepositorioEndereco;
import org.SafeDrive.Repositorio.RepositorioLogin;
import org.SafeDrive.Repositorio.RepositorioOficina;
import org.SafeDrive.Repositorio.RepositorioUsuario;
import org.SafeDrive.Servico.ValidadorEntidades;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuOperacional {

    private String tipoAcesso;
    private static Scanner scanner;
    private static RepositorioLogin repositorioLogin = new RepositorioLogin();
    private static RepositorioEndereco repositorioEndereco = new RepositorioEndereco();
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    private RepositorioOficina repositorioOficina = new RepositorioOficina();

    private static final Logger logger = LogManager.getLogger(MenuOperacional.class);

    public MenuOperacional(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
        this.scanner = new Scanner(System.in);
        this.repositorioUsuario = new RepositorioUsuario();
        this.repositorioOficina = new RepositorioOficina();
    }

    public void exibirMenuOperacional() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Menu de " + tipoAcesso + " ---");
            System.out.println("1. Criar Login de " + tipoAcesso);
            System.out.println("2. Fazer Login de " + tipoAcesso);
            System.out.println("3. Redefinir Senha");
            System.out.println("0. Voltar");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    if (tipoAcesso.equals("Usuário")) {
                        criarUsuario();
                    } else {
                        criarOficina();
                    }
                    break;
                case 2:
                    if (tipoAcesso.equals("Usuário")) {
                        loginUsuario();
                    } else {
                        loginOficina();
                    }
                    break;
                case 3:
                    if (tipoAcesso.equals("Usuário")) {
                        redefinirSenhaUsuario();
                    } else {
                        redefinirSenhaOficina();
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    logger.warn("Opção inválida no menu: " + option);
            }
        }
    }

    private static void criarUsuario() {
        try {
            System.out.print("Digite o nome completo do usuário: ");
            String nomeUsuario = scanner.nextLine();

            System.out.print("Digite o CPF: ");
            String cpf = scanner.nextLine();
            ValidadorEntidades.validarCpf(cpf);

            System.out.print("Digite a CNH: ");
            String cnh = scanner.nextLine();
            ValidadorEntidades.validarCnh(cnh);

            System.out.print("Digite seu gênero: ");
            String genero = scanner.nextLine();
            ValidadorEntidades.validarGenero(genero);

            System.out.print("Digite seu telefone: ");
            String telefone = scanner.nextLine();
            ValidadorEntidades.validarTelefone(telefone);

            System.out.print("Digite sua data de nascimento (yyyy-MM-dd): ");
            LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
            ValidadorEntidades.validarDataNascimento(dataNascimento);

            System.out.println("Digite o Estado: ");
            String estado = scanner.nextLine();
            ValidadorEntidades.validarEstado(estado);

            System.out.println("Digite a cidade: ");
            String cidade = scanner.nextLine();

            System.out.println("Digite o bairro: ");
            String bairro = scanner.nextLine();

            System.out.println("Digite o complemento: ");
            String complemento = scanner.nextLine();

            System.out.println("Digite o número: ");
            String numero = scanner.nextLine();
            ValidadorEntidades.validarNumero(numero);

            System.out.println("Digite o CEP: ");
            String cep = scanner.nextLine();
            ValidadorEntidades.validarCep(cep);

            System.out.print("Digite o email: ");
            String email = scanner.nextLine();
            ValidadorEntidades.validarEmail(email);

            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();
            ValidadorEntidades.validarSenha(senha);

            Login login = new Login(email, senha);
            int loginId = repositorioLogin.adicionar(login);

            Endereco endereco = new Endereco(numero, complemento, estado, bairro, cidade, cep);
            int enderecoId = repositorioEndereco.adicionar(endereco);

            Usuario usuario = new Usuario(nomeUsuario, cpf, cnh, genero, telefone, dataNascimento, loginId, enderecoId);
            repositorioUsuario.adicionar(usuario);

            System.out.println("Usuário criado com sucesso!");
            logger.info("Usuário cadastrado: " + nomeUsuario + " (CPF: " + cpf + ")");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: data de nascimento inválida. Por favor, use o formato yyyy-MM-dd.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }


    private void loginUsuario() {
        System.out.println("\n--- Login de Usuário ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            ValidadorEntidades.validarEmail(email);
            Usuario usuario = repositorioUsuario.buscarPorLogin(email, senha);
            if (usuario != null && usuario.getLogin().getSenha().equals(senha)) {
                System.out.println("Login de usuário efetuado com sucesso!");
                logger.info("Usuário logado com sucesso: " + usuario.getNomeUsuario());
            } else {
                System.out.println("Login ou senha inválidos.");
                logger.warn("Tentativa de login falhou para: " + email);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void criarOficina() {
        System.out.println("\n--- Criar Login de Oficina ---");

        System.out.print("Nome da Oficina: ");
        String nomeOficina = scanner.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        ValidadorEntidades.validarCnpj(cnpj);

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        ValidadorEntidades.validarTelefone(telefone);

        System.out.print("Email: ");
        String email = scanner.nextLine();
        ValidadorEntidades.validarEmail(email);

        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        ValidadorEntidades.validarSenha(senha);

        Login login = new Login();
        login.setEmail(email);
        login.setSenha(senha);

        repositorioOficina.adicionar(new Oficina(nomeOficina, cnpj, especialidade, telefone, login));
        System.out.println("Oficina criada com sucesso!");
        logger.info("Oficina criada: " + nomeOficina + " (CNPJ: " + cnpj + ")");
    }

    private void loginOficina() {
        System.out.println("\n--- Login de Oficina ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            ValidadorEntidades.validarEmail(email);
            Oficina oficina = repositorioOficina.buscarPorLogin(email, senha);

            if (oficina == null || oficina.getLogin() == null || !oficina.getLogin().getSenha().equals(senha)) {
                System.out.println("Login ou senha inválidos.");
                logger.warn("Tentativa de login falhou para oficina: " + email);
            } else {
                System.out.println("Login de oficina efetuado com sucesso!");
                logger.info("Oficina logada com sucesso: " + oficina.getNomeOficina());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void redefinirSenhaUsuario() {
        System.out.println("\n--- Redefinir Senha de Usuário ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Nova Senha: ");
        String novaSenha = scanner.nextLine();

        try {
            ValidadorEntidades.validarEmail(email);
            ValidadorEntidades.validarSenha(novaSenha);

            boolean sucesso = repositorioLogin.redefinirSenha(email, novaSenha);
            if (sucesso) {
                System.out.println("Senha redefinida com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com este email.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void redefinirSenhaOficina() {
        System.out.println("\n--- Redefinir Senha de Oficina ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Nova Senha: ");
        String novaSenha = scanner.nextLine();

        try {
            ValidadorEntidades.validarEmail(email);
            ValidadorEntidades.validarSenha(novaSenha);

            boolean sucesso = repositorioLogin.redefinirSenha(email, novaSenha);
            if (sucesso) {
                System.out.println("Senha redefinida com sucesso!");
            } else {
                System.out.println("Nenhuma oficina encontrada com este email.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
