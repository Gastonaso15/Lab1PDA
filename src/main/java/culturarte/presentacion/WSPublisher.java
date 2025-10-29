package culturarte.presentacion;

import culturarte.logica.endpoints.UsuarioWSEndpoint;

import jakarta.xml.ws.Endpoint;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.File;

public class WSPublisher {

    private static final String CONFIG_FILE_NAME = "config.properties";

    private static String getConfigurationFilePath() {
        String userHome = System.getProperty("user.home");
        File configDir = new File(userHome, ".Culturarte");
        File configFile = new File(configDir, CONFIG_FILE_NAME);

        if (configFile.exists()) {
            return configFile.getAbsolutePath();
        } else {
            return CONFIG_FILE_NAME;
        }
    }

    public static void main(String[] args) {
        String urlBase = "";
        String configPath = getConfigurationFilePath();

        try (FileInputStream fis = new FileInputStream(configPath)) {
            Properties prop = new Properties();
            prop.load(fis);
            urlBase = prop.getProperty("servidor.central.base_url");

            if (urlBase == null || urlBase.isEmpty()) {
                System.err.println("ERROR: La propiedad 'servidor.central.base_url' no se encontró o está vacía.");
                return;
            }

        } catch (IOException e) {
            System.err.println("ERROR al leer el archivo de configuración: " + configPath + ". Asegúrate de que existe en la ruta de ejecución o en ~/.Culturarte/.");
            e.printStackTrace();
            return;
        }

        String urlUsuario = urlBase + "/usuarios";
        System.out.println("Publicando Web Service de Usuarios en: " + urlUsuario + "?wsdl");

        Endpoint.publish(urlUsuario, new UsuarioWSEndpoint());

        System.out.println("Servidor Central iniciado. Web Services disponibles. Presione Enter para detener...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}