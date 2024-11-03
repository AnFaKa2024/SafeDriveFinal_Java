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
    private RepositorioLogin repositorioLogin = new RepositorioLogin();

    @Override
    public int adicionar(Usuario usuario) {
        repositorioLogin.adicionar(usuario.getLogin());
        String sql = "INSERT INTO T_FGK_USUARIO (cpf, telefone, nm_usuario, dt_nascimento, cnh, id_login) VALUES (?, ?, ?, ?, ?, ?)";
        int usuarioId = -1;

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getNomeUsuario());
            stmt.setDate(4, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(5, usuario.getCnh());
            stmt.setInt(6, usuario.getLogin().getId()); // ID do login que foi gerado anteriormente

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuarioId = generatedKeys.getInt(1);
                        logger.info("Usuário cadastrado com sucesso com ID: " + usuarioId);
                    }
                }
            } else {
                logger.error("Falha ao adicionar usuário, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            logger.error("Erro ao adicionar usuário: " + e.getMessage(), e);
        }
        return usuarioId;
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE T_FGK_USUARIO SET id_login = ?, cpf = ?, telefone = ?, nm_usuario = ?, dt_nascimento = ?, cnh = ? WHERE id_usuario = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getLogin().getId());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getNomeUsuario());
            stmt.setDate(5, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(6, usuario.getCnh());
            stmt.setInt(7, usuario.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Usuário atualizado com sucesso: " + usuario.getNomeUsuario());
            } else {
                logger.warn("Nenhum usuário encontrado para atualizar com ID: " + usuario.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM T_FGK_USUARIO WHERE id_usuario = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Usuário removido com ID: " + id);
            } else {
                logger.warn("Nenhum usuário encontrado para remover com ID: " + id);
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM T_FGK_USUARIO WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            id,
                            false,
                            buscarLoginPorId(rs.getInt("id_login")),
                            rs.getString("cpf"),
                            rs.getString("telefone"),
                            buscarEnderecosPorUsuarioId(id),
                            rs.getString("nm_usuario"),
                            rs.getDate("dt_nascimento").toLocalDate(),
                            rs.getString("cnh"),
                            buscarVeiculosPorUsuarioId(id)
                    );
                } else {
                    logger.warn("Nenhum usuário encontrado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_USUARIO";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        false,
                        buscarLoginPorId(rs.getInt("id_login")),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        buscarEnderecosPorUsuarioId(rs.getInt("id_usuario")),
                        rs.getString("nm_usuario"),
                        rs.getDate("dt_nascimento").toLocalDate(),
                        rs.getString("cnh"),
                        buscarVeiculosPorUsuarioId(rs.getInt("id_usuario"))
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar usuários: " + e.getMessage(), e);
        }
        return usuarios;
    }

    public Usuario buscarPorLogin(String email, String senha) {
        String sql = "SELECT u.*, l.email FROM T_FGK_USUARIO u INNER JOIN T_FGK_LOGIN l ON u.id_login = l.id_login WHERE l.email = ? AND l.senha = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setEmail(rs.getString("email"));
                    login.setSenha(senha);

                    return new Usuario(
                            rs.getString("cpf"),
                            rs.getString("cnh"),
                            rs.getString("nm_usuario"),
                            rs.getString("genero"), // Certifique-se de que a coluna "genero" existe na tabela
                            rs.getString("telefone"),
                            rs.getDate("dt_nascimento").toLocalDate(), // Convertendo para LocalDate
                            login
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário por login: " + e.getMessage(), e);
        }
        return null;
    }

    private Login buscarLoginPorId(int loginId) {
        String sql = "SELECT * FROM T_FGK_LOGIN WHERE id_login = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, loginId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setId(rs.getInt("id_login"));
                    login.setEmail(rs.getString("email"));
                    login.setSenha(rs.getString("senha"));
                    return login;
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar login por ID: " + e.getMessage(), e);
        }
        return null;
    }

    private List<Endereco> buscarEnderecosPorUsuarioId(int usuarioId) {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_ENDERECO WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(rs.getInt("id_endereco"));
                    endereco.setNumero(rs.getString("numero"));
                    endereco.setComplemento(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setEstado(rs.getString("estado"));
                    endereco.setCep(rs.getString("cep"));
                    enderecos.add(endereco);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar endereços por ID do usuário: " + e.getMessage(), e);
        }
        return enderecos;
    }

    private List<Veiculo> buscarVeiculosPorUsuarioId(int usuarioId) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM T_FGK_VEICULO WHERE id_usuario = ?";
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar veículos por ID do usuário: " + e.getMessage(), e);
        }
        return veiculos;
    }
}
