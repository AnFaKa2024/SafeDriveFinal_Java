package org.SafeDrive.Controle;

import org.SafeDrive.Modelo.Login;
import org.SafeDrive.Repositorio.RepositorioLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ControleLogin {

    private RepositorioLogin repositorioLogin;
    private static final Logger logger = LogManager.getLogger(ControleLogin.class);

    public ControleLogin() {
        this.repositorioLogin = new RepositorioLogin();
    }

    public void adicionarLogin(Login login) {
        if (login != null) {
            repositorioLogin.adicionar(login);
            logger.info("Login adicionado: " + login.getEmail());
        } else {
            logger.error("Tentativa de adicionar login nulo.");
            throw new IllegalArgumentException("Login não pode ser nulo.");
        }
    }

    public Login buscarLoginPorId(int id) {
        Login login = repositorioLogin.buscarPorId(id);
        if (login != null) {
            logger.info("Login encontrado: " + login.getEmail());
        } else {
            logger.warn("Login com ID " + id + " não encontrado.");
        }
        return login;
    }

    public List<Login> listarLogins() {
        List<Login> logins = repositorioLogin.listar();
        logger.info("Listando todos os logins: " + logins.size() + " logins encontrados.");
        return logins;
    }

    public void removerLogin(int id) {
        Login login = repositorioLogin.buscarPorId(id);
        if (login != null) {
            repositorioLogin.remover(login.getId());
            logger.info("Login removido: " + login.getEmail());
        } else {
            logger.warn("Tentativa de remover login inexistente com ID " + id);
            throw new IllegalArgumentException("Login não encontrado para remoção.");
        }
    }

    public void atualizarLogin(Login login) {
        if (login != null) {
            repositorioLogin.atualizar(login);
            logger.info("Login atualizado: " + login.getEmail());
        } else {
            logger.error("Tentativa de atualizar login nulo.");
            throw new IllegalArgumentException("Login não pode ser nulo.");
        }
    }

    public boolean redefinirSenha(String email, String novaSenha) {
        if (email == null || email.isEmpty()) {
            logger.error("Tentativa de redefinir senha com e-mail nulo ou vazio.");
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio.");
        }
        if (novaSenha == null || novaSenha.isEmpty()) {
            logger.error("Tentativa de redefinir senha com nova senha nula ou vazia.");
            throw new IllegalArgumentException("Nova senha não pode ser nula ou vazia.");
        }

        boolean sucesso = repositorioLogin.redefinirSenha(email, novaSenha);
        if (sucesso) {
            logger.info("Senha redefinida com sucesso para o e-mail: " + email);
        } else {
            logger.warn("Nenhum login encontrado para o e-mail: " + email);
        }
        return sucesso;
    }
}
