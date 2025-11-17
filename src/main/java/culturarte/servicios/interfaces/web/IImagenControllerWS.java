package culturarte.servicios.interfaces.web;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 * Interfaz Web Service para el manejo de imágenes.
 * Permite subir imágenes al servidor central.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IImagenControllerWS {
    
    /**
     * Sube una imagen al servidor central.
     * 
     * @param imagenBytes Los bytes de la imagen
     * @param nombreArchivo El nombre del archivo (incluyendo extensión)
     * @param tipo Tipo de imagen: "propuesta" o "usuario"
     * @return La ruta relativa donde se guardó la imagen (ej: "uploads/propuestas/ImagenProp123.jpg")
     * @throws Exception Si ocurre un error al guardar la imagen
     */
    @WebMethod
    String subirImagen(byte[] imagenBytes, String nombreArchivo, String tipo) throws Exception;
    
    /**
     * Obtiene una imagen del servidor central en formato Base64.
     * Esto permite mostrar la imagen directamente en HTML usando data URIs.
     * 
     * @param rutaImagen La ruta relativa de la imagen (ej: "uploads/propuestas/ImagenProp123.jpg")
     * @return La imagen codificada en Base64, lista para usar en data URI (ej: "data:image/jpeg;base64,...")
     * @throws Exception Si la imagen no existe o hay un error al leerla
     */
    @WebMethod
    String obtenerImagenBase64(String rutaImagen) throws Exception;
}

