package culturarte.logica.controlador;

import culturarte.logica.DT.*;
import culturarte.logica.manejador.PropuestaManejador;
import culturarte.logica.manejador.UsuarioManejador;
import culturarte.logica.modelo.*;
import culturarte.persistencia.JPAUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PropuestaController implements IPropuestaController {

    public PropuestaController() {
        EntityManager em = JPAUtil.getEntityManager();
    }

    @Override
    public  void crearPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista, Double precioEntrada, Double montoNecesario, byte[] imagen, String proponente, String categoria, List<String> listaTipos) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);
        if (p != null)
            throw new Exception("La propuesta con el titulo " + titulo + " ya esta registrada");
        UsuarioManejador mu = UsuarioManejador.getinstance();
        Proponente prop = (Proponente) mu.obtenerUsuarioNick(proponente);

        Categoria Cat = mp.obtenerPorNombre(categoria);
        if (Cat == null) {
            throw new Exception("La categoría " + categoria + " no existe");
        }

        List<TipoRetorno> tipos = new ArrayList<>();
        for (String t : listaTipos) {
            try {
                tipos.add(TipoRetorno.valueOf(t));
            } catch (IllegalArgumentException e) {
                throw new Exception("Tipo de retorno inválido: " + t);
            }
        }

        p = new Propuesta(titulo,descripcion,lugar,fechaPrevista,precioEntrada,montoNecesario,imagen,prop,Cat,tipos);
        mp.addPropuesta(p);
    }

    @Override
    public List<DTPropuesta> devolverTodasLasPropuestas(){
        PropuestaManejador mp = PropuestaManejador.getinstance();
        List<DTPropuesta> props = mp.obtenerTodasLasPropuestas();
        return props;
    }

    @Override
    public List<DTPropuesta> devolverPropuestasPorEstado(DTEstadoPropuesta estado) {
        EstadoPropuesta estadoModelo = EstadoPropuesta.valueOf(estado.toString());
        PropuestaManejador mp = PropuestaManejador.getinstance();
        return mp.obtenerPropuestasPorEstado(estadoModelo);
    }
    @Override
    public void modificarPropuesta(String titulo, String descripcion, String lugar, LocalDate fechaPrevista,
                                   Double precioEntrada, Double montoNecesario, LocalDate fechaPublicacion) throws Exception {
        PropuestaManejador mp = PropuestaManejador.getinstance();
        Propuesta p = mp.obtenerPropuesta(titulo);

        if (p == null) {
            throw new Exception("⚠️ La propuesta con el título " + titulo + " no existe.");
        }

        // actualizar campos (excepto titulo)
        p.setDescripcion(descripcion);
        p.setLugar(lugar);
        p.setFechaPrevista(fechaPrevista);
        p.setPrecioEntrada(precioEntrada);
        p.setMontoNecesario(montoNecesario);
        p.setFechaPublicacion(fechaPublicacion);

        // persistir cambios
        mp.actualizarPropuesta(p);
    }

    @Override
    public List<String> listarNombreCategorias(){
        PropuestaManejador mc = PropuestaManejador.getinstance();
        List<String> cats = mc.listarCategorias();
        return cats;
    }

    @Override
    public void crearCategoria(String nombre, String padre) throws Exception {
        PropuestaManejador mc = PropuestaManejador.getinstance();
        Categoria catPadre = mc.obtenerPorNombre(padre);
        if (mc.obtenerPorNombre(nombre) != null) {
            throw new Exception("La categoría ya existe.");
        }
        if (catPadre == null) {
            catPadre = mc.obtenerPorNombre("Categoría");
        }
        Categoria nueva = new Categoria(nombre,catPadre);
        mc.addCategoria(nueva);
    }

    @Override
    public Categoria obtenerCategoriaPorNombre(String nombre) {
        PropuestaManejador mc = PropuestaManejador.getinstance();
        return mc.obtenerPorNombre(nombre);
    }

    @Override
    public List<DTCategoria> listarDTCategorias(){
        PropuestaManejador mc = PropuestaManejador.getinstance();
        List<DTCategoria> cats = mc.listarDTCategorias();
        return cats;
    }

    @Override
    public void registrarColaboracion(String tituloPropuesta, String nicknameColaborador, Double monto, String tipoRetorno) throws Exception {
        PropuestaManejador pm = PropuestaManejador.getinstance();
        UsuarioManejador um = UsuarioManejador.getinstance();

        Propuesta propuesta = pm.obtenerPropuesta(tituloPropuesta);
        if (propuesta == null) {
            throw new Exception("La propuesta con título " + tituloPropuesta + " no existe.");
        }

        Usuario usu = um.obtenerUsuarioNick(nicknameColaborador);
        if (!(usu instanceof Colaborador)) {
            throw new Exception("El usuario " + nicknameColaborador + " no es un colaborador válido.");
        }
        Colaborador colaborador = (Colaborador) usu;

        TipoRetorno retorno;
        try {
            retorno = TipoRetorno.valueOf(tipoRetorno);
        } catch (IllegalArgumentException e) {
            throw new Exception("El tipo de retorno ingresado no es válido.");
        }

        Colaboracion colaboracion = new Colaboracion(
                propuesta,
                colaborador,
                monto,
                retorno,
                java.time.LocalDateTime.now()
        );

        pm.agregarColaboracion(colaboracion);
    }
    @Override
    public List<DTColaboracion> getListaTodasLasColaboraciones() {
        //Tengo todas las propuestas
        List<DTPropuesta> listaPropuestas = this.devolverTodasLasPropuestas();
        //tengo que recorrer todas las propuestas y mostrar todas las colaboraciones asociadsa

        List<DTColaboracion> listaColaboraciones_externa = new ArrayList<>();
        List<DTColaboracion> listaColaboraciones_interna;
        for(DTPropuesta p : listaPropuestas) {   //recorro las propuestas
            //por cada propuesta tengo una lista de colaboraciones
            if (p != null) {
                listaColaboraciones_interna = p.getColaboraciones();     //extraigo la colaboracion de cada una
                for (DTColaboracion col : listaColaboraciones_interna) {
                    if (col != null) {
                        listaColaboraciones_externa.add(col);       //recorro la lista interna para agregarla a la lista externa
                    }
                }
            }
        }
        return listaColaboraciones_externa;
    }


    @Override
    public boolean cancelarColaboracionAPropuesta() {
        boolean cancelado = false;
        /*
        boolean confirmacion = false;
        //obentengo todas las colaboraciones
        List<DTColaboracion> listaColaboraciones = this.getListaTodasLasColaboraciones(); //Tengo que mostrar por pantalla la lista
        System.out.println("Elija la que desea eliminar");
        //DTColaboracion colaboracion = ?;   //Tengo que ver como la solicito
        //Tengo que ver como la consigo
        System.out.println("Confirma Eliminacion?");
        if (confirmacion) {
            listaColaboraciones.remove(colaboracion);
            cancelado = true;
        }
        */
        return  cancelado;
    }

}

