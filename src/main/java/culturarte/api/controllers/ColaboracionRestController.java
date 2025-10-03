package culturarte.api.controllers;

import culturarte.api.dto.*;
import culturarte.api.dto.ColaboracionDto;
import culturarte.api.dto.ColaboracionPostDto;
import culturarte.api.dto.PropuestaDto;
import culturarte.api.dto.PropuestaCategoriaDto;
import culturarte.api.dto.TipoRetornoDto;
import culturarte.logica.controladores.PropuestaController; //as PropuestaControllerLogic;
import culturarte.logica.DTs.DTColaboracion;
import culturarte.logica.modelos.*;
import culturarte.logica.manejadores.PropuestaManejador;
import culturarte.logica.manejadores.UsuarioManejador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colaboraciones")
@CrossOrigin(origins = "*")
public class ColaboracionRestController {

    @Autowired
    private PropuestaController propuestaController;

    @Autowired
    private PropuestaManejador propuestaManejador;

    @Autowired
    private UsuarioManejador usuarioManejador;

    @GetMapping
    // @PreAuthorize("isAuthenticated()") // Temporalmente deshabilitado para pruebas
    public ResponseEntity<List<ColaboracionDto>> getAllColaboraciones() {
        try {
            List<DTColaboracion> dtColaboraciones = propuestaController.obtenerTodasLasColaboraciones();
            List<ColaboracionDto> colaboraciones = dtColaboraciones.stream()
                    .map(this::convertToColaboracionDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(colaboraciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    // @PreAuthorize("isAuthenticated()") // Temporalmente deshabilitado para pruebas
    public ResponseEntity<Void> createColaboracion(@Valid @RequestBody ColaboracionPostDto colaboracionDto) {
        try {
            // Obtener la propuesta por ID
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorId(colaboracionDto.getIdPropuesta().getId());
            if (propuesta == null) {
                return ResponseEntity.notFound().build();
            }

            // Obtener el colaborador por nombre (usando el nombre del DTO)
            Usuario usuario = usuarioManejador.obtenerUsuarioPorNickname(colaboracionDto.getColaborador().getNombre());
            if (!(usuario instanceof Colaborador colaborador)) {
                return ResponseEntity.badRequest().build();
            }

            // Crear la colaboración usando el controlador existente
            propuestaController.registrarColaboracion(
                    propuesta.getTitulo(),
                    colaborador.getNickname(),
                    colaboracionDto.getMonto(),
                    colaboracionDto.getTipoRetorno() != null ? colaboracionDto.getTipoRetorno().getTipo() : null
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    private ColaboracionDto convertToColaboracionDto(Colaboracion colaboracion) {
        ColaboracionDto dto = new ColaboracionDto();
        dto.setId(colaboracion.getId());
        dto.setMonto(colaboracion.getMonto());
        dto.setFechaColaboracion(colaboracion.getFechaHora().toLocalDate());

        // Convertir propuesta
        if (colaboracion.getPropuesta() != null) {
            PropuestaDto propuestaDto = new PropuestaDto();
            propuestaDto.setId(colaboracion.getPropuesta().getId());
            propuestaDto.setTitulo(colaboracion.getPropuesta().getTitulo());
            dto.setPropuesta(propuestaDto);
        }

        // Convertir colaborador
        if (colaboracion.getColaborador() != null) {
            PropuestaCategoriaDto colaboradorDto = new PropuestaCategoriaDto(
                    colaboracion.getColaborador().getId(),
                    colaboracion.getColaborador().getNickname()
            );
            dto.setColaborador(colaboradorDto);
        }

        // Convertir tipo de retorno
        if (colaboracion.getTipoRetorno() != null) {
            TipoRetornoDto tipoRetornoDto = new TipoRetornoDto(colaboracion.getTipoRetorno().toString());
            dto.setTipoRetorno(tipoRetornoDto);
        }

        return dto;
    }

    private ColaboracionDto convertToColaboracionDto(DTColaboracion dtColaboracion) {
        ColaboracionDto dto = new ColaboracionDto();
        dto.setId(dtColaboracion.getId());
        dto.setMonto(dtColaboracion.getMonto());
        dto.setFechaColaboracion(dtColaboracion.getFechaHora().toLocalDate());

        // Convertir propuesta
        if (dtColaboracion.getPropuesta() != null) {
            PropuestaDto propuestaDto = new PropuestaDto();
            propuestaDto.setId(dtColaboracion.getPropuesta().getId());
            propuestaDto.setTitulo(dtColaboracion.getPropuesta().getTitulo());
            dto.setPropuesta(propuestaDto);
        }

        // Convertir colaborador
        if (dtColaboracion.getColaborador() != null) {
            PropuestaCategoriaDto colaboradorDto = new PropuestaCategoriaDto(
                    dtColaboracion.getColaborador().getId(),
                    dtColaboracion.getColaborador().getNickname()
            );
            dto.setColaborador(colaboradorDto);
        }

        // Convertir tipo de retorno
        if (dtColaboracion.getTipoRetorno() != null) {
            TipoRetornoDto tipoRetornoDto = new TipoRetornoDto(dtColaboracion.getTipoRetorno().toString());
            dto.setTipoRetorno(tipoRetornoDto);
        }

        return dto;
    }
}
