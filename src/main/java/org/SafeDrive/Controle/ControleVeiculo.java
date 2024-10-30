package org.SafeDrive.Controle;

import org.SafeDrive.Modelo.Veiculo;
import org.SafeDrive.Repositorio.RepositorioVeiculo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ControleVeiculo {

    private static final Logger logger = LogManager.getLogger(ControleVeiculo.class);
    private RepositorioVeiculo repositorioVeiculo;

    public ControleVeiculo() {
        this.repositorioVeiculo = new RepositorioVeiculo();
    }

    public void adicionarVeiculo(String tipoVeiculo, String marca, String modelo, String placa,
                                 String anoFabricacao, int qtdEixo, boolean temSeguro,
                                 String numeroSeguro) {
        Veiculo veiculo = new Veiculo();
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setAnoFabricacao(java.time.LocalDate.parse(anoFabricacao)); // Converte String para LocalDate
        veiculo.setQtdEixo(qtdEixo);
        veiculo.setTemSeguro(temSeguro);
        veiculo.setNumeroSeguro(numeroSeguro);

        repositorioVeiculo.adicionar(veiculo);
    }

    public void atualizarVeiculo(int id, String tipoVeiculo, String marca, String modelo,
                                 String placa, String anoFabricacao, int qtdEixo,
                                 boolean temSeguro, String numeroSeguro) {
        Veiculo veiculo = repositorioVeiculo.buscarPorId(id);
        if (veiculo != null) {
            veiculo.setTipoVeiculo(tipoVeiculo);
            veiculo.setMarca(marca);
            veiculo.setModelo(modelo);
            veiculo.setPlaca(placa);
            veiculo.setAnoFabricacao(java.time.LocalDate.parse(anoFabricacao)); // Converte String para LocalDate
            veiculo.setQtdEixo(qtdEixo);
            veiculo.setTemSeguro(temSeguro);
            veiculo.setNumeroSeguro(numeroSeguro);
            repositorioVeiculo.atualizar(veiculo);
        } else {
            logger.error("Veículo com ID " + id + " não encontrado.");
        }
    }

    public void removerVeiculo(int id) {
        Veiculo veiculo = repositorioVeiculo.buscarPorId(id);
        if (veiculo != null) {
            repositorioVeiculo.remover(id);
        } else {
            logger.error("Veículo com ID " + id + " não encontrado.");
        }
    }

    public Veiculo buscarVeiculoPorId(int id) {
        return repositorioVeiculo.buscarPorId(id);
    }

    public List<Veiculo> listarVeiculos() {
        return repositorioVeiculo.listar();
    }
}
