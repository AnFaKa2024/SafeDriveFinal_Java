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

    public void adicionarOrcamento(double maoDeObra, double pecas) {
        Orcamento orcamento = new Orcamento();
        orcamento.setMaoDeObra(maoDeObra);
        orcamento.setPecas(pecas);

        repositorioOrcamento.adicionar(orcamento);
    }

    public void atualizarOrcamento(int id, double maoDeObra, double pecas) {
        Orcamento orcamento = repositorioOrcamento.buscarPorId(id);
        if (orcamento != null) {
            orcamento.setMaoDeObra(maoDeObra);
            orcamento.setPecas(pecas);
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
        return repositorioOrcamento.buscarPorId(id);
    }

    public List<Orcamento> listarOrcamentos() {
        return repositorioOrcamento.listar();
    }
}
