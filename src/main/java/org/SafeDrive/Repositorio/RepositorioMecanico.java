package org.SafeDrive.Repositorio;

import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.SafeDrive.Modelo.Mecanico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMecanico implements RepositorioGenerico<Mecanico>{

    private static final Logger logger = LogManager.getLogger(RepositorioEndereco.class);

    @Override
    public void adicionar(Mecanico mecanico) {
        String sql = "INSERT INTO T_AFK_MECANICO (nome, endereco) VALUES (?,?)";

        try(Connection conexao = ConexaoBanco.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, mecanico.getNome());
            stmt.setString(2, mecanico.getCpf());

            stmt.executeUpdate();

            try(ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    mecanico.getId(generatedKeys.getInt(1));
                    logger.info("Nome do mecânico cadastrado com sucesso com ID" + mecanico.getId());
                }
            }

        }catch (SQLException e){
            logger.error("Erro ao adicionar Nome do Mecânico" + e.getMessage(), e);
        }

    }

    @Override
    public void atualizar(Mecanico entidade) {
       // NO CASO DE NOME E CPF, NÃO TEM O QUE ATUALIZAR
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_AFK_MECANICO WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setInt(1,id);
            stmt.executeUpdate();
            logger.info("Mecânico Removido pelo ID" + id);

        }catch (SQLException e){
            logger.error("Erro ao remover o mecânico pelo ID" + id + ":" + e.getMessage(), e);
        }

    }

    @Override
    public List<Mecanico> listar() {
        List<Mecanico> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_AFK_MECANICO";

        try(Connection conexao = ConexaoBanco.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                Mecanico mecanico = new Mecanico(
                    rs.getInt("id_mecanico"),
                    rs.getString("nome"),
                    rs.getString("cpf")
                );
                mecanico.add(mecanico);
            }
        }catch (SQLException e){
            logger.error( "Erro ao listar Mecânicos: " + e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public Mecanico buscarPorId(int id) {
        String sql = "SELECT * FROM T_AFK_MECANICO WHERE id_mecanico = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setInt(1.id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return new Mecanico(
                        rs.getInt("id_mecanico"),
                );
            }

        }catch (SQLException e){
            logger.error("Erro ao buscar mecânico pelo ID" + id + ":" + e.getMessage(), e);
        }
        return null;
    }
}
