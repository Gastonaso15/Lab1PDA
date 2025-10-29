package culturarte.servicios.interfaces;

import culturarte.servicios.DTs.DTUsuario;
import culturarte.logica.modelos.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ISesionController
{
    @WebMethod
    DTUsuario login(String nick, String password);
    @WebMethod
    void logout();
    @WebMethod
    Usuario getUsuarioActual();
    @WebMethod
    boolean isLoggedIn();
}