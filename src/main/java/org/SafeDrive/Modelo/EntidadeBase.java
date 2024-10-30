package org.SafeDrive.Modelo;


public abstract class EntidadeBase {
    protected int id;
    protected boolean deletado = false;

    public EntidadeBase() {
    }

    public EntidadeBase(int id, boolean deletado) {
        this.id = id;
        this.deletado = deletado;
    }

    public boolean isDeletado() {
        return deletado;
    }

    public void setDeletado(boolean deletado) {
        this.deletado = deletado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
