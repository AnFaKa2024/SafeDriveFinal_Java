package org.SafeDrive.Repositorio;

import org.SafeDrive.Modelo.Login;
import org.SafeDrive.Modelo.Oficina;
import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioOficina implements RepositorioGenerico<Oficina> {

    private static final Logger logger = LogManager.getLogger(RepositorioOficina.class);

    @Override
    public int adicionar(Oficina oficina) {
        String sql = "INSERT INTO T_FGK_OFICINA (cnpj, telefone, id_login, nm_oficina, especialidade, nome_proprietario) VALUES (?, ?, ?, ?, ?, ?)";
        int oficinaId = -1;

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, oficina.getCnpj());
            stmt.setString(2, oficina.getTelefone());
            stmt.setInt(3, oficina.getLogin().getId()); // Configuração do id_login
            stmt.setString(4, oficina.getNomeOficina());
            stmt.setString(5, oficina.getEspecialidade());
            stmt.setString(6, oficina.getNomeProprietario());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        oficinaId = generatedKeys.getInt(1);
                        oficina.setId(oficinaId);
                        logger.info("Oficina cadastrada com sucesso com ID: " + oficinaId);
                    }
                }
            } else {
                logger.error("Falha ao adicionar oficina, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar oficina: " + e.getMessage(), e);
        }
        return oficinaId;
    }

    @Override
    public void atualizar(Oficina oficina) {
        String sql = "UPDATE T_FGK_OFICINA SET cnpj = ?, telefone = ?, nm_oficina = ?, especialidade = ?, nome_proprietario = ? WHERE id_oficina = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, oficina.getCnpj());
            stmt.setString(2, oficina.getTelefone());
            stmt.setString(3, oficina.getNomeOficina());
            stmt.setString(4, oficina.getEspecialidade());
            stmt.setString(5, oficina.getNomeProprietario());
            stmt.setInt(6, oficina.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Oficina atualizada com sucesso: " + oficina.getNomeOficina());
            } else {
                logger.warn("Nenhuma oficina encontrada para atualizar com ID: " + oficina.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar oficina: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_FGK_OFICINA WHERE id_oficina = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Oficina removida com sucesso com ID: " + id);
            } else {
                logger.warn("Nenhuma oficina encontrada para remover com ID: " + id);
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover oficina com ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Oficina buscarPorId(int id) {
        String sql = "SELECT * FROM T_FGK_OFICINA WHERE id_oficina = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Login login = new Login();
                    login.setId(rs.getInt("id_login"));

                    return new Oficina(
                            login,
                            rs.getString("cnpj"),
                            rs.getString("telefone"),
                            rs.getInt("id_oficina"),
                            rs.getString("nm_oficina"),
                            rs.getString("especialidade"),
                            rs.getInt("id_login"),
                            rs.getString("nome_proprietario")
                    );
                } else {
                    logger.warn("Nenhuma oficina encontrada com ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar oficina por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Oficina> listar() {
        List<Oficina> oficinas = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_OFICINA";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Login login = new Login();
                login.setId(rs.getInt("id_login"));

                Oficina oficina = new Oficina(
                        login,
                        rs.getString("cnpj"),
                        rs.getString("telefone"),
                        rs.getInt("id_oficina"),
                        rs.getString("nm_oficina"),
                        rs.getString("especialidade"),
                        rs.getInt("id_login"),
                        rs.getString("nome_proprietario")
                );
                oficinas.add(oficina);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar oficinas: " + e.getMessage(), e);
        }
        return oficinas;
    }

    public Oficina buscarPorLogin(String email, String senha) {
        String sql = "SELECT o.*, l.senha, l.email FROM T_FGK_OFICINA o " +
                "INNER JOIN T_FGK_LOGIN l ON o.id_login = l.id_login " +
                "WHERE l.email = ? AND l.senha = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setId(rs.getInt("id_login"));
                    login.setEmail(rs.getString("email"));
                    login.setSenha(rs.getString("senha"));

                    Oficina oficina = new Oficina(
                            login,
                            rs.getString("cnpj"),
                            rs.getString("telefone"),
                            rs.getInt("id_oficina"),
                            rs.getString("nm_oficina"),
                            rs.getString("especialidade"),
                            rs.getInt("id_login"),
                            rs.getString("nome_proprietario")
                    );

                    return oficina;
                } else {
                    logger.warn("Nenhuma oficina encontrada para o login fornecido.");
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar oficina por login: " + e.getMessage(), e);
        }
        return null;
    }
}
