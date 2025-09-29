package culturarte.api.controllers;

import culturarte.api.dto.PropuestaDto;
import culturarte.api.dto.PropuestaCategoriaDto;
import culturarte.api.dto.PropuestaEstadoDto;
import culturarte.api.dto.TipoRetornoDto;
import culturarte.logica.controladores.PropuestaController; //as PropuestaControllerLogic;
import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.DTs.DTCategoria;
import culturarte.logica.modelos.*;
import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/propuestas")
@CrossOrigin(origins = "*")
public class PropuestaRestController {

    @Autowired
    private PropuestaController propuestaController;

    @Autowired
    private PropuestaManejador propuestaManejador;

    @Autowired
    private UsuarioManejador usuarioManejador;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PropuestaDto>> getAllPropuestas() {
        try {
            List<DTPropuesta> dtPropuestas = propuestaController.devolverTodasLasPropuestas();
            List<PropuestaDto> propuestas = dtPropuestas.stream()
                    .map(this::convertToPropuestaDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(propuestas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropuestaDto> createPropuesta(@Valid @RequestBody PropuestaDto propuestaDto) {
        try {
            // Convertir DTO a parámetros para el controlador existente
            List<String> tiposRetorno = propuestaDto.getTiposRetorno() != null ?
                    propuestaDto.getTiposRetorno().stream()
                            .map(TipoRetornoDto::getTipo)
                            .collect(Collectors.toList()) : new ArrayList<>();

            propuestaController.crearPropuesta(
                    propuestaDto.getTitulo(),
                    propuestaDto.getDescripcion(),
                    propuestaDto.getLugar(),
                    propuestaDto.getFechaPrevista(),
                    propuestaDto.getPrecioEntrada(),
                    propuestaDto.getMontoNecesario(),
                    propuestaDto.getImagen(),
                    propuestaDto.getProponente().getNombre(), // Usando el nombre como nickname del proponente del DTO
                    propuestaDto.getCategoria().getNombre(),
                    tiposRetorno
            );

            // Obtener la propuesta creada
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorTitulo(propuestaDto.getTitulo());
            PropuestaDto responseDto = convertToPropuestaDto(propuesta);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropuestaDto> getPropuestaById(@PathVariable Long id) {
        try {
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorId(id);
            if (propuesta == null) {
                return ResponseEntity.notFound().build();
            }
            PropuestaDto propuestaDto = convertToPropuestaDto(propuesta);
            return ResponseEntity.ok(propuestaDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropuestaDto> updatePropuesta(@PathVariable Long id, @Valid @RequestBody PropuestaDto propuestaDto) {
        try {
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorId(id);
            if (propuesta == null) {
                return ResponseEntity.notFound().build();
            }

            // Convertir DTO a parámetros para el controlador existente
            List<String> tiposRetorno = propuestaDto.getTiposRetorno() != null ?
                    propuestaDto.getTiposRetorno().stream()
                            .map(TipoRetornoDto::getTipo)
                            .collect(Collectors.toList()) : new ArrayList<>();

            propuestaController.modificarPropuesta(
                    propuesta.getTitulo(), // Usar el título existente
                    propuestaDto.getDescripcion(),
                    propuestaDto.getLugar(),
                    propuestaDto.getFechaPrevista(),
                    propuestaDto.getPrecioEntrada(),
                    propuestaDto.getMontoNecesario(),
                    propuestaDto.getImagen(),
                    tiposRetorno,
                    propuestaDto.getCategoria().getNombre()
            );

            // Obtener la propuesta actualizada
            Propuesta propuestaActualizada = propuestaManejador.obtenerPropuestaPorId(id);
            PropuestaDto responseDto = convertToPropuestaDto(propuestaActualizada);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePropuesta(@PathVariable Long id) {
        try {
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorId(id);
            if (propuesta == null) {
                return ResponseEntity.notFound().build();
            }

            // Aquí deberías implementar la lógica para eliminar la propuesta
            // propuestaManejador.eliminarPropuesta(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private PropuestaDto convertToPropuestaDto(Propuesta propuesta) {
        PropuestaDto dto = new PropuestaDto();
        dto.setId(propuesta.getId());
        dto.setTitulo(propuesta.getTitulo());
        dto.setDescripcion(propuesta.getDescripcion());
        dto.setLugar(propuesta.getLugar());
        dto.setFechaPrevista(propuesta.getFechaPrevista());
        dto.setPrecioEntrada(propuesta.getPrecioEntrada());
        dto.setMontoNecesario(propuesta.getMontoNecesario());
        dto.setFechaPublicacion(propuesta.getFechaPublicacion());
        dto.setImagen(propuesta.getImagen());
        dto.setEstadoActual(propuesta.getEstadoActual() != null ? propuesta.getEstadoActual().toString() : null);

        // Convertir categoría
        if (propuesta.getCategoria() != null) {
            PropuestaCategoriaDto categoriaDto = new PropuestaCategoriaDto(
                    propuesta.getCategoria().getId(),
                    propuesta.getCategoria().getNombre()
            );
            dto.setCategoria(categoriaDto);
        }

        // Convertir proponente
        if (propuesta.getProponente() != null) {
            PropuestaCategoriaDto proponenteDto = new PropuestaCategoriaDto(
                    propuesta.getProponente().getId(),
                    propuesta.getProponente().getNickname()
            );
            dto.setProponente(proponenteDto);
        }

        // Convertir historial
        if (propuesta.getHistorial() != null) {
            List<PropuestaEstadoDto> historialDto = propuesta.getHistorial().stream()
                    .map(pe -> new PropuestaEstadoDto(
                            pe.getFechaCambio().atStartOfDay(),
                            pe.getEstado().toString()
                    ))
                    .collect(Collectors.toList());
            dto.setHistorial(historialDto);
        }

        // Convertir tipos de retorno
        if (propuesta.getTiposRetorno() != null) {
            List<TipoRetornoDto> tiposRetornoDto = propuesta.getTiposRetorno().stream()
                    .map(tr -> new TipoRetornoDto(tr.toString()))
                    .collect(Collectors.toList());
            dto.setTiposRetorno(tiposRetornoDto);
        }

        return dto;
    }

    private PropuestaDto convertToPropuestaDto(DTPropuesta dtPropuesta) {
        PropuestaDto dto = new PropuestaDto();
        dto.setId(dtPropuesta.getId());
        dto.setTitulo(dtPropuesta.getTitulo());
        dto.setDescripcion(dtPropuesta.getDescripcion());
        dto.setLugar(dtPropuesta.getLugar());
        dto.setFechaPrevista(dtPropuesta.getFechaPrevista());
        dto.setPrecioEntrada(dtPropuesta.getPrecioEntrada());
        dto.setMontoNecesario(dtPropuesta.getMontoNecesario());
        dto.setFechaPublicacion(dtPropuesta.getFechaPublicacion());
        dto.setImagen(dtPropuesta.getImagen());
        dto.setEstadoActual(dtPropuesta.getEstadoActual() != null ? dtPropuesta.getEstadoActual().toString() : null);

        // Convertir categoría
        if (dtPropuesta.getCategoria() != null) {
            PropuestaCategoriaDto categoriaDto = new PropuestaCategoriaDto(
                    dtPropuesta.getCategoria().getId(),
                    dtPropuesta.getCategoria().getNombre()
            );
            dto.setCategoria(categoriaDto);
        }

        // Convertir proponente
        if (dtPropuesta.getDTProponente() != null) {
            PropuestaCategoriaDto proponenteDto = new PropuestaCategoriaDto(
                    dtPropuesta.getDTProponente().getId(),
                    dtPropuesta.getDTProponente().getNickname()
            );
            dto.setProponente(proponenteDto);
        }

        return dto;
    }
}
