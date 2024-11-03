package org.SafeDrive.Servico;

import org.SafeDrive.Modelo.Login;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidadorEntidades {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    public static boolean validarLogin(Login login) {
        if (login == null) {
            System.out.println("Login inválido: objeto Login é nulo.");
            return false;
        }

        if (login.getEmail() == null || login.getEmail().isEmpty()) {
            System.out.println("Login inválido: o email não pode ser nulo ou vazio.");
            return false;
        }

        if (!EMAIL_PATTERN.matcher(login.getEmail()).matches()) {
            System.out.println("Login inválido: o formato do email é inválido.");
            return false;
        }

        if (login.getSenha() == null || login.getSenha().isEmpty()) {
            System.out.println("Login inválido: a senha não pode ser nula ou vazia.");
            return false;
        }

        if (login.getSenha().length() < 6) {
            System.out.println("Login inválido: a senha deve ter pelo menos 6 caracteres.");
            return false;
        }
        return false;
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

        if (cpfNumerico.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
        }

        if (!isCpfValido(cpfNumerico)) {
            throw new IllegalArgumentException("CPF inválido. O CPF não é válido de acordo com o algoritmo.");
        }

        return true;
    }

    private static boolean isCpfValido(String cpf) {
        int soma = 0;
        int digito1, digito2;

        for (int i = 0; i < 9; i++) {
            soma += (10 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        int resto = soma % 11;
        digito1 = (resto < 2) ? 0 : 11 - resto;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (11 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        resto = soma % 11;
        digito2 = (resto < 2) ? 0 : 11 - resto;

        return (digito1 == Character.getNumericValue(cpf.charAt(9)) && digito2 == Character.getNumericValue(cpf.charAt(10)));
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

    public static void validarSenha(String senha) {
        // Critérios de validação
        int minLength = 8; // Mínimo de 8 caracteres
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        if (senha.length() < minLength) {
            throw new IllegalArgumentException("A senha deve ter pelo menos " + minLength + " caracteres.");
        }

        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        StringBuilder msgErro = new StringBuilder("A senha deve conter:");
        if (!hasUppercase) msgErro.append("\n- Pelo menos uma letra maiúscula.");
        if (!hasLowercase) msgErro.append("\n- Pelo menos uma letra minúscula.");
        if (!hasDigit) msgErro.append("\n- Pelo menos um dígito.");
        if (!hasSpecialChar) msgErro.append("\n- Pelo menos um caractere especial.");

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
            throw new IllegalArgumentException(msgErro.toString());
        }
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
