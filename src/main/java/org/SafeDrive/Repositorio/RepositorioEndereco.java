package org.SafeDrive.Repositorio;

import org.SafeDrive.Modelo.Endereco;
import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEndereco implements RepositorioGenerico<Endereco> {

    private static final Logger logger = LogManager.getLogger(RepositorioEndereco.class);

    @Override
    public int adicionar(Endereco endereco) {
        String sql = "INSERT INTO T_FGK_ENDERECO (logradouro, numero, complemento, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = 0;

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getNumero());
            stmt.setString(2, endereco.getComplemento());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        endereco.setId(generatedId);
                        logger.info("Endereço cadastrado com sucesso com ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar endereço: " + e.getMessage(), e);
        }
        return generatedId;
    }

    @Override
    public void atualizar(Endereco endereco) {
        String sql = "UPDATE T_FGK_ENDERECO SET logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getNumero());
            stmt.setString(2, endereco.getComplemento());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());
            stmt.setInt(7, endereco.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Endereço atualizado com sucesso: " + endereco.getCep());
            } else {
                logger.warn("Nenhum endereço encontrado para atualizar com ID: " + endereco.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar endereço com ID " + endereco.getId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_FGK_ENDERECO WHERE id_endereco = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Endereço removido com sucesso com ID: " + id);
            } else {
                logger.warn("Nenhum endereço encontrado para remover com ID: " + id);
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover endereço com ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Endereco buscarPorId(int id) {
        String sql = "SELECT * FROM T_FGK_ENDERECO WHERE id_endereco = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Endereco(
                            rs.getInt("id_endereco"),
                            false,
                            rs.getString("numero"),
                            rs.getString("complemento"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep")
                    );
                } else {
                    logger.warn("Nenhum endereço encontrado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar endereço por ID " + id + ": " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Endereco> listar() {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_ENDERECO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Endereco endereco = new Endereco(
                        rs.getInt("id_endereco"),
                        false,
                        rs.getString("numero"),
                        rs.getString("complemento"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep")
                );
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar endereços: " + e.getMessage(), e);
        }
        return enderecos;
    }
}
