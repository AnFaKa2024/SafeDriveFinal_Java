package org.SafeDrive.Controle;

import org.SafeDrive.Modelo.Endereco;
import org.SafeDrive.Repositorio.RepositorioEndereco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ControleEndereco {

    private RepositorioEndereco repositorioEndereco;
    private static final Logger logger = LogManager.getLogger(ControleEndereco.class);

    public ControleEndereco() {
        this.repositorioEndereco = new RepositorioEndereco();
    }

    public void adicionarEndereco(Endereco endereco) {
        if (endereco != null) {
            repositorioEndereco.adicionar(endereco);
            logger.info("Endereço adicionado: " + endereco.getLogradouro());
        } else {
            logger.error("Tentativa de adicionar endereço nulo.");
            throw new IllegalArgumentException("Endereço não pode ser nulo.");
        }
    }

    public Endereco buscarEnderecoPorId(int id) {
        Endereco endereco = repositorioEndereco.buscarPorId(id);
        if (endereco != null) {
            logger.info("Endereço encontrado: " + endereco.getLogradouro());
        } else {
            logger.warn("Endereço com ID " + id + " não encontrado.");
        }
        return endereco;
    }

    public List<Endereco> listarEnderecos() {
        List<Endereco> enderecos = repositorioEndereco.listar();
        logger.info("Listando todos os endereços: " + enderecos.size() + " endereços encontrados.");
        return enderecos;
    }

    public void removerEndereco(int id) {
        Endereco endereco = repositorioEndereco.buscarPorId(id);
        if (endereco != null) {
            repositorioEndereco.remover(endereco.getId());
            logger.info("Endereço removido: " + endereco.getLogradouro());
        } else {
            logger.warn("Tentativa de remover endereço inexistente com ID " + id);
            throw new IllegalArgumentException("Endereço não encontrado para remoção.");
        }
    }

    public void atualizarEndereco(Endereco endereco) {
        if (endereco != null) {
            repositorioEndereco.atualizar(endereco);
            logger.info("Endereço atualizado: " + endereco.getLogradouro());
        } else {
            logger.error("Tentativa de atualizar endereço nulo.");
            throw new IllegalArgumentException("Endereço não pode ser nulo.");
        }
    }
}
