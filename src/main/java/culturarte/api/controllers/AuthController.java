package culturarte.api.controllers;

import culturarte.api.security.JwtUtil;
import culturarte.logica.modelos.Usuario;
import culturarte.logica.manejadores.UsuarioManejador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioManejador usuarioManejador;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            String token = jwtUtil.generateToken(loginRequest.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            return ResponseEntity.status(401).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Verificar si el usuario ya existe
            Usuario existingUser = usuarioManejador.obtenerUsuarioPorNickname(registerRequest.getUsername());
            if (existingUser != null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "El usuario ya existe");
                return ResponseEntity.status(400).body(error);
            }

            // Aquí deberías implementar la lógica para crear un nuevo usuario
            // Por ahora, solo devolvemos un error indicando que no está implementado
            Map<String, String> error = new HashMap<>();
            error.put("error", "Registro no implementado");
            return ResponseEntity.status(501).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error en el registro");
            return ResponseEntity.status(500).body(error);
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}