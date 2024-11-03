package org.SafeDrive.Modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "T_FGK_ORCAMENTO")
public class Orcamento extends EntidadeBase {

    @Min(value = 0, message = "O valor da mão de obra não pode ser negativo.")
    @Column(name = "mao_de_obra", nullable = false)
    private double maoDeObra;

    @Column(name = "guincho", nullable = false)
    private double guincho = 0;

    @Transient
    private double total;

    @OneToOne
    @JoinColumn(name = "id_oficina", referencedColumnName = "id")
    private Oficina oficina;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Orcamento() {}

    public Orcamento(double maoDeObra, double guincho, Oficina oficina, String tipo) {
        this.maoDeObra = maoDeObra;
        this.guincho = guincho;
        this.oficina = oficina;
        this.tipo = tipo;
        this.total = calcularTotal();
    }

    public Orcamento(int id, boolean deletado, double maoDeObra, double guincho, String tipo) {
    }

    // Enum para representar os tipos de orçamento
    public enum TipoOrcamento {
        BASICO, COMPLETO, PREMIUM
    }

    // Getters e Setters
    public double getGuincho() {
        return guincho;
    }

    public void setGuincho(double guincho) {
        this.guincho = guincho;
        this.total = calcularTotal();
    }

    public double getMaoDeObra() {
        return maoDeObra;
    }

    public void setMaoDeObra(double maoDeObra) {
        this.maoDeObra = maoDeObra;
        this.total = calcularTotal();
    }

    public double getTotal() {
        return total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Método para calcular o total
    private double calcularTotal() {
        return maoDeObra + guincho;
    }

    @Override
    public String toString() {
        return "Orcamento{" +
                "maoDeObra=" + maoDeObra +
                ", guincho=" + guincho +
                ", total=" + total +
                ", oficina=" + oficina +
                ", tipo=" + tipo +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orcamento)) return false;
        Orcamento orcamento = (Orcamento) o;
        return Double.compare(orcamento.maoDeObra, maoDeObra) == 0 &&
                Double.compare(orcamento.guincho, guincho) == 0 &&
                Objects.equals(oficina, orcamento.oficina) &&
                tipo == orcamento.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maoDeObra, guincho, oficina, tipo);
    }
}
