package org.SafeDrive.Modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "T_FGK_ENDERECO")
public class Endereco extends EntidadeBase {

    @NotBlank(message = "O número é obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotBlank(message = "O estado é obrigatório.")
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "O CEP deve estar no formato 12345-678.")
    @Column(name = "cep", nullable = false)
    private String cep;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private int id;

    public Endereco() {}

    public Endereco(int id, boolean deletado, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        super(id, deletado);
        this.id = id;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Endereco(String numero, String complemento, String estado, String bairro, String cidade, String cep) {
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Endereco: %s, %s, %s, %s - %s, %s, %s",
                numero, complemento != null ? complemento : "",
                bairro, cidade, estado, cep);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco endereco = (Endereco) o;
        return  Objects.equals(numero, endereco.numero) &&
                Objects.equals(complemento, endereco.complemento) &&
                Objects.equals(bairro, endereco.bairro) &&
                Objects.equals(cidade, endereco.cidade) &&
                Objects.equals(estado, endereco.estado) &&
                Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, complemento, bairro, cidade, estado, cep);
    }
}
