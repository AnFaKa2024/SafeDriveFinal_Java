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
    public int adicionar(Orcamento orcamento) {
        String sql = "INSERT INTO T_FGK_ORCAMENTO (mao_de_obra, guincho, tipo_orcamento) VALUES (?, ?, ?)";
        int orcamentoId = -1;

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, orcamento.getMaoDeObra());
            stmt.setDouble(2, orcamento.getGuincho());
            stmt.setString(3, orcamento.getTipo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orcamentoId = generatedKeys.getInt(1);
                        orcamento.setId(orcamentoId);
                        logger.info("Orçamento cadastrado com sucesso com ID: " + orcamentoId);
                    }
                }
            } else {
                logger.error("Falha ao adicionar orçamento, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar orçamento: " + e.getMessage(), e);
        }
        return orcamentoId;
    }

    @Override
    public void atualizar(Orcamento orcamento) {
        String sql = "UPDATE T_FGK_ORCAMENTO SET mao_de_obra = ?, guincho = ?, tipo = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, orcamento.getMaoDeObra());
            stmt.setDouble(2, orcamento.getGuincho());
            stmt.setString(3, orcamento.getTipo());
            stmt.setInt(4, orcamento.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Orçamento atualizado com sucesso: ID " + orcamento.getId());
            } else {
                logger.warn("Nenhum orçamento encontrado para atualizar com ID: " + orcamento.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar orçamento: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_FGK_ORCAMENTO WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Orçamento removido com sucesso com ID: " + id);
            } else {
                logger.warn("Nenhum orçamento encontrado para remover com ID: " + id);
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover orçamento com ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Orcamento buscarPorId(int id) {
        String sql = "SELECT * FROM T_FGK_ORCAMENTO WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    double maoDeObra = rs.getDouble("mao_de_obra");
                    double guincho = rs.getDouble("guincho");
                    String tipo = rs.getString("tipo_orcamento");
                    boolean deletado = rs.getBoolean("deletado");

                    return new Orcamento(id, deletado, maoDeObra, guincho, tipo);
                } else {
                    logger.warn("Nenhum orçamento encontrado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar orçamento por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Orcamento> listar() {
        List<Orcamento> orcamentos = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_ORCAMENTO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                double maoDeObra = rs.getDouble("mao_de_obra");
                double guincho = rs.getDouble("guincho");
                String tipo = rs.getString("tipo");
                boolean deletado = rs.getBoolean("deletado");

                Orcamento orcamento = new Orcamento(id, deletado, maoDeObra, guincho, tipo);
                orcamentos.add(orcamento);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar orçamentos: " + e.getMessage(), e);
        }
        return orcamentos;
    }
}
