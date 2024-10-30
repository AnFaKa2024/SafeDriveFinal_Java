package org.SafeDrive.Repositorio;

import org.SafeDrive.Modelo.*;
import org.SafeDrive.InfraEstrutura.ConexaoBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario implements RepositorioGenerico<Usuario> {

    private static final Logger logger = LogManager.getLogger(RepositorioUsuario.class);

    @Override
    public void adicionar(Usuario usuario) {
        String sql = "INSERT INTO T_USUARIO (login_id, cpf, telefone, nomeCompleto, dataNascimento, cnh) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, usuario.getLogin().getId());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getNomeUsuario());
            stmt.setDate(5, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(6, usuario.getCnh());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                    logger.info("Usuário cadastrado com sucesso com ID: " + usuario.getId());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE T_USUARIO SET login_id = ?, cpf = ?, telefone = ?, nomeCompleto = ?, dataNascimento = ?, cnh = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getLogin().getId());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getNomeUsuario());
            stmt.setDate(5, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(6, usuario.getCnh());
            stmt.setInt(7, usuario.getId());

            stmt.executeUpdate();
            logger.info("Usuário atualizado: " + usuario.getNomeUsuario());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_USUARIO WHERE id = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Usuário removido com ID: " + id);
        } catch (SQLException e) {
            logger.error("Erro ao remover usuário: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM T_USUARIO WHERE id = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        id,
                        false,
                        buscarLoginPorId(rs.getInt("login_id")),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        buscarEnderecosPorUsuarioId(id),
                        rs.getString("nomeCompleto"),
                        rs.getDate("dataNascimento").toLocalDate(),
                        rs.getString("cnh"),
                        buscarVeiculosPorUsuarioId(id)
                );
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM T_USUARIO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        false,
                        buscarLoginPorId(rs.getInt("login_id")),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        buscarEnderecosPorUsuarioId(rs.getInt("id")),
                        rs.getString("nomeCompleto"),
                        rs.getDate("dataNascimento").toLocalDate(),
                        rs.getString("cnh"),
                        buscarVeiculosPorUsuarioId(rs.getInt("id"))
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public Usuario buscarPorLogin(String email, String senha) {
        String sql = "SELECT u.*, l.email FROM T_SafeDrive_USUARIO u INNER JOIN T_AFK_LOGIN l ON u.id_login = l.id_login WHERE l.email = ? AND l.senha = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Login login = new Login();
                login.setEmail(rs.getString("email"));
                login.setSenha(senha);

                return new Usuario(
                        rs.getString("cpf"),
                        rs.getString("cnh"),
                        rs.getString("nm_usuario"),
                        rs.getString("genero"),
                        rs.getString("telefone"),
                        rs.getDate("dt_nascimento"),
                        login
                );
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário por login: " + e.getMessage());
        }
        return null;
    }


    private Login buscarLoginPorId(int loginId) {
        String sql = "SELECT * FROM T_AFK_LOGIN WHERE id_login = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, loginId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Login login = new Login();
                login.setId(rs.getInt("id_login"));
                login.setEmail(rs.getString("email"));
                login.setSenha(rs.getString("senha"));
                return login;
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar login por ID: " + e.getMessage());
        }
        return null;
    }


    private List<Endereco> buscarEnderecosPorUsuarioId(int usuarioId) {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM T_AFK_ENDERECO WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("id_endereco"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setEstado(rs.getString("estado"));
                endereco.setCep(rs.getString("cep"));
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar endereços por ID do usuário: " + e.getMessage());
        }
        return enderecos;
    }


    private List<Veiculo> buscarVeiculosPorUsuarioId(int usuarioId) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM T_AFK_VEICULO WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rs.getInt("id_veiculo"));
                veiculo.setTipoVeiculo(rs.getString("tipo_veiculo"));
                veiculo.setMarca(rs.getString("marca"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setAnoFabricacao(rs.getDate("ano_fabricacao").toLocalDate());
                veiculo.setQtdEixo(rs.getInt("qtd_eixo"));
                veiculo.setTemSeguro(rs.getBoolean("tem_seguro"));
                veiculo.setNumeroSeguro(rs.getString("numero_seguro"));
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar veículos por ID do usuário: " + e.getMessage());
        }
        return veiculos;
    }

}
