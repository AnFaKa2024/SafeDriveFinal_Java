package org.SafeDrive.Modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario extends EntidadeBase {

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 15, message = "O CPF deve ter entre 11 e 15 caracteres.")
    private String cpf;

    @NotBlank(message = "A CNH é obrigatória.")
    @Size(min = 11, max = 15, message = "A CNH deve ter entre 11 e 15 caracteres.")
    private String cnh;

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(max = 90, message = "O nome deve ter no máximo 90 caracteres.")
    private String nomeUsuario;

    @Pattern(regexp = "feminino|masculino|indefinido", message = "O gênero deve ser feminino, masculino ou indefinido.")
    private String genero;

    @ElementCollection
    @CollectionTable(name = "T_SafeDrive_ENDERECO", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "endereco", nullable = false)
    @NotNull(message = "A lista de endereços não pode ser nula.")
    @OneToMany(mappedBy = "usuario")
    private List<Endereco> enderecos = new ArrayList<>();

    private String telefone;

    @Past(message = "A data de nascimento deve estar no passado.")
    private LocalDate dataNascimento;

    private Login login;

    @Id
    private int id;

    public Usuario() {
    }

    public Usuario(int id, boolean deletado, String cpf, String cnh, String nomeUsuario, String genero,
                   List<Endereco> enderecos, String telefone, LocalDate dataNascimento, Login login) {
        super(id, deletado);
        this.cpf = cpf;
        this.cnh = cnh;
        this.nomeUsuario = nomeUsuario;
        this.genero = genero;
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.login = login;
    }

    public Usuario(String cpf, String cnh, String nomeUsuario, String genero,
                   List<Endereco> enderecos, String telefone, LocalDate dataNascimento, Login login) {
        this.cpf = cpf;
        this.cnh = cnh;
        this.nomeUsuario = nomeUsuario;
        this.genero = genero;
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.login = login;
    }

    public Usuario(int id, boolean deletado, Login loginId, String cpf, String telefone, List<Endereco> enderecos,
                   String nomeUsuario, LocalDate dataNascimento, String cnh, List<Veiculo> veiculos) {
        this.id = id;
        this.deletado = deletado;
        this.login = loginId;
        this.cpf = cpf;
        this.cnh = cnh;
        this.nomeUsuario = nomeUsuario;
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(String cpf, String cnh, String nmUsuario, String genero, String telefone, Date dtNascimento, Login login) {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        if (enderecos != null && !enderecos.isEmpty()) {
            this.enderecos = enderecos;
        } else {
            throw new IllegalArgumentException("A lista de endereços não pode ser nula ou vazia.");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cpf='" + cpf + '\'' +
                ", cnh='" + cnh + '\'' +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", genero='" + genero + '\'' +
                ", enderecos=" + enderecos +
                ", telefone='" + telefone + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", login=" + login +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return cpf.equals(usuario.cpf) && cnh.equals(usuario.cnh) && Objects.equals(login, usuario.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, cnh, login);
    }
}
