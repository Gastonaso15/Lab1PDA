package culturarte.logica.endpoints;

import culturarte.logica.endpoints.envoltorios.*;
import culturarte.servicios.Fabrica;
import culturarte.servicios.interfaces.IPropuestaController;
import culturarte.servicios.interfaces.web.IPropuestaControllerWS;
import culturarte.servicios.DTs.*;

import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService(endpointInterface = "culturarte.servicios.interfaces.web.IPropuestaControllerWS")
public class PropuestaWSEndpoint implements IPropuestaControllerWS {

    private IPropuestaController controlador;

    public PropuestaWSEndpoint() {
        this.controlador = Fabrica.getInstance().getIPropuestaController();
    }

    @Override
    public DTPropuesta getDTPropuesta(String titulo) {
        return controlador.getDTPropuesta(titulo);
    }

    @Override
    public void crearPropuesta(String titulo, String descripcion, String lugar, @XmlJavaTypeAdapter(AdaptadorLocalDate.class) java.time.LocalDate fechaPrevista,
                               Double precioEntrada, Double montoNecesario, String imagen,
                               String proponente, String categoria, ListaStrings listaTipos) throws Exception {
        controlador.crearPropuesta(titulo, descripcion, lugar, fechaPrevista, precioEntrada,
                montoNecesario, imagen, proponente, categoria, listaTipos.getLista());
    }

    @Override
    public ListaDTPropuesta devolverTodasLasPropuestas() {
        return new ListaDTPropuesta(controlador.devolverTodasLasPropuestas());
    }

    @Override
    public ListaDTPropuesta devolverPropuestasPorEstado(DTEstadoPropuesta estado) {
        return new ListaDTPropuesta(controlador.devolverPropuestasPorEstado(estado));
    }

    @Override
    public ListaDTPropuesta getPropuestasIngresadas() {
        return new ListaDTPropuesta(controlador.getPropuestasIngresadas());
    }

    @Override
    public void modificarPropuesta(String titulo, String nuevaDescripcion, String nuevoLugar,
                                   @XmlJavaTypeAdapter(AdaptadorLocalDate.class) java.time.LocalDate nuevaFechaPrevista, Double nuevoPrecioEntrada,
                                   Double nuevoMontoNecesario, String imagen,
                                   ListaStrings listaTipos, String categoria) throws Exception {
        controlador.modificarPropuesta(titulo, nuevaDescripcion, nuevoLugar, nuevaFechaPrevista,
                nuevoPrecioEntrada, nuevoMontoNecesario, imagen, listaTipos.getLista(), categoria);
    }

    @Override
    public void crearCategoria(String nombre, String padre) throws Exception {
        controlador.crearCategoria(nombre, padre);
    }

    @Override
    public ListaDTCategoria devolverTodasLasCategorias() {
        return new ListaDTCategoria(controlador.devolverTodasLasCategorias());
    }

    @Override
    public void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto,
                                      String tipoRetorno) throws Exception {
        controlador.registrarColaboracion(tituloPropuesta, nicknameColaborador, monto, tipoRetorno);
    }

    @Override
    public void cancelarColaboracion(Long idColaboracion) throws Exception {
        controlador.cancelarColaboracion(idColaboracion);
    }

    @Override
    public void marcarConstanciaEmitida(Long idColaboracion) throws Exception {
        controlador.marcarConstanciaEmitida(idColaboracion);
    }

    @Override
    public ListaDTColaboracion obtenerTodasLasColaboraciones() {
        return new ListaDTColaboracion(controlador.obtenerTodasLasColaboraciones());
    }

    @Override
    public void evaluarPropuesta(String titulo, boolean publicar) throws Exception {
        controlador.evaluarPropuesta(titulo, publicar);
    }

    @Override
    public void publicarPropuesta(String titulo) throws Exception {
        controlador.publicarPropuesta(titulo);
    }

    @Override
    public void agregarComentario(String tituloPropuesta, String nicknameUsuario, String contenido) throws Exception {
        controlador.agregarComentario(tituloPropuesta, nicknameUsuario, contenido);
    }

    @Override
    public ListaDTComentario obtenerComentariosPropuesta(String tituloPropuesta) {
        return new ListaDTComentario(controlador.obtenerComentariosPropuesta(tituloPropuesta));
    }

    @Override
    public void extenderFinanciacion(DTUsuario usuario, String tituloPropuesta) {
        controlador.extenderFinanciacion(usuario, tituloPropuesta);
    }

    @Override
    public void modificarHistorialYEstadoPropuesta(DTPropuesta propuesta){
        controlador.modificarHistorialYEstadoPropuesta(propuesta);
    }

    @Override
    public ListaDTColaboracion devolverColaboracionesSinPago(String nicknameColaborador) {
        return new ListaDTColaboracion(controlador.devolverColaboracionesSinPago(nicknameColaborador));
    }

    @Override
    public void registrarPago(Long idColaboracion, DTPago dtPago) throws Exception {
        controlador.registrarPago(idColaboracion, dtPago);
    }
}
