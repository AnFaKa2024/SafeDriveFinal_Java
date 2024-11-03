package org.SafeDrive.Repositorio;

import org.SafeDrive.Modelo.Login;
import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioLogin implements RepositorioGenerico<Login> {

    private static final Logger logger = LogManager.getLogger(RepositorioLogin.class);

    @Override
    public int adicionar(Login login) {
        String sql = "INSERT INTO T_FGK_LOGIN (email, senha) VALUES (?, ?)";
        int loginId = -1;

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (login.getEmail() == null || login.getSenha() == null) {
                logger.error("Email ou senha não podem ser nulos.");
                return loginId;
            }
            stmt.setString(1, login.getEmail());
            stmt.setString(2, login.getSenha());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("Falha ao adicionar login, nenhuma linha afetada.");
                return loginId;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loginId = generatedKeys.getInt(1);
                    login.setId(loginId); // Define o ID gerado no objeto login
                    logger.info("Login cadastrado com sucesso com ID: " + loginId);
                } else {
                    logger.error("Falha ao adicionar login, nenhum ID gerado.");
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar login: " + e.getMessage(), e);
        }
        return loginId;
    }

    @Override
    public void atualizar(Login login) {
        String sql = "UPDATE T_FGK_LOGIN SET email = ?, senha = ? WHERE id_login = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, login.getEmail());
            stmt.setString(2, login.getSenha());
            stmt.setInt(3, login.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Login atualizado com sucesso para o email: " + login.getEmail());
            } else {
                logger.warn("Nenhum login encontrado para atualizar com ID: " + login.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar login: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_FGK_LOGIN WHERE id_login = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Login removido com sucesso com ID: " + id);
            } else {
                logger.warn("Nenhum login encontrado para remover com ID: " + id);
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover login com ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Login buscarPorId(int id) {
        String sql = "SELECT * FROM T_FGK_LOGIN WHERE id_login = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Login(
                            rs.getInt("id_login"),
                            false,
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                } else {
                    logger.warn("Nenhum login encontrado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar login por ID " + id + ": " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Login> listar() {
        List<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_LOGIN";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Login login = new Login(
                        rs.getInt("id_login"),
                        false,
                        rs.getString("email"),
                        rs.getString("senha")
                );
                logins.add(login);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar logins: " + e.getMessage(), e);
        }
        return logins;
    }

    public static boolean redefinirSenha(String email, String novaSenha) {
        String sql = "UPDATE T_FGK_LOGIN SET senha = ? WHERE email = ?";
        boolean senhaRedefinida = false;

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novaSenha);
            stmt.setString(2, email);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                senhaRedefinida = true;
                logger.info("Senha redefinida com sucesso para o e-mail: " + email);
            } else {
                logger.warn("Nenhum login encontrado para o e-mail: " + email);
            }
        } catch (SQLException e) {
            logger.error("Erro ao redefinir senha para o e-mail " + email + ": " + e.getMessage(), e);
        }
        return senhaRedefinida;
    }
}
