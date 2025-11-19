# Registro de Horas - Mejoras y Tests

## Resumen del Trabajo Realizado

### Tests Unitarios
- Tests para PropuestaController
- Tests para UsuarioController  
- Tests para UsuarioManejador
- Tests para PropuestaManejador
- Tests para UsuarioWSEndpoint
- Tests para PropuestaWSEndpoint

### Mejoras de GUI
- Refactorización de EstacionDeTrabajo
- Mejoras en ~15 InternalFrames:
  - AltaUsuario, AltaPropuesta
  - ConsultarPropuesta, EvaluarPropuesta
  - RegistrarColaboracion, CancelarColaboracion
  - SeguirUsuario, DejarSeguirUsuario
  - ConsultaPerfilProponente, ConsultaPerfilColaborador
  - VerProponentesEliminados
  - ConsultaPropuestasPorEstado
  - ModificarDatosPropuesta
  - AltaCategoria
  - GestionarComentarios
  - VerRegistroAcceso
- Aplicación de estilos consistentes
- Corrección de problemas de visibilidad
- Configuración de menús desplegables

### Correcciones de Bugs
- Conflicto de nombres en ConsultaPropuestasPorEstadoInternalFrame
- Problemas con colores dependientes del tema del sistema
- Problemas con tamaños de elementos
- Problemas con menús desplegables
- Visualización de propuestas eliminadas

---

## Distribución de Horas

| Categoría | Horas | Porcentaje | Descripción |
|-----------|-------|------------|-------------|
| **Estudio** | 3-4 | 5% | Analizar código existente, documentación de Swing/Mockito/JUnit, entender arquitectura MVC |
| **Análisis** | 4-5 | 6% | Identificar métodos sin tests, analizar problemas de UI, determinar requisitos |
| **Diseño** | 5-6 | 7% | Diseñar casos de prueba, planear mejoras de UI, definir estándares de estilo |
| **Implementación Lógica** | 18-22 | 26% | Escribir tests unitarios, mockear dependencias, casos de éxito/error, debugging |
| **Implementación GUI** | 28-35 | 42% | Refactorizar UI, mejorar frames, aplicar estilos, corregir visibilidad |
| **Verificación** | 6-8 | 9% | Ejecutar tests, testing manual, verificar correcciones |
| **Otros** | 4-6 | 6% | Configuración, búsqueda de documentación, revisión, debugging adicional |

### **TOTAL: 68-86 horas** (Recomendado: **75 horas**)

---

## Detalle por Categoría

### Estudio (3-4 horas)
- Revisar estructura del código existente: 1 hora
- Estudiar documentación de Swing, Mockito, JUnit: 1-1.5 horas
- Entender patrón MVC y arquitectura del proyecto: 1-1.5 horas

### Análisis (4-5 horas)
- Identificar métodos sin tests: 1 hora
- Analizar problemas de UI reportados: 1-1.5 horas
- Determinar requisitos de mejoras: 1 hora
- Planificar refactor de UI: 1-1.5 horas

### Diseño (5-6 horas)
- Diseñar casos de prueba para cada método: 2 horas
- Planear estructura de tests: 1 hora
- Diseñar mejoras de UI (layouts, colores, componentes): 1.5-2 horas
- Definir estándares de estilo: 0.5-1 hora

### Implementación Lógica (18-22 horas)
- Escribir tests para PropuestaController: 3-4 horas
- Escribir tests para UsuarioController: 3-4 horas
- Escribir tests para Manejadores: 3-4 horas
- Escribir tests para Endpoints: 2-3 horas
- Mockear dependencias correctamente: 2-3 horas
- Debugging y corrección de tests: 3-4 horas
- Verificar cobertura: 2 horas

### Implementación GUI (28-35 horas)
- Refactorizar EstacionDeTrabajo: 2-3 horas
- Mejorar cada InternalFrame (15 frames × 1.5-2 horas): 22-30 horas
  - Cambiar layouts a GridBagLayout
  - Aplicar estilos consistentes
  - Mejorar botones y colores
  - Aumentar tamaños de fuentes
  - Configurar JComboBox
- Aplicar estilos consistentes globalmente: 2-3 horas
- Corregir problemas de visibilidad: 2-3 horas
- Testing visual: 2 horas

### Verificación (6-8 horas)
- Ejecutar todos los tests: 1 hora
- Verificar cobertura de código: 1 hora
- Testing manual de UI: 2-3 horas
- Verificar colores y tamaños en diferentes temas: 1-2 horas
- Probar menús desplegables: 0.5-1 hora
- Verificar correcciones de bugs: 0.5-1 hora

### Otros (4-6 horas)
- Configuración de entorno (Maven, dependencias): 1 hora
- Búsqueda de documentación y ejemplos: 1-2 horas
- Revisión de código: 1 hora
- Depuración adicional y ajustes: 1-2 horas

---

## Notas

- Las horas están estimadas para un desarrollador de nivel junior/intermedio
- La mayor parte del tiempo se invirtió en **Implementación GUI** (42%) debido a la cantidad de frames mejorados
- Los tests unitarios representan una parte significativa (26%) del trabajo
- Se recomienda usar **75 horas** como valor total para el registro


