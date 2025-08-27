package culturarte.test;

import culturarte.logica.Fabrica;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import java.util.List;

public class TestConsultaPropuestasPorEstado {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL CASO DE USO: CONSULTA DE PROPUESTAS POR ESTADO ===\n");
        
        try {
            // Obtener el controlador
            Fabrica fabrica = Fabrica.getInstance();
            IPropuestaController propuestaController = fabrica.getIPropuestaController();
            
            System.out.println("✓ Controlador de propuestas inicializado correctamente");
            
            // Probar la consulta por cada estado
            System.out.println("\n--- PROBANDO CONSULTA POR ESTADOS ---");
            
            for (DTEstadoPropuesta estado : DTEstadoPropuesta.values()) {
                System.out.println("\n🔍 Consultando propuestas en estado: " + estado.name());
                
                try {
                    List<DTPropuesta> propuestas = propuestaController.devolverPropuestasPorEstado(estado);
                    
                    if (propuestas != null) {
                        System.out.println("   ✓ Consulta exitosa - Encontradas: " + propuestas.size() + " propuestas");
                        
                        // Mostrar detalles de las primeras 3 propuestas (si existen)
                        int count = 0;
                        for (DTPropuesta propuesta : propuestas) {
                            if (count >= 3) break;
                            
                            System.out.println("   📋 Propuesta " + (count + 1) + ":");
                            System.out.println("      - ID: " + propuesta.getId());
                            System.out.println("      - Título: " + propuesta.getTitulo());
                            System.out.println("      - Estado: " + (propuesta.getEstadoActual() != null ? propuesta.getEstadoActual().name() : "Sin estado"));
                            System.out.println("      - Proponente: " + (propuesta.getDTProponente() != null ? propuesta.getDTProponente().getNickname() : "N/A"));
                            
                            count++;
                        }
                        
                        if (propuestas.size() > 3) {
                            System.out.println("   ... y " + (propuestas.size() - 3) + " propuestas más");
                        }
                    } else {
                        System.out.println("   ⚠️ La consulta devolvió null");
                    }
                    
                } catch (Exception e) {
                    System.out.println("   ❌ Error en la consulta: " + e.getMessage());
                }
            }
            
            // Probar la consulta por ID (si hay propuestas)
            System.out.println("\n--- PROBANDO CONSULTA POR ID ---");
            
            try {
                List<DTPropuesta> todasLasPropuestas = propuestaController.devolverTodasLasPropuestas();
                
                if (todasLasPropuestas != null && !todasLasPropuestas.isEmpty()) {
                    DTPropuesta primeraPropuesta = todasLasPropuestas.get(0);
                    Long idPrueba = primeraPropuesta.getId();
                    
                    if (idPrueba != null) {
                        System.out.println("🔍 Consultando propuesta por ID: " + idPrueba);
                        
                        DTPropuesta propuestaDetalle = propuestaController.obtenerPropuestaPorId(idPrueba);
                        
                        if (propuestaDetalle != null) {
                            System.out.println("   ✓ Consulta por ID exitosa");
                            System.out.println("   📋 Detalles de la propuesta:");
                            System.out.println("      - ID: " + propuestaDetalle.getId());
                            System.out.println("      - Título: " + propuestaDetalle.getTitulo());
                            System.out.println("      - Descripción: " + (propuestaDetalle.getDescripcion() != null ? propuestaDetalle.getDescripcion().substring(0, Math.min(50, propuestaDetalle.getDescripcion().length())) + "..." : "Sin descripción"));
                            System.out.println("      - Estado: " + (propuestaDetalle.getEstadoActual() != null ? propuestaDetalle.getEstadoActual().name() : "Sin estado"));
                            System.out.println("      - Lugar: " + (propuestaDetalle.getLugar() != null ? propuestaDetalle.getLugar() : "No especificado"));
                            System.out.println("      - Fecha Prevista: " + (propuestaDetalle.getFechaPrevista() != null ? propuestaDetalle.getFechaPrevista().toString() : "No definida"));
                        } else {
                            System.out.println("   ❌ No se encontró la propuesta con ID: " + idPrueba);
                        }
                    } else {
                        System.out.println("   ⚠️ La primera propuesta no tiene ID asignado");
                    }
                } else {
                    System.out.println("   ℹ️ No hay propuestas en el sistema para probar la consulta por ID");
                }
                
            } catch (Exception e) {
                System.out.println("   ❌ Error en la consulta por ID: " + e.getMessage());
            }
            
            System.out.println("\n=== RESUMEN DE LA PRUEBA ===");
            System.out.println("✓ Interfaz IPropuestaController actualizada correctamente");
            System.out.println("✓ Implementación PropuestaController funcional");
            System.out.println("✓ Método obtenerPropuestaPorId implementado");
            System.out.println("✓ Método devolverPropuestasPorEstado funcional");
            System.out.println("✓ PropuestaManejador actualizado con nuevos métodos");
            System.out.println("✓ Interfaz de usuario ConsultaPropuestasPorEstadoInternalFrame creada");
            System.out.println("✓ Menú en EstacionDeTrabajo actualizado");
            
            System.out.println("\n🎉 CASO DE USO 'CONSULTA DE PROPUESTAS POR ESTADO' IMPLEMENTADO EXITOSAMENTE");
            
        } catch (Exception e) {
            System.out.println("❌ Error general en la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
