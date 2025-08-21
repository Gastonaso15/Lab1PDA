package culturarte.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import culturarte.modelo.Propuesta;
import culturarte.modelo.Proponente;
import culturarte.services.PropuestaService;
import culturarte.services.UsuarioService;


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
    private UsuarioService usuarioService;


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

        panel.add(new JLabel("Imagen:"));
        JPanel imagenPanel = new JPanel(new BorderLayout(5, 5));
        tfImagenPath = new JTextField();
        tfImagenPath.setEditable(false);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        imagenPanel.add(tfImagenPath, BorderLayout.CENTER);
        imagenPanel.add(btnSeleccionarImagen, BorderLayout.EAST);
        panel.add(imagenPanel);

        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                tfImagenPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        panel.add(new JLabel("Proponente:"));
        cbProponente = new JComboBox<>();
        usuarioService = new UsuarioService();

        List<Proponente> proponentes = usuarioService.obtenerTodosLosProponentes();
        for (Proponente p : proponentes) {
            cbProponente.addItem(p);
        }

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
                Proponente proponente = (Proponente) cbProponente.getSelectedItem();

                if (titulo.isEmpty() || descripcion.isEmpty() || lugar.isEmpty() || proponente == null) {
                    JOptionPane.showMessageDialog(this,
                            "Completar todos los campos obligatorios",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                byte[] imagenBytes = null;
                String rutaImagen = tfImagenPath.getText().trim();
                if (!rutaImagen.isEmpty()) {
                    File file = new File(rutaImagen);
                    try (FileInputStream fis = new FileInputStream(file)) {
                        imagenBytes = fis.readAllBytes();
                    } catch (IOException ioEx) {
                        JOptionPane.showMessageDialog(this,
                                "Error al leer la imagen: " + ioEx.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                Propuesta propuesta = new Propuesta();
                propuesta.setTitulo(titulo);
                propuesta.setDescripcion(descripcion);
                propuesta.setLugar(lugar);
                propuesta.setFechaPrevista(fechaPrevista);
                propuesta.setPrecioEntrada(precioEntrada);
                propuesta.setMontoNecesario(montoNecesario);
                propuesta.setImagen(imagenBytes);
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
