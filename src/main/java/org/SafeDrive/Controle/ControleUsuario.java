package org.SafeDrive.Controle;

import org.SafeDrive.Modelo.Usuario;
import org.SafeDrive.Repositorio.RepositorioUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ControleUsuario {

    private RepositorioUsuario repositorioUsuario;
    private static final Logger logger = LogManager.getLogger(ControleUsuario.class);

    public ControleUsuario() {
        this.repositorioUsuario = new RepositorioUsuario();
    }

    public void adicionarUsuario(Usuario usuario) {
        if (usuario != null) {
            repositorioUsuario.adicionar(usuario);
            logger.info("Usuário adicionado: " + usuario.getNomeUsuario());
        } else {
            logger.error("Tentativa de adicionar usuário nulo.");
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = repositorioUsuario.buscarPorId(id);
        if (usuario != null) {
            logger.info("Usuário encontrado: " + usuario.getNomeUsuario());
        } else {
            logger.warn("Usuário com ID " + id + " não encontrado.");
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorLogin(String email, String senha) {
        Usuario usuario = repositorioUsuario.buscarPorLogin(email, senha);
        if (usuario != null) {
            logger.info("Usuário encontrado pelo login: " + usuario.getNomeUsuario());
        } else {
            logger.warn("Login inválido para email: " + email);
        }
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.listar();
        logger.info("Listando todos os usuários: " + usuarios.size() + " usuários encontrados.");
        return usuarios;
    }

    public void removerUsuario(int id) {
        Usuario usuario = repositorioUsuario.buscarPorId(id);
        if (usuario != null) {
            repositorioUsuario.remover(usuario.getId());
            logger.info("Usuário removido: " + usuario.getNomeUsuario());
        } else {
            logger.warn("Tentativa de remover usuário inexistente com ID " + id);
            throw new IllegalArgumentException("Usuário não encontrado para remoção.");
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        if (usuario != null) {
            repositorioUsuario.atualizar(usuario);
            logger.info("Usuário atualizado: " + usuario.getNomeUsuario());
        } else {
            logger.error("Tentativa de atualizar usuário nulo.");
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
    }
}
