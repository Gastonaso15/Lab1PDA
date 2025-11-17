package culturarte.logica.endpoints;

import culturarte.servicios.interfaces.web.IImagenControllerWS;
import jakarta.jws.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Endpoint Web Service para el manejo de imágenes.
 * Implementa la lógica para guardar imágenes en el servidor central.
 */
@WebService(endpointInterface = "culturarte.servicios.interfaces.web.IImagenControllerWS")
public class ImagenWSEndpoint implements IImagenControllerWS {

    // Usar el directorio home del usuario para guardar imágenes
    // Esto asegura que funcione independientemente de dónde se ejecute el JAR
    private static final String BASE_UPLOAD_DIR;

    static {
        String userHome = System.getProperty("user.home");
        File culturarteDir = new File(userHome, ".Culturarte");
        File uploadsDir = new File(culturarteDir, "uploads");
        BASE_UPLOAD_DIR = uploadsDir.getAbsolutePath();
        // Crear el directorio si no existe
        uploadsDir.mkdirs();
    }

    @Override
    public String subirImagen(byte[] imagenBytes, String nombreArchivo, String tipo) throws Exception {
        if (imagenBytes == null || imagenBytes.length == 0) {
            throw new IllegalArgumentException("Los bytes de la imagen no pueden estar vacíos");
        }

        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacío");
        }

        if (tipo == null || (!tipo.equals("propuesta") && !tipo.equals("usuario"))) {
            throw new IllegalArgumentException("El tipo debe ser 'propuesta' o 'usuario'");
        }

        // Determinar el directorio según el tipo
        String subdirectorio = tipo.equals("propuesta") ? "propuestas" : "usuarios";
        File directorio = new File(BASE_UPLOAD_DIR, subdirectorio);
        if (!directorio.exists()) {
            if (!directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
            }
        }

        // Asegurar que el nombre del archivo sea seguro (sin path traversal)
        String nombreSeguro = new File(nombreArchivo).getName();

        // Crear la ruta completa del archivo
        File archivoDestino = new File(directorio, nombreSeguro);

        // Guardar los bytes en el archivo
        try (FileOutputStream fos = new FileOutputStream(archivoDestino)) {
            fos.write(imagenBytes);
            fos.flush();
        } catch (IOException e) {
            throw new Exception("Error al guardar la imagen: " + e.getMessage(), e);
        }

        // Devolver la ruta relativa (usando / para compatibilidad web)
        // Esta ruta se usará para buscar la imagen después
        String rutaRelativa = "uploads/" + subdirectorio + "/" + nombreSeguro;
        return rutaRelativa;
    }

    @Override
    public String obtenerImagenBase64(String rutaImagen) throws Exception {
        if (rutaImagen == null || rutaImagen.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta de la imagen no puede estar vacía");
        }

        // La ruta que viene puede ser relativa (uploads/propuestas/...) o absoluta
        // Necesitamos construir la ruta completa basada en BASE_UPLOAD_DIR
        File archivo = null;
        String rutaRelativa = null;

        if (rutaImagen.startsWith("uploads/") || rutaImagen.startsWith("uploads\\")) {
            // Es una ruta relativa, construir la ruta completa
            rutaRelativa = rutaImagen.replace("uploads/", "").replace("uploads\\", "");
            archivo = new File(BASE_UPLOAD_DIR, rutaRelativa);
        } else if (new File(rutaImagen).isAbsolute()) {
            // Es una ruta absoluta, verificar que esté dentro del directorio base
            archivo = new File(rutaImagen);
            String rutaAbsoluta = archivo.getAbsolutePath();
            String baseDirAbsoluta = new File(BASE_UPLOAD_DIR).getAbsolutePath();
            if (!rutaAbsoluta.startsWith(baseDirAbsoluta)) {
                throw new IOException("La ruta de la imagen está fuera del directorio permitido: " + rutaImagen);
            }
        } else {
            // Asumir que es relativa a BASE_UPLOAD_DIR
            archivo = new File(BASE_UPLOAD_DIR, rutaImagen);
        }

        // Si el archivo no existe en el nuevo directorio, intentar buscar en el directorio antiguo
        // (para compatibilidad con imágenes subidas antes de este cambio)
        if (!archivo.exists() || !archivo.isFile()) {
            // Intentar buscar en el directorio de trabajo actual (para imágenes antiguas)
            File archivoAntiguo = new File("uploads", rutaRelativa != null ? rutaRelativa : rutaImagen);
            if (archivoAntiguo.exists() && archivoAntiguo.isFile()) {
                archivo = archivoAntiguo;
            } else {
                throw new IOException("La imagen no existe: " + rutaImagen +
                        " (buscada en: " + archivo.getAbsolutePath() +
                        " y en: " + archivoAntiguo.getAbsolutePath() + ")");
            }
        }

        // Leer el archivo y convertirlo a Base64
        try (FileInputStream fis = new FileInputStream(archivo)) {
            byte[] imagenBytes = fis.readAllBytes();
            String base64 = Base64.getEncoder().encodeToString(imagenBytes);

            // Determinar el tipo MIME según la extensión
            String nombreArchivo = archivo.getName().toLowerCase();
            String mimeType;
            if (nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".jpeg")) {
                mimeType = "image/jpeg";
            } else if (nombreArchivo.endsWith(".png")) {
                mimeType = "image/png";
            } else if (nombreArchivo.endsWith(".gif")) {
                mimeType = "image/gif";
            } else {
                mimeType = "image/jpeg"; // Por defecto
            }

            // Devolver el data URI completo, listo para usar en HTML
            return "data:" + mimeType + ";base64," + base64;
        } catch (IOException e) {
            throw new Exception("Error al leer la imagen: " + e.getMessage(), e);
        }
    }
}

