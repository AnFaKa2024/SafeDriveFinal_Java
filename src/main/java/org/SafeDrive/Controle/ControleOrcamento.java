package org.SafeDrive.Controle;

import org.SafeDrive.Modelo.Orcamento;
import org.SafeDrive.Repositorio.RepositorioOrcamento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ControleOrcamento {

    private static final Logger logger = LogManager.getLogger(ControleOrcamento.class);
    private RepositorioOrcamento repositorioOrcamento;

    public ControleOrcamento() {
        this.repositorioOrcamento = new RepositorioOrcamento();
    }

    public void adicionarOrcamento(int id, double maoDeObra, double guincho, String tipo) {
        Orcamento orcamento = new Orcamento(id, false, maoDeObra, 0.0, tipo);
        orcamento.setMaoDeObra(maoDeObra);
        orcamento.setGuincho(guincho);
        orcamento.setTipo(tipo);

        repositorioOrcamento.adicionar(orcamento);
    }

    public void atualizarOrcamento(int id, double maoDeObra, double guincho, double pecas) {
        Orcamento orcamento = repositorioOrcamento.buscarPorId(id);
        if (orcamento != null) {
            orcamento.setMaoDeObra(maoDeObra);
            orcamento.setGuincho(guincho);
            repositorioOrcamento.atualizar(orcamento);
        } else {
            logger.error("Orçamento com ID " + id + " não encontrado.");
        }
    }

    public void removerOrcamento(int id) {
        Orcamento orcamento = repositorioOrcamento.buscarPorId(id);
        if (orcamento != null) {
            repositorioOrcamento.remover(id);
        } else {
            logger.error("Orçamento com ID " + id + " não encontrado.");
        }
    }

    public Orcamento buscarOrcamentoPorId(int id) {
        Orcamento orcamento = repositorioOrcamento.buscarPorId(id);
        if (orcamento == null) {
            logger.error("Orçamento com ID " + id + " não encontrado.");
        }
        return orcamento;
    }

    public List<Orcamento> listarOrcamentos() {
        return repositorioOrcamento.listar();
    }
}
