package org.SafeDrive.Modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "T_SafeDrive_LOGIN")
public class Login extends EntidadeBase {

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @Column(name = "senha", nullable = false)
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;
    @Id
    private Long id;

    public Login() {}

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Login(int id, boolean deletado, String email, String senha) {
        super(id, deletado);
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isSenhaValida() {
        return senha != null && senha.length() >= 6;
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", senha='********'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Login)) return false;
        Login login = (Login) o;
        return email.equals(login.email) && senha.equals(login.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, senha);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getId() {
        return Math.toIntExact(id);
    }
}
