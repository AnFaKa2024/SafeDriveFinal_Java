package org.SafeDrive;

import jakarta.ws.rs.core.UriBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.SafeDrive.Operacional.Menu;

import java.io.IOException;
import java.net.URI;

import static org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory.createHttpServer;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int DEFAULT_PORT = 9998;

    private static int getPort() {
        String httpPort = System.getProperty("jersey.test.port");
        if (httpPort != null) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
                logger.warn("Número de porta inválido fornecido, utilizando a porta padrão " + DEFAULT_PORT);
            }
        }
        return DEFAULT_PORT;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort()).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() {
        ResourceConfig resourceConfig = new ResourceConfig()
                .packages("org.SafeDrive.Controle")
                .property(ServerProperties.WADL_FEATURE_DISABLE, true);

        logger.info("Iniciando o servidor HTTP Grizzly...");
        return createHttpServer(BASE_URI, resourceConfig, true);
    }

    public static void main(String[] args) {
        HttpServer httpServer = null;
        try {
            httpServer = startServer();
            logger.info(String.format("O aplicativo Jersey começou com WADL disponível em %sapplication.wadl\nPressione Enter para pará-lo...", BASE_URI));

            Menu menu = new Menu();
            menu.oficinaOuUsuario();

            System.in.read();
        } catch (IOException e) {
            logger.error("IOException ocorreu ao iniciar o servidor: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Ocorreu um erro inesperado: " + e.getMessage(), e);
        } finally {
            if (httpServer != null) {
                httpServer.shutdownNow();
            }
            logger.info("SafeDrive Finalizado.");
        }
    }
}
