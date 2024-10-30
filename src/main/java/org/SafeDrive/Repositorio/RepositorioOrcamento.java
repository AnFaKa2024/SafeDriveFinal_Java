package org.SafeDrive.Repositorio;

import org.SafeDrive.Modelo.Orcamento;
import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioOrcamento implements RepositorioGenerico<Orcamento> {

    private static final Logger logger = LogManager.getLogger(RepositorioOrcamento.class);

    @Override
    public void adicionar(Orcamento orcamento) {
        String sql = "INSERT INTO T_ORCAMENTO (mao_de_obra, pecas, tipo_orcamento) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, orcamento.getMaoDeObra());
            stmt.setDouble(2, orcamento.getPecas());
            stmt.setString(3, orcamento.getTipo());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orcamento.setId(generatedKeys.getInt(1));
                    logger.info("Orçamento cadastrado com sucesso com ID: " + orcamento.getId());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar orçamento: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Orcamento orcamento) {
        String sql = "UPDATE T_ORCAMENTO SET mao_de_obra = ?, pecas = ?, tipo_orcamento = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, orcamento.getMaoDeObra());
            stmt.setDouble(2, orcamento.getPecas());
            stmt.setString(3, orcamento.getTipo());
            stmt.setInt(4, orcamento.getId());

            stmt.executeUpdate();
            logger.info("Orçamento atualizado: ID " + orcamento.getId());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar orçamento: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_ORCAMENTO WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Orçamento removido com ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao remover orçamento: " + e.getMessage());
        }
    }

    @Override
    public Orcamento buscarPorId(int id) {
        String sql = "SELECT * FROM T_ORCAMENTO WHERE id = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Orcamento(
                        id,
                        rs.getBoolean("deletado"),
                        rs.getDouble("mao_de_obra"),
                        rs.getDouble("pecas"),
                        null,
                        rs.getString("tipo_orcamento")
                );
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar orçamento por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Orcamento> listar() {
        List<Orcamento> orcamentos = new ArrayList<>();
        String sql = "SELECT * FROM T_ORCAMENTO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Orcamento orcamento = new Orcamento(
                        rs.getInt("id"),
                        rs.getBoolean("deletado"),
                        rs.getDouble("mao_de_obra"),
                        rs.getDouble("pecas"),
                        null,
                        rs.getString("tipo_orcamento")
                );
                orcamentos.add(orcamento);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar orçamentos: " + e.getMessage());
        }
        return orcamentos;
    }
}
