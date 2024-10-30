package org.SafeDrive.Operacional;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);

    public void oficinaOuUsuario() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Selecione o tipo de acesso:");
            System.out.println("1. Usuário");
            System.out.println("2. Oficina");
            System.out.println("0. Sair");

            int mainOption = scanner.nextInt();
            scanner.nextLine();

            switch (mainOption) {
                case 1:
                    new MenuOperacional("Usuário").exibirMenuOperacional();
                    break;
                case 2:
                    new MenuOperacional("Oficina").exibirMenuOperacional();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
