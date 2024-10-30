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
    public void adicionar(Endereco endereco) {
        String sql = "INSERT INTO T_AFK_ENDERECO (logradouro, numero, complemento, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    endereco.setId(generatedKeys.getInt(1));
                    logger.info("Endereço cadastrado com sucesso com ID: " + endereco.getId());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar endereço: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Endereco endereco) {
        String sql = "UPDATE T_AFK_ENDERECO SET logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());
            stmt.setInt(8, endereco.getId());

            stmt.executeUpdate();
            logger.info("Endereço atualizado: " + endereco.getLogradouro());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar endereço com ID " + endereco.getId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_AFK_ENDERECO WHERE id_endereco = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Endereço removido com ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao remover endereço com ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Endereco buscarPorId(int id) {
        String sql = "SELECT * FROM T_AFK_ENDERECO WHERE id_endereco = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Endereco(
                        rs.getInt("id_endereco"),
                        false,
                        rs.getString("logradouro"),
                        rs.getString("numero"),
                        rs.getString("complemento"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep")
                );
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar endereço por ID " + id + ": " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Endereco> listar() {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM T_AFK_ENDERECO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Endereco endereco = new Endereco(
                        rs.getInt("id_endereco"),
                        false,
                        rs.getString("logradouro"),
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
