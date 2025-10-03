package culturarte.api.controllers;

import culturarte.api.security.JwtUtil;
import culturarte.api.dto.LoginRequest;
import culturarte.api.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Para simplificar las pruebas, vamos a crear un login básico
            // En producción deberías validar contra tu base de datos de usuarios
            if ("admin".equals(loginRequest.getUsername()) && "admin".equals(loginRequest.getPassword())) {
                String token = jwtUtil.generateTokenWithRole(loginRequest.getUsername(), "USER");
                return ResponseEntity.ok(new LoginResponse(token, "Bearer", 86400L));
            } else {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint funcionando correctamente");
    }
}