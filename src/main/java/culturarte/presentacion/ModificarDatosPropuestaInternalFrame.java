package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IPropuestaController;

public class ModificarDatosPropuestaInternalFrame extends JInternalFrame {

    private IPropuestaController ICP;

    private JComboBox<String> cbPropuestas;
    private JTextField tfDescripcion;
    private JTextField tfLugar;
    private JTextField tfFechaPrevista;
    private JTextField tfPrecioEntrada;
    private JTextField tfMontoNecesario;
    private JTextField tfFechaPublicacion;

    private JButton btnGuardar;

    private List<DTPropuesta> listaPropuestas;

    public ModificarDatosPropuestaInternalFrame(IPropuestaController ICP) {
        super("Modificar Propuesta", true, true, true, true);
        this.ICP = ICP;

        setSize(600, 400);
        setLayout(new GridLayout(8, 2, 5, 5));

        add(new JLabel("Seleccionar Propuesta:"));
        cbPropuestas = new JComboBox<>();
        add(cbPropuestas);

        add(new JLabel("Descripcion:"));
        tfDescripcion = new JTextField();
        add(tfDescripcion);

        add(new JLabel("Lugar:"));
        tfLugar = new JTextField();
        add(tfLugar);

        add(new JLabel("Fecha Prevista (yyyy-MM-dd):"));
        tfFechaPrevista = new JTextField();
        add(tfFechaPrevista);

        add(new JLabel("Precio Entrada:"));
        tfPrecioEntrada = new JTextField();
        add(tfPrecioEntrada);

        add(new JLabel("Monto Necesario:"));
        tfMontoNecesario = new JTextField();
        add(tfMontoNecesario);

        add(new JLabel("Fecha Publicacion (yyyy-MM-dd):"));
        tfFechaPublicacion = new JTextField();
        add(tfFechaPublicacion);

        btnGuardar = new JButton("Guardar Cambios");
        add(btnGuardar);

        // Cargar propuestas en el combo
        cargarPropuestas();

        // Listener para mostrar los datos de la propuesta seleccionada
        cbPropuestas.addActionListener(e -> cargarDatosPropuesta());

        // Listener para guardar cambios
        btnGuardar.addActionListener(e -> guardarCambios());
    }

    private void cargarPropuestas() {
        listaPropuestas = ICP.devolverTodasLasPropuestas();
        cbPropuestas.removeAllItems();
        for (DTPropuesta p : listaPropuestas) {
            cbPropuestas.addItem(p.getTitulo());
        }
        if (!listaPropuestas.isEmpty()) {
            cbPropuestas.setSelectedIndex(0);
            cargarDatosPropuesta();
        }
    }

    private void cargarDatosPropuesta() {
        int index = cbPropuestas.getSelectedIndex();
        if (index >= 0) {
            DTPropuesta p = listaPropuestas.get(index);
            tfDescripcion.setText(p.getDescripcion());
            tfLugar.setText(p.getLugar());
            tfFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
            tfPrecioEntrada.setText(p.getPrecioEntrada() != null ? p.getPrecioEntrada().toString() : "");
            tfMontoNecesario.setText(p.getMontoNecesario() != null ? p.getMontoNecesario().toString() : "");
            tfFechaPublicacion.setText(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "");
        }
    }

    private void guardarCambios() {
        try {
            int index = cbPropuestas.getSelectedIndex();
            if (index < 0) return;

            DTPropuesta p = listaPropuestas.get(index);

            String descripcion = tfDescripcion.getText();
            String lugar = tfLugar.getText();
            LocalDate fechaPrevista = LocalDate.parse(tfFechaPrevista.getText());
            Double precioEntrada = Double.parseDouble(tfPrecioEntrada.getText());
            Double montoNecesario = Double.parseDouble(tfMontoNecesario.getText());
            LocalDate fechaPublicacion = LocalDate.parse(tfFechaPublicacion.getText());

            ICP.modificarPropuesta(
                    p.getTitulo(),
                    descripcion,
                    lugar,
                    fechaPrevista,
                    precioEntrada,
                    montoNecesario,
                    fechaPublicacion
            );

            JOptionPane.showMessageDialog(this, "Propuesta modificada correctamente.");
            cargarPropuestas(); // refresca la lista
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar propuesta: " + ex.getMessage());
        }
    }
}
