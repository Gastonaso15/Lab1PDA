package culturarte.api.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Primary
public class SimpleUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Para simplificar las pruebas, creamos un usuario básico
        // En producción deberías cargar desde tu base de datos
        if ("admin".equals(username)) {
            return User.builder()
                    .username(username)
                    .password("admin") // No se usa para JWT
                    .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))
                    .build();
        }
        
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
