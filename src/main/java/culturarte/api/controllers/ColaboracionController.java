package culturarte.api.controllers;

import culturarte.api.dto.ColaboracionDto;
import culturarte.api.dto.PropuestaDto;
import culturarte.api.dto.PropuestaCategoriaDto;
import culturarte.api.dto.TipoRetornoDto;
import culturarte.api.dto.TotalAportesDto;
import culturarte.logica.controladores.PropuestaController as PropuestaControllerLogic;
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
@RequestMapping("/api/colaboraciones")
@CrossOrigin(origins = "*")
public class ColaboracionRestController {

    @Autowired
    private PropuestaControllerLogic propuestaController;

    @Autowired
    private PropuestaManejador propuestaManejador;

    @Autowired
    private UsuarioManejador usuarioManejador;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ColaboracionDto> createColaboracion(@Valid @RequestBody ColaboracionDto colaboracionDto) {
        try {
            // Obtener la propuesta por ID
            Propuesta propuesta = propuestaManejador.obtenerPropuestaPorId(colaboracionDto.getPropuesta().getId());
            if (propuesta == null) {
                return ResponseEntity.notFound().build();
            }

            // Obtener el colaborador por ID
            Usuario usuario = usuarioManejador.obtenerUsuarioPorId(colaboracionDto.getColaborador().getId());
            if (!(usuario instanceof Colaborador colaborador)) {
                return ResponseEntity.badRequest().build();
            }

            // Crear la colaboración usando el controlador existente
            propuestaController.registrarColaboracion(
                    propuesta.getTitulo(),
                    colaborador.getNickname(),
                    colaboracionDto.getMonto(),
                    colaboracionDto.getTipoRetorno().getTipo()
            );

            // Obtener la colaboración creada (necesitarías implementar un método para obtener la última colaboración)
            Colaboracion colaboracion = propuestaManejador.obtenerColaboracionPorId(colaboracionDto.getId());
            ColaboracionDto responseDto = convertToColaboracionDto(colaboracion);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/total")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TotalAportesDto> getTotalAportes() {
        try {
            List<DTColaboracion> colaboraciones = propuestaController.obtenerTodasLasColaboraciones();
            Double total = colaboraciones.stream()
                    .mapToDouble(DTColaboracion::getMonto)
                    .sum();

            TotalAportesDto totalDto = new TotalAportesDto(total);
            return ResponseEntity.ok(totalDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ColaboracionDto convertToColaboracionDto(Colaboracion colaboracion) {
        ColaboracionDto dto = new ColaboracionDto();
        dto.setId(colaboracion.getId());
        dto.setMonto(colaboracion.getMonto());
        dto.setFechaHora(colaboracion.getFechaHora());

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
        dto.setFechaHora(dtColaboracion.getFechaHora());

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
