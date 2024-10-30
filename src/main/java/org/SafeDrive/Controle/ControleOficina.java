package org.SafeDrive.Controle;

import org.SafeDrive.Modelo.Oficina;
import org.SafeDrive.Repositorio.RepositorioOficina;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ControleOficina {

    private RepositorioOficina repositorioOficina;
    private static final Logger logger = LogManager.getLogger(ControleOficina.class);

    public ControleOficina() {
        this.repositorioOficina = new RepositorioOficina();
    }

    public void adicionarOficina(Oficina oficina) {
        if (oficina != null) {
            repositorioOficina.adicionar(oficina);
            logger.info("Oficina adicionada: " + oficina.getNomeOficina());
        } else {
            logger.error("Tentativa de adicionar oficina nula.");
            throw new IllegalArgumentException("Oficina não pode ser nula.");
        }
    }

    public Oficina buscarOficinaPorId(int id) {
        Oficina oficina = repositorioOficina.buscarPorId(id);
        if (oficina != null) {
            logger.info("Oficina encontrada: " + oficina.getNomeOficina());
        } else {
            logger.warn("Oficina com ID " + id + " não encontrada.");
        }
        return oficina;
    }

    public List<Oficina> listarOficinas() {
        List<Oficina> oficinas = repositorioOficina.listar();
        logger.info("Listando todas as oficinas: " + oficinas.size() + " oficinas encontradas.");
        return oficinas;
    }

    public void removerOficina(int id) {
        Oficina oficina = repositorioOficina.buscarPorId(id);
        if (oficina != null) {
            repositorioOficina.remover(oficina.getId());
            logger.info("Oficina removida: " + oficina.getNomeOficina());
        } else {
            logger.warn("Tentativa de remover oficina inexistente com ID " + id);
            throw new IllegalArgumentException("Oficina não encontrada para remoção.");
        }
    }

    public void atualizarOficina(Oficina oficina) {
        if (oficina != null) {
            repositorioOficina.atualizar(oficina);
            logger.info("Oficina atualizada: " + oficina.getNomeOficina());
        } else {
            logger.error("Tentativa de atualizar oficina nula.");
            throw new IllegalArgumentException("Oficina não pode ser nula.");
        }
    }
}
