package org.SafeDrive.Operacional;

import org.apache.logging.log4j.core.util.JsonUtils;

public class void MenuMecanico {
    int opcao;
    do {
        System.out.println("""" \n ==== MENU MECÂNICO ====
                            1. Cadastrar Mecânico
                            2. Listar Mecânico
                            3. Remover Mecânico
                            """);


        opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao){
            case 1:
                cadastrarMecanico();
        }
    }
}
