package org.SafeDrive.Repositorio;

import org.SafeDrive.Modelo.Veiculo;
import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVeiculo implements RepositorioGenerico<Veiculo> {

    private static final Logger logger = LogManager.getLogger(RepositorioVeiculo.class);

    @Override
    public int adicionar(Veiculo veiculo) {
        String sql = "INSERT INTO T_FGK_VEICULO (tipo_veiculo, marca, modelo, placa, ano_fabricacao, qtd_eixo, tem_seguro, numero_seguro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veiculo.getTipoVeiculo());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setString(4, veiculo.getPlaca());
            stmt.setDate(5, Date.valueOf(veiculo.getAnoFabricacao()));
            stmt.setInt(6, veiculo.getQtdEixo());
            stmt.setBoolean(7, veiculo.isTemSeguro());
            stmt.setString(8, veiculo.getNumeroSeguro());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    veiculo.setId(generatedKeys.getInt(1));
                    logger.info("Veículo cadastrado com sucesso com ID: " + veiculo.getId());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar veículo: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE T_FGK_VEICULO SET tipo_veiculo = ?, marca = ?, modelo = ?, placa = ?, ano_fabricacao = ?, qtd_eixo = ?, tem_seguro = ?, numero_seguro = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getTipoVeiculo());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setString(4, veiculo.getPlaca());
            stmt.setDate(5, Date.valueOf(veiculo.getAnoFabricacao()));
            stmt.setInt(6, veiculo.getQtdEixo());
            stmt.setBoolean(7, veiculo.isTemSeguro());
            stmt.setString(8, veiculo.getNumeroSeguro());
            stmt.setInt(9, veiculo.getId());

            stmt.executeUpdate();
            logger.info("Veículo atualizado: ID " + veiculo.getId());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_FGK_VEICULO WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Veículo removido com ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao remover veículo: " + e.getMessage());
        }
    }

    @Override
    public Veiculo buscarPorId(int id) {
        String sql = "SELECT * FROM T_FGK_VEICULO WHERE id = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Veiculo(
                        id,
                        false,
                        rs.getString("tipo_veiculo"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getDate("ano_fabricacao").toLocalDate(),
                        rs.getInt("qtd_eixo"),
                        rs.getBoolean("tem_seguro"),
                        rs.getString("numero_seguro")
                );
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar veículo por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Veiculo> listar() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_VEICULO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getInt("id"),
                        false,
                        rs.getString("tipo_veiculo"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getDate("ano_fabricacao").toLocalDate(),
                        rs.getInt("qtd_eixo"),
                        rs.getBoolean("tem_seguro"),
                        rs.getString("numero_seguro")
                );
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar veículos: " + e.getMessage());
        }
        return veiculos;
    }
}
