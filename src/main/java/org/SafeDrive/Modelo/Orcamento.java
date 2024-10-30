package org.SafeDrive.Modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "T_SafeDrive_ORCAMENTO")
public class Orcamento extends EntidadeBase {

    @Min(value = 0, message = "O valor da mão de obra não pode ser negativo.")
    @Column(name = "mao_de_obra", nullable = false)
    private double maoDeObra;

    @Min(value = 0, message = "O valor das peças não pode ser negativo.")
    @Column(name = "pecas")
    private Double pecas;

    @Transient
    private double total;

    @OneToOne
    @JoinColumn(name = "id_oficina", referencedColumnName = "id_oficina")
    private Oficina oficina;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_orcamento", nullable = false)
    private String tipo;

    @Id
    private int id;

    public Orcamento() {
    }

    public Orcamento(int id, boolean deletado, double maoDeObra, Double pecas, double total, Oficina oficina,
                     String tipo, int id1) {
        super(id, deletado);
        this.maoDeObra = maoDeObra;
        this.pecas = pecas;
        this.total = total;
        this.oficina = oficina;
        this.tipo = tipo;
        this.id = id1;
    }

    public Orcamento(double maoDeObra, Double pecas, double total, Oficina oficina, String tipo, int id) {
        this.maoDeObra = maoDeObra;
        this.pecas = pecas;
        this.total = total;
        this.oficina = oficina;
        this.tipo = tipo;
        this.id = id;
    }

    public Orcamento(int id, boolean deletado, double maoDeObra, Double pecas, Oficina oficina) {
        super(id, deletado);
        this.maoDeObra = maoDeObra;
        this.pecas = pecas;
        this.oficina = oficina;
        this.total = calcularTotal();
    }

    public Orcamento(int id, boolean deletado, double maoDeObra, double pecas, Object tipo, String tipoOrcamento) {
    }

    public double getMaoDeObra() {
        return maoDeObra;
    }

    public void setMaoDeObra(double maoDeObra) {
        this.maoDeObra = maoDeObra;
        this.total = calcularTotal();
    }

    public Double getPecas() {
        return pecas;
    }

    public void setPecas(Double pecas) {
        this.pecas = pecas;
        this.total = calcularTotal();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    // Método para calcular o total localmente, sem depender do banco de dados
    private double calcularTotal() {
        return maoDeObra + (pecas != null ? pecas : 0);
    }

    @Override
    public String toString() {
        return "Orcamento{" +
                "id=" + getId() +
                ", maoDeObra=" + maoDeObra +
                ", pecas=" + pecas +
                ", total=" + total +
                ", oficina=" + (oficina != null ? oficina.getNomeOficina() : "N/A") +
                ", tipo=" + tipo +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orcamento)) return false;
        Orcamento orcamento = (Orcamento) o;
        return Double.compare(orcamento.maoDeObra, maoDeObra) == 0 &&
                Objects.equals(pecas, orcamento.pecas) &&
                Objects.equals(oficina, orcamento.oficina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maoDeObra, pecas, oficina);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return Math.toIntExact(id);
    }
}
