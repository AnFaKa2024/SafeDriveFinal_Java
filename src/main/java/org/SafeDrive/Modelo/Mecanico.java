package org.SafeDrive.Modelo;

public class Mecanico extends EntidadeBase{
    String nome;
    String cpf;

    public Mecanico() {
    }

    public Mecanico(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Mecanico(int id, boolean deletado, String cpf, String nome) {
        super(id, deletado);
        this.cpf = cpf;
        this.nome = nome;
    }

    public Mecanico(int id, boolean deletado, String nome) {
        super(id, deletado);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Mecanico{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", id=" + id +
                ", deletado=" + deletado +
                '}';
    }
}

