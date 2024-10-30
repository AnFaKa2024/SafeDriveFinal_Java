package org.SafeDrive.Modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "T_SafeDrive_OFICINA")
public class Oficina extends EntidadeBase {

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Login login;

    @Column(name = "cnpj", nullable = false, length = 14, unique = true)
    @NotBlank(message = "O CNPJ é obrigatório.")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 caracteres.")
    private String cnpj;

    @Column(name = "telefone", nullable = false)
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @CollectionTable(name = "T_SafeDrive_ENDERECO", joinColumns = @JoinColumn(name = "id_oficina"))
    @Column(name = "endereco", nullable = false)
    @NotNull(message = "A lista de endereços não pode ser nula.")
    @OneToMany(mappedBy = "oficina")
    private List<Endereco> enderecos = new ArrayList<>();


    @Column(name = "nome_oficina", nullable = false)
    @NotBlank(message = "O nome da oficina é obrigatório.")
    private String nomeOficina;

    @Column(name = "especialidade", nullable = false)
    @NotBlank(message = "A especialidade é obrigatória.")
    private String especialidade;

    @Column(name = "orcamento")
    private String orcamento;

    @Column(name = "nome_proprietario", nullable = false)
    @NotBlank(message = "O nome do proprietário é obrigatório.")
    private String nomeProprietario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    private int id;

    public Oficina() {}

    public Oficina(Login login, String cnpj, String telefone, List<Endereco> enderecos, // Alterado para List<Endereco>
                   String nomeOficina, String especialidade, String orcamento, String nomeProprietario) {
        this.login = login;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        this.nomeOficina = nomeOficina;
        this.especialidade = especialidade;
        this.orcamento = orcamento;
        this.nomeProprietario = nomeProprietario;
    }

    public Oficina(String nomeOficina, String cnpj, String especialidade, String telefone, Login login) {
    }

    // Getters e Setters
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if (cnpj.matches("\\d{14}")) {
            this.cnpj = cnpj;
        } else {
            throw new IllegalArgumentException("CNPJ inválido. Deve conter 14 dígitos.");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Endereco> getEnderecos() { // Alterado para List<Endereco>
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) { // Alterado para List<Endereco>
        if (enderecos != null && !enderecos.isEmpty()) {
            this.enderecos = enderecos;
        } else {
            throw new IllegalArgumentException("A lista de endereços não pode ser nula ou vazia.");
        }
    }

    public String getNomeOficina() {
        return nomeOficina;
    }

    public void setNomeOficina(String nomeOficina) {
        this.nomeOficina = nomeOficina;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(String orcamento) {
        this.orcamento = orcamento;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    @Override
    public String toString() {
        return "Oficina{" +
                "login=" + login +
                ", cnpj='" + cnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                ", enderecos=" + enderecos +
                ", nomeOficina='" + nomeOficina + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", orcamento='" + orcamento + '\'' +
                ", nomeProprietario='" + nomeProprietario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oficina)) return false;
        Oficina oficina = (Oficina) o;
        return Objects.equals(login, oficina.login) &&
                Objects.equals(cnpj, oficina.cnpj) &&
                Objects.equals(telefone, oficina.telefone) &&
                Objects.equals(enderecos, oficina.enderecos) &&
                Objects.equals(nomeOficina, oficina.nomeOficina) &&
                Objects.equals(especialidade, oficina.especialidade) &&
                Objects.equals(orcamento, oficina.orcamento) &&
                Objects.equals(nomeProprietario, oficina.nomeProprietario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, cnpj, telefone, enderecos, nomeOficina, especialidade, orcamento, nomeProprietario);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return Math.toIntExact(id);
    }
}
