package culturarte.presentacion;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import culturarte.logica.DT.DTCategoria;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;
import culturarte.logica.DT.DTTipoRetorno;

public class AltaPropuestaInternalFrame extends JInternalFrame {

    private JTextField tfTitulo;
    private JTextField tfDescripcion;
    private JTextField tfLugar;
    private JTextField tfFechaPrevista; // formato yyyy-MM-dd
    private JTextField tfPrecioEntrada;
    private JTextField tfMontoNecesario;
    private JTextField tfImagenPath;
    private JTree treeCategorias;
    private DefaultMutableTreeNode rootCategorias;
    private List<JCheckBox> checkBoxesTiposRetorno;
    private JComboBox<String> cbProponente;

    private IUsuarioController UsuarioContr;
    private IPropuestaController PropuestaContr;

    public AltaPropuestaInternalFrame(IPropuestaController icp,IUsuarioController icu) {
        super("Alta de Propuesta", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;
        UsuarioContr = icu;

        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));

        rootCategorias = new DefaultMutableTreeNode("Categorías");
        treeCategorias = new JTree(rootCategorias);
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollTree = new JScrollPane(treeCategorias);

        List<DTCategoria> categorias = PropuestaContr.listarDTCategorias();
        CategoriaUIHelper.cargarCategorias(treeCategorias, null, categorias);

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

        List<String> proponentes = UsuarioContr.devolverNicknamesProponentes();
        for (String p : proponentes) {
            cbProponente.addItem(p);
        }

        panel.add(cbProponente);

        panel.add(new JLabel("Tipo(s) de Retorno:"));
        JPanel panelCheckBoxes = new JPanel();
        panelCheckBoxes.setLayout(new BoxLayout(panelCheckBoxes, BoxLayout.Y_AXIS));
        checkBoxesTiposRetorno = new ArrayList<>();
        List<String> tiposRetorno = listarTiposRetorno();
        for (String tipo : tiposRetorno) {
            JCheckBox checkBox = new JCheckBox(tipo);
            checkBoxesTiposRetorno.add(checkBox);
            panelCheckBoxes.add(checkBox);
        }
        JScrollPane scrollTiposRetorno = new JScrollPane(panelCheckBoxes);

        panel.add(scrollTiposRetorno);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, panel);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);


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
                String proponente = (String) cbProponente.getSelectedItem();

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeCategorias.getLastSelectedPathComponent();
                String categoria = (selectedNode != null) ? selectedNode.toString() : null;

                List<String> tiposSeleccionados = new ArrayList<>();
                for (JCheckBox cb : checkBoxesTiposRetorno) {
                    if (cb.isSelected()) {
                        tiposSeleccionados.add(cb.getText());
                    }
                }


                if (titulo.isEmpty() || descripcion.isEmpty() || lugar.isEmpty() || proponente == null || categoria == null) {
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

                PropuestaContr.crearPropuesta(titulo,descripcion,lugar,fechaPrevista,precioEntrada,montoNecesario,imagenBytes,proponente,categoria,tiposSeleccionados);

                JOptionPane.showMessageDialog(this,
                        "Propuesta creada correctamente",
                        "Alta de Propuesta",
                        JOptionPane.INFORMATION_MESSAGE);


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

    public List<String> listarTiposRetorno() {
        List<String> lista = new ArrayList<>();
        for (DTTipoRetorno t : DTTipoRetorno.values()) {
            lista.add(t.name());
        }
        return lista;
    }
}
