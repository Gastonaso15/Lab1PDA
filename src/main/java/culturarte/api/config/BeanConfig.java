package culturarte.api.config;

import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.controladores.PropuestaController;
import culturarte.logica.controladores.UsuarioController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UsuarioManejador usuarioManejador() {
        return UsuarioManejador.getInstance();
    }

    @Bean
    public PropuestaManejador propuestaManejador() {
        return PropuestaManejador.getInstance();
    }

    @Bean
    public PropuestaController propuestaController() {
        return new PropuestaController();
    }

    @Bean
    public UsuarioController usuarioController() {
        return new UsuarioController();
    }
}
