package org.SafeDrive.Repositorio;

import java.util.List;

public interface RepositorioGenerico<T> {
    void adicionar(T entidade);
    void atualizar(T entidade);
    void remover(int id);
    List<T> listar();
    T buscarPorId(int id);
}
