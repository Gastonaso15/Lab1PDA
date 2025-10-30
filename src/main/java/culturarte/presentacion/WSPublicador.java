package culturarte.presentacion;

import culturarte.logica.endpoints.PropuestaWSEndpoint;
import culturarte.logica.endpoints.UsuarioWSEndpoint;

import jakarta.xml.ws.Endpoint;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.File;

public class WSPublicador {

    private static final String ARCHIVO_CONFIGURACION = "config.propiedades";

    private static String getConfigurationFilePath() {
        String userHome = System.getProperty("user.home");
        File direccionConfiguracion = new File(userHome, ".Culturarte");
        File archivoConfiguracion = new File(direccionConfiguracion, ARCHIVO_CONFIGURACION);

        if (archivoConfiguracion.exists()) {
            return archivoConfiguracion.getAbsolutePath();
        } else {
            return ARCHIVO_CONFIGURACION;
        }
    }

    public static void main(String[] args) {
        String urlBase = "";
        String pathConfiguracion = getConfigurationFilePath();

        try (FileInputStream fis = new FileInputStream(pathConfiguracion)) {
            Properties prop = new Properties();
            prop.load(fis);
            urlBase = prop.getProperty("servidor.central.base_url");

            if (urlBase == null || urlBase.isEmpty()) {
                System.err.println("ERROR: La propiedad 'servidor.central.base_url' no se encontró o está vacía.");
                return;
            }

        } catch (IOException e) {
            System.err.println("ERROR al leer el archivo de configuración: " + pathConfiguracion + ". Asegúrate de que existe en la ruta de ejecución o en ~/.Culturarte/.");
            e.printStackTrace();
            return;
        }


        String urlUsuario = urlBase + "/usuarios";
        Endpoint.publish(urlUsuario, new UsuarioWSEndpoint());
        System.out.println("Web Service de usuarios: " + urlUsuario + "?wsdl");

        String urlPropuesta = urlBase + "/propuestas";
        Endpoint.publish(urlPropuesta, new PropuestaWSEndpoint());
        System.out.println("Web Service de propuestas: " + urlPropuesta + "?wsdl");

    }
}