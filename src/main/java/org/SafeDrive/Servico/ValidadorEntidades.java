package org.SafeDrive.Servico;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidadorEntidades {

    private static final Scanner scanner = new Scanner(System.in);

    private static String obterEntradaComConfirmacao(String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = scanner.nextLine().trim();
            System.out.println("Você digitou: " + entrada);
            System.out.print("Está correto? (ok para confirmar, qualquer outra tecla para corrigir): ");
            String confirmacao = scanner.nextLine();
            if (confirmacao.equalsIgnoreCase("ok")) {
                break;
            } else {
                System.out.println("Por favor, digite novamente.");
            }
        }
        return entrada;
    }

    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome inválido. O nome não pode ser vazio.");
            return false;
        }
        return true;
    }

    public static boolean validarCpf(String cpf) {
        String cpfNumerico = cpf.replaceAll("[^\\d]", "");
        if (cpfNumerico.length() != 11 || !Pattern.matches("\\d{11}", cpfNumerico)) {
            System.out.println("CPF inválido. Deve conter 11 dígitos numéricos.");
            return false;
        }
        // Adicionar lógica de validação de CPF se necessário
        return true;
    }

    public static boolean validarCnpj(String cnpj) {
        String cnpjNumerico = cnpj.replaceAll("[^0-9]", "");
        if (cnpjNumerico.length() != 14) {
            System.out.println("CNPJ inválido. Deve conter 14 dígitos numéricos.");
            return false;
        }
        if (cnpjNumerico.matches("(\\d)\\1{13}")) {
            System.out.println("CNPJ inválido. Todos os dígitos não podem ser iguais.");
            return false;
        }

        try {
            int[] multiplicadores1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpjNumerico.charAt(i)) * multiplicadores1[i];
            }
            int resto = soma % 11;
            int primeiroDigitoVerificador = (resto < 2) ? 0 : 11 - resto;

            int[] multiplicadores2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpjNumerico.charAt(i)) * multiplicadores2[i];
            }
            resto = soma % 11;
            int segundoDigitoVerificador = (resto < 2) ? 0 : 11 - resto;

            if (primeiroDigitoVerificador != Character.getNumericValue(cnpjNumerico.charAt(12))
                    || segundoDigitoVerificador != Character.getNumericValue(cnpjNumerico.charAt(13))) {
                System.out.println("CNPJ inválido. Dígitos verificadores não correspondem.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro ao validar CNPJ: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean validarCnh(String cnh) {
        String cnhNumerica = cnh.replaceAll("[^\\d]", "");
        if (cnhNumerica.length() != 11) {
            System.out.println("CNH inválida. Deve conter 11 dígitos numéricos.");
            return false;
        }
        return true;
    }

    public static boolean validarTelefone(String telefone) {
        String telefoneNumerico = telefone.replaceAll("[^\\d]", "");
        if (telefoneNumerico.length() < 10 || telefoneNumerico.length() > 11) {
            System.out.println("Telefone inválido. Deve conter entre 10 e 11 dígitos numéricos.");
            return false;
        }
        return true;
    }

    public static boolean validarAnoFabricacao(int anoFabricacao) {
        int anoAtual = LocalDate.now().getYear();
        if (anoFabricacao <= 1885 || anoFabricacao > anoAtual) {
            System.out.println("Ano de fabricação inválido. Deve ser maior que 1885 e menor ou igual ao ano atual.");
            return false;
        }
        return true;
    }

    public static boolean validarPlaca(String placa) {
        if (!Pattern.matches("[A-Z]{3}\\d{4}", placa.replaceAll("[^A-Za-z0-9]", "").toUpperCase())) {
            System.out.println("Placa inválida. Deve seguir o formato AAA0A00.");
            return false;
        }
        return true;
    }

    public static boolean validarQtdEixo(int qtdEixo) {
        if (qtdEixo <= 0) {
            System.out.println("Quantidade de eixos inválida. Deve ser maior que zero.");
            return false;
        }
        return true;
    }

    public static boolean validarGenero(String genero) {
        if (genero == null || !(genero.equalsIgnoreCase("Masculino") ||
                genero.equalsIgnoreCase("Feminino") ||
                genero.equalsIgnoreCase("Indefinido"))) {
            System.out.println("Gênero inválido. Deve ser Masculino, Feminino ou Indefinido.");
            return false;
        }
        return true;
    }

    public static boolean validarDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            System.out.println("Data de nascimento inválida. Deve ser uma data anterior à data atual.");
            return false;
        }
        return true;
    }

    public static boolean validarEmail(String email) {
        if (!Pattern.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
            System.out.println("Email inválido. O formato deve ser válido.");
            return false;
        }
        return true;
    }

    public static boolean validarCep(String cep) {
        String cepNumerico = cep.replaceAll("[^\\d]", "");
        if (cepNumerico.length() != 8) {
            System.out.println("CEP inválido. Deve conter 8 dígitos numéricos.");
            return false;
        }
        return true;
    }

    public static boolean validarNumero(String numero) {
        if (!Pattern.matches("\\d+", numero)) {
            System.out.println("Número inválido. Deve conter apenas dígitos numéricos.");
            return false;
        }
        return true;
    }

    public static boolean validarEstado(String estado) {
        if (!Pattern.matches("[A-Z]{2}", estado.toUpperCase())) {
            System.out.println("Estado inválido. Deve conter duas letras maiúsculas.");
            return false;
        }
        return true;
    }

    public static boolean validarNomeOficina(String nomeOficina) {
        if (nomeOficina == null || nomeOficina.trim().isEmpty()) {
            System.out.println("Nome da oficina inválido. O nome não pode ser vazio.");
            return false;
        }
        return true;
    }

    public static boolean validarNumeroSeguro(String numeroSeguro) {
        if (!Pattern.matches("\\d{10}", numeroSeguro)) {
            System.out.println("Número de seguro inválido. Deve conter 10 dígitos numéricos.");
            return false;
        }
        return true;
    }
}
