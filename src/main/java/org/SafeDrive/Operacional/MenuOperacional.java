package org.SafeDrive.Operacional;

import org.SafeDrive.Modelo.Mecanico;
import org.SafeDrive.Modelo.Oficina;
import org.SafeDrive.Modelo.Usuario;
import org.SafeDrive.Modelo.Login;
import org.SafeDrive.Repositorio.RepositorioLogin;
import org.SafeDrive.Repositorio.RepositorioMecanico;
import org.SafeDrive.Repositorio.RepositorioOficina;
import org.SafeDrive.Repositorio.RepositorioUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class MenuOperacional {

    private String tipoAcesso;
    private Scanner scanner;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioOficina repositorioOficina;
    private RepositorioMecanico repositorioMecanico;

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

    private void criarUsuario() {
        System.out.println("\n--- Criar Login de Usuário ---");

        System.out.print("Nome completo: ");
        String nomeCompleto = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("CNH: ");
        String cnh = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Login login = new Login();
        login.setEmail(email);
        login.setSenha(senha);

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(nomeCompleto);
        usuario.setCpf(cpf);
        usuario.setCnh(cnh);
        usuario.setLogin(login);

        repositorioUsuario.adicionar(usuario);
        System.out.println("Usuário criado com sucesso!");
        logger.info("Usuário criado: " + nomeCompleto + " (CPF: " + cpf + ")");
    }

    private void loginUsuario() {
        System.out.println("\n--- Login de Usuário ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = repositorioUsuario.buscarPorLogin(email, senha);
        if (usuario != null && usuario.getLogin().getSenha().equals(senha)) {
            System.out.println("Login de usuário efetuado com sucesso!");
            logger.info("Usuário logado com sucesso: " + usuario.getNomeUsuario());
        } else {
            System.out.println("Login ou senha inválidos.");
            logger.warn("Tentativa de login falhou para: " + email);
        }
    }

    private void criarOficina() {
        System.out.println("\n--- Criar Login de Oficina ---");

        System.out.print("Nome da Oficina: ");
        String nomeOficina = scanner.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

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

        Oficina oficina = repositorioOficina.buscarPorLogin(email, senha);

        if (oficina == null || oficina.getLogin() == null || !oficina.getLogin().getSenha().equals(senha)) {
            System.out.println("Login ou senha inválidos.");
            logger.warn("Tentativa de login falhou para oficina: " + email);
        } else {
            System.out.println("Login de oficina efetuado com sucesso!");
            logger.info("Oficina logada com sucesso: " + oficina.getNomeOficina());
        }
    }

    private void redefinirSenhaUsuario() {
        System.out.println("\n--- Redefinir Senha de Usuário ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Nova Senha: ");
        String novaSenha = scanner.nextLine();

        boolean sucesso = RepositorioLogin.redefinirSenha(email, novaSenha);
        if (sucesso) {
            System.out.println("Senha redefinida com sucesso!");
        } else {
            System.out.println("Nenhum usuário encontrado com este email.");
        }
    }

    private void redefinirSenhaOficina() {
        System.out.println("\n--- Redefinir Senha de Oficina ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Nova Senha: ");
        String novaSenha = scanner.nextLine();

        boolean sucesso = RepositorioLogin.redefinirSenha(email, novaSenha);
        if (sucesso) {
            System.out.println("Senha redefinida com sucesso!");
        } else {
            System.out.println("Nenhuma oficina encontrada com este email.");
        }
    }

    private void cadastrarMecanico(){
        System.out.println("\n--- Cadastrar Mecanico ---");

        System.out.println("Nome Completo: ");
        String nome = scanner.nextLine();

        System.out.println("CPF: ");
        String cpf = scanner.nextLine();

        repositorioMecanico.adicionar(new Mecanico(nome, cpf));
        System.out.println("Mecanico cadastrado com sucesso!");
        logger.info("Mecanico cadastrado! " + nome + " (CPF: " + cpf + ")");

    }
}
