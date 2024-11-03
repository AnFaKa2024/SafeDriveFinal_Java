package org.SafeDrive.Modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "T_FGK_USUARIO")
public class Usuario extends EntidadeBase {

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 15, message = "O CPF deve ter entre 11 e 15 caracteres.")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "A CNH é obrigatória.")
    @Size(min = 11, max = 15, message = "A CNH deve ter entre 11 e 15 caracteres.")
    @Column(name = "cnh", nullable = false, unique = true)
    private String cnh;

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(max = 90, message = "O nome deve ter no máximo 90 caracteres.")
    @Column(name = "nome_usuario", nullable = false)
    private String nomeUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private String genero;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @Column(name = "telefone")
    private String telefone;

    @Past(message = "A data de nascimento deve estar no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "login_id", nullable = false)
    private Login login;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Usuario(int id, boolean b, Login idLogin, String cpf, String telefone, List<Endereco> enderecos, String
            nmUsuario, LocalDate dtNascimento, String cnh, List<Veiculo> veiculos) {
        this.id = id;
        this.cpf = cpf;
        this.cnh = cnh;
        this.dataNascimento = dtNascimento;
        this.login = idLogin;
        this.enderecos = enderecos;
        this.telefone = telefone;
        this.enderecos.addAll(this.enderecos);
    }

    public Usuario(String cpf, String cnh, String nmUsuario, String genero, String telefone, LocalDate dtNascimento, Login login) {
    }

    public Usuario(String nomeUsuario, String cpf, String cnh, String genero, String telefone, LocalDate dataNascimento, int loginId, int enderecoId) {
    }

    public enum Genero {
        FEMININO, MASCULINO, INDEFINIDO
    }

    public Usuario() {}

    public Usuario(String cpf, String cnh, String nomeUsuario, String genero, List<Endereco> enderecos, String telefone, LocalDate dataNascimento, Login login) {
        this.cpf = cpf;
        this.cnh = cnh;
        this.nomeUsuario = nomeUsuario;
        this.genero = genero;
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.login = login;
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
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
