package culturarte.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import culturarte.modelo.Propuesta;
import culturarte.modelo.Proponente;
import culturarte.services.PropuestaService;

public class AltaPropuestaInternalFrame extends JInternalFrame {

    private JTextField tfTitulo;
    private JTextField tfDescripcion;
    private JTextField tfLugar;
    private JTextField tfFechaPrevista; // formato yyyy-MM-dd
    private JTextField tfPrecioEntrada;
    private JTextField tfMontoNecesario;
    private JTextField tfImagenPath;

    private JComboBox<Proponente> cbProponente;

    private PropuestaService propuestaService;

    public AltaPropuestaInternalFrame() {
        super("Alta de Propuesta", true, true, true, true);
        setSize(500, 400);
        setLayout(new BorderLayout());

        propuestaService = new PropuestaService();

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        panel.add(new JLabel("Título:"));
        tfTitulo = new JTextField();
        panel.add(tfTitulo);

        panel.add(new JLabel("Descripción:"));
        tfDescripcion = new JTextField();
        panel.add(tfDescripcion);

        panel.add(new JLabel("Lugar:"));
        tfLugar = new JTextField();
        panel.add(tfLugar);

        panel.add(new JLabel("Fecha Prevista (yyyy-MM-dd):"));
        tfFechaPrevista = new JTextField();
        panel.add(tfFechaPrevista);

        panel.add(new JLabel("Precio Entrada:"));
        tfPrecioEntrada = new JTextField();
        panel.add(tfPrecioEntrada);

        panel.add(new JLabel("Monto Necesario:"));
        tfMontoNecesario = new JTextField();
        panel.add(tfMontoNecesario);

        panel.add(new JLabel("Imagen Path:"));
        tfImagenPath = new JTextField();
        panel.add(tfImagenPath);

        // Por simplicidad, asumimos un Proponente fijo o vacío por ahora
        panel.add(new JLabel("Proponente:"));
        cbProponente = new JComboBox<>();
        panel.add(cbProponente);

        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");
        botones.add(aceptar);
        botones.add(cancelar);
        add(botones, BorderLayout.SOUTH);

        cancelar.addActionListener(e -> dispose());

        aceptar.addActionListener(e -> {
            try {
                String titulo = tfTitulo.getText().trim();
                String descripcion = tfDescripcion.getText().trim();
                String lugar = tfLugar.getText().trim();
                LocalDate fechaPrevista = LocalDate.parse(tfFechaPrevista.getText().trim());
                Double precioEntrada = Double.parseDouble(tfPrecioEntrada.getText().trim());
                Double montoNecesario = Double.parseDouble(tfMontoNecesario.getText().trim());
                String imagenPath = tfImagenPath.getText().trim();
                Proponente proponente = (Proponente) cbProponente.getSelectedItem();

                if (titulo.isEmpty() || descripcion.isEmpty() || lugar.isEmpty() || proponente == null) {
                    JOptionPane.showMessageDialog(this,
                            "Completar todos los campos obligatorios",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Propuesta propuesta = new Propuesta();
                propuesta.setTitulo(titulo);
                propuesta.setDescripcion(descripcion);
                propuesta.setLugar(lugar);
                propuesta.setFechaPrevista(fechaPrevista);
                propuesta.setPrecioEntrada(precioEntrada);
                propuesta.setMontoNecesario(montoNecesario);
                propuesta.setImagenPath(imagenPath);
                propuesta.setProponente(proponente);

                propuestaService.crearPropuesta(propuesta);

                JOptionPane.showMessageDialog(this,
                        "Propuesta creada correctamente",
                        "Alta de Propuesta",
                        JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos
                tfTitulo.setText("");
                tfDescripcion.setText("");
                tfLugar.setText("");
                tfFechaPrevista.setText("");
                tfPrecioEntrada.setText("");
                tfMontoNecesario.setText("");
                tfImagenPath.setText("");
                cbProponente.setSelectedIndex(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear propuesta: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
