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
    public void adicionar(Oficina oficina) {
        String sql = "INSERT INTO T_AFK_OFICINA (cnpj, telefone, id_login, nm_oficina, especialidade, nome_proprietario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, oficina.getCnpj());
            stmt.setString(2, oficina.getTelefone());
            //stmt.setInt(3, oficina.getLogin().getId());
            stmt.setString(4, oficina.getNomeOficina());
            stmt.setString(5, oficina.getEspecialidade());
            stmt.setString(6, oficina.getNomeProprietario());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    oficina.setId(generatedKeys.getInt(1));
                    logger.info("Oficina cadastrada com sucesso com ID: " + oficina.getId());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar oficina: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Oficina oficina) {
        String sql = "UPDATE T_AFK_OFICINA SET cnpj = ?, telefone = ?, nm_oficina = ?, especialidade = ?, nome_proprietario = ? WHERE id_oficina = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, oficina.getCnpj());
            stmt.setString(2, oficina.getTelefone());
            stmt.setString(3, oficina.getNomeOficina());
            stmt.setString(4, oficina.getEspecialidade());
            stmt.setString(5, oficina.getNomeProprietario()); // Atualizando nome do proprietário
            stmt.setInt(6, oficina.getId());

            stmt.executeUpdate();
            logger.info("Oficina atualizada: " + oficina.getNomeOficina());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar oficina: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_AFK_OFICINA WHERE id_oficina = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Oficina removida com ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao remover oficina: " + e.getMessage());
        }
    }

    @Override
    public Oficina buscarPorId(int id) {
        String sql = "SELECT * FROM T_AFK_OFICINA WHERE id_oficina = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Oficina(
                        (Login) null,
                        rs.getString("cnpj"),
                        rs.getString("telefone"),
                        null,
                        rs.getString("nm_oficina"),
                        rs.getString("especialidade"),
                        null,
                        rs.getString("nome_proprietario")
                );
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar oficina por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Oficina> listar() {
        List<Oficina> oficinas = new ArrayList<>();
        String sql = "SELECT * FROM T_AFK_OFICINA";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Oficina oficina = new Oficina(
                        (Login) null,
                        rs.getString("cnpj"),
                        rs.getString("telefone"),
                        null,
                        rs.getString("nm_oficina"),
                        rs.getString("especialidade"),
                        null,
                        rs.getString("nome_proprietario")
                );
                oficinas.add(oficina);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar oficinas: {}", e.getMessage());
        }
        return oficinas;
    }

    public Oficina buscarPorLogin(String email, String senha) {
        String sql = "SELECT o.*, l.senha FROM T_AFK_OFICINA o " +
                "INNER JOIN T_AFK_LOGIN l ON o.id_login = l.id_login " +
                "WHERE l.email = ? AND l.senha = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Login login = new Login();
                login.setId(rs.getInt("id_login"));
                login.setEmail(email);
                login.setSenha(rs.getString("senha"));

                Oficina oficina = new Oficina(
                        (Login) null,
                        rs.getString("cnpj"),
                        rs.getString("telefone"),
                        null,
                        rs.getString("nm_oficina"),
                        rs.getString("especialidade"),
                        null,
                        rs.getString("nome_proprietario")
                );
                oficina.setLogin(login);

                return oficina;
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar oficina por login: {}", e.getMessage());
        }
        return null;
    }

}
