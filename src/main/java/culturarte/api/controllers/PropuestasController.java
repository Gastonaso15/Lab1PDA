package culturarte.api.controllers;

import culturarte.api.dto.*;
import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.controladores.PropuestaController;
import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;
import culturarte.logica.modelos.Propuesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/propuestas")
@CrossOrigin(origins = "*")
public class PropuestasController {

    @Autowired
    private PropuestaController propuestaController;

    @Autowired
    private PropuestaManejador propuestaManejador;

    @Autowired
    private UsuarioManejador usuarioManejador;

    // Endpoint que sabemos que funciona
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test endpoint funcionando!");
    }

    // Obtener todas las propuestas
    @GetMapping
    public ResponseEntity<List<PropuestaGetDto>> getAllPropuestas() {
        try {
            List<DTPropuesta> dtPropuestas = propuestaController.devolverTodasLasPropuestas();
            List<PropuestaGetDto> propuestas = dtPropuestas.stream()
                    .map(this::convertToPropuestaGetDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(propuestas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Endpoint principal para obtener propuestas por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPropuestaById(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "image", required = false, defaultValue = "false") boolean returnImage) {
        try {
            System.out.println("DEBUG: Endpoint /{id} llamado con ID: " + id);
            System.out.println("DEBUG: returnImage parameter: " + returnImage);
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorId(id);
            if (propuesta == null) {
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("DEBUG: Propuesta obtenida, imagen: " + propuesta.getImagen());
            if (returnImage) {
                // Retornar la imagen si se solicita
                if (propuesta.getImagen() != null && !propuesta.getImagen().isEmpty()) {
                    try {
                        java.nio.file.Path imagePath = java.nio.file.Paths.get(propuesta.getImagen());
                        if (java.nio.file.Files.exists(imagePath)) {
                            byte[] imageBytes = java.nio.file.Files.readAllBytes(imagePath);
                            String contentType = getContentType(propuesta.getImagen());
                            return ResponseEntity.ok()
                                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                                .body(imageBytes);
                        } else {
                            return ResponseEntity.notFound().build();
                        }
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Retornar la propuesta completa
                PropuestaGetDto propuestaDto = convertToPropuestaGetDto(propuesta);
                return ResponseEntity.ok(propuestaDto);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Métodos de conversión simplificados
    private PropuestaGetDto convertToPropuestaGetDto(Propuesta propuesta) {
        PropuestaGetDto dto = new PropuestaGetDto();
        dto.setId(propuesta.getId());
        dto.setTitulo(propuesta.getTitulo());
        dto.setDescripcion(propuesta.getDescripcion());
        dto.setLugar(propuesta.getLugar());
        dto.setMontoNecesario(propuesta.getMontoNecesario());
        dto.setImagen(propuesta.getImagen());
        
        // Convertir proponente
        if (propuesta.getProponente() != null) {
            PropuestaCategoriaDto proponenteDto = new PropuestaCategoriaDto(
                    propuesta.getProponente().getId(),
                    propuesta.getProponente().getNickname()
            );
            dto.setProponente(proponenteDto);
        }
        
        // Convertir categoría
        if (propuesta.getCategoria() != null) {
            PropuestaCategoriaDto categoriaDto = new PropuestaCategoriaDto(
                    propuesta.getCategoria().getId(),
                    propuesta.getCategoria().getNombre()
            );
            dto.setCategoria(categoriaDto);
        }
        
        return dto;
    }

    private PropuestaGetDto convertToPropuestaGetDto(DTPropuesta dtPropuesta) {
        PropuestaGetDto dto = new PropuestaGetDto();
        dto.setId(dtPropuesta.getId());
        dto.setTitulo(dtPropuesta.getTitulo());
        dto.setDescripcion(dtPropuesta.getDescripcion());
        dto.setLugar(dtPropuesta.getLugar());
        dto.setMontoNecesario(dtPropuesta.getMontoNecesario());
        dto.setImagen(dtPropuesta.getImagen());
        
        // Convertir proponente - simplificado
        if (dtPropuesta.getDTProponente() != null) {
            PropuestaCategoriaDto proponenteDto = new PropuestaCategoriaDto(
                    dtPropuesta.getDTProponente().getId(),
                    dtPropuesta.getDTProponente().getNickname()
            );
            dto.setProponente(proponenteDto);
        }
        
        // Convertir categoría
        if (dtPropuesta.getCategoria() != null) {
            PropuestaCategoriaDto categoriaDto = new PropuestaCategoriaDto(
                    dtPropuesta.getCategoria().getId(),
                    dtPropuesta.getCategoria().getNombre()
            );
            dto.setCategoria(categoriaDto);
        }
        
        return dto;
    }

    private String getContentType(String filename) {
        if (filename == null) return "application/octet-stream";
        
        String extension = filename.toLowerCase();
        if (extension.endsWith(".jpg") || extension.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (extension.endsWith(".png")) {
            return "image/png";
        } else if (extension.endsWith(".gif")) {
            return "image/gif";
        } else if (extension.endsWith(".webp")) {
            return "image/webp";
        }
        
        return "application/octet-stream";
    }
}