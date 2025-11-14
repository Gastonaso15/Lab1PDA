package culturarte.presentacion.internalFrames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import culturarte.servicios.DTs.DTCategoria;
import culturarte.servicios.interfaces.IPropuestaController;
import culturarte.servicios.interfaces.IUsuarioController;
import culturarte.servicios.DTs.DTTipoRetorno;
import culturarte.presentacion.helpers.CategoriaUIHelper;

public class AltaPropuestaInternalFrame extends JInternalFrame {

    private final JTextField tfTitulo;
    private final JTextField tfDescripcion;
    private final JTextField tfLugar;
    private final JTextField tfFechaPrevista;
    private final JTextField tfPrecioEntrada;
    private final JTextField tfMontoNecesario;
    private final JTextField tfImagenPath;
    private final JTree treeCategorias;
    private final List<JCheckBox> checkBoxesTiposRetorno;
    private final JComboBox<String> cbProponente;

    private final IPropuestaController PropuestaContr;

    public AltaPropuestaInternalFrame(IPropuestaController icp,IUsuarioController icu) {
        super("Alta de Propuesta", true, true, true, true);
        setSize(1100, 700);
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        PropuestaContr = icp;

        DefaultMutableTreeNode rootCategorias = new DefaultMutableTreeNode("Categorías");
        treeCategorias = new JTree(rootCategorias);
        treeCategorias.setShowsRootHandles(true);
        treeCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollTree = new JScrollPane(treeCategorias);
        scrollTree.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Seleccionar Categoría",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        scrollTree.setPreferredSize(new Dimension(280, 0));

        List<DTCategoria> categorias = PropuestaContr.devolverTodasLasCategorias();
        CategoriaUIHelper.cargarCategorias(treeCategorias, categorias);

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Datos de la Propuesta",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 70, 70)
        ));
        panelDatos.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        gridPanel.add(createLabel("Título:"), gbc);
        gbc.gridx = 1;
        tfTitulo = new JTextField(25);
        estilizarTextField(tfTitulo);
        gridPanel.add(tfTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gridPanel.add(createLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        tfDescripcion = new JTextField(25);
        estilizarTextField(tfDescripcion);
        gridPanel.add(tfDescripcion, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gridPanel.add(createLabel("Lugar:"), gbc);
        gbc.gridx = 1;
        tfLugar = new JTextField(25);
        estilizarTextField(tfLugar);
        gridPanel.add(tfLugar, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gridPanel.add(createLabel("Fecha Prevista:"), gbc);
        gbc.gridx = 1;
        JPanel fechaPanel = new JPanel(new BorderLayout(5, 0));
        fechaPanel.setBackground(Color.WHITE);
        tfFechaPrevista = new JTextField(15);
        estilizarTextField(tfFechaPrevista);
        fechaPanel.add(tfFechaPrevista, BorderLayout.CENTER);
        JLabel hintLabel = new JLabel("(yyyy-mm-dd)");
        hintLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        hintLabel.setForeground(new Color(120, 120, 120));
        fechaPanel.add(hintLabel, BorderLayout.EAST);
        gridPanel.add(fechaPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gridPanel.add(createLabel("Precio Entrada:"), gbc);
        gbc.gridx = 1;
        tfPrecioEntrada = new JTextField(25);
        estilizarTextField(tfPrecioEntrada);
        gridPanel.add(tfPrecioEntrada, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gridPanel.add(createLabel("Monto Necesario:"), gbc);
        gbc.gridx = 1;
        tfMontoNecesario = new JTextField(25);
        estilizarTextField(tfMontoNecesario);
        gridPanel.add(tfMontoNecesario, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gridPanel.add(createLabel("Imagen (Opcional):"), gbc);
        gbc.gridx = 1;
        JPanel imagenPanel = new JPanel(new BorderLayout(5, 5));
        imagenPanel.setBackground(Color.WHITE);
        tfImagenPath = new JTextField();
        tfImagenPath.setEditable(false);
        estilizarTextField(tfImagenPath);
        JButton btnSeleccionarImagen = crearBoton("Seleccionar", new Color(40, 50, 70), Color.WHITE);
        imagenPanel.add(tfImagenPath, BorderLayout.CENTER);
        imagenPanel.add(btnSeleccionarImagen, BorderLayout.EAST);
        gridPanel.add(imagenPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        gridPanel.add(createLabel("Proponente:"), gbc);
        gbc.gridx = 1;
        cbProponente = new JComboBox<>();
        cbProponente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbProponente.setBackground(Color.WHITE);
        cbProponente.setPreferredSize(new Dimension(200, 30));
        cbProponente.setMaximumRowCount(10);
        List<String> proponentes = icu.devolverNicknamesProponentes();
        for (String p : proponentes) {
            cbProponente.addItem(p);
        }
        gridPanel.add(cbProponente, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gridPanel.add(createLabel("Tipo(s) de Retorno:"), gbc);
        gbc.gridx = 1;
        JPanel panelCheckBoxes = new JPanel();
        panelCheckBoxes.setLayout(new BoxLayout(panelCheckBoxes, BoxLayout.Y_AXIS));
        panelCheckBoxes.setBackground(Color.WHITE);
        checkBoxesTiposRetorno = new ArrayList<>();
        List<String> tiposRetorno = listarTiposRetorno();
        for (String tipo : tiposRetorno) {
            JCheckBox checkBox = new JCheckBox(tipo);
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            checkBox.setBackground(Color.WHITE);
            checkBoxesTiposRetorno.add(checkBox);
            panelCheckBoxes.add(checkBox);
        }
        JScrollPane scrollTiposRetorno = new JScrollPane(panelCheckBoxes);
        scrollTiposRetorno.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollTiposRetorno.setPreferredSize(new Dimension(200, 100));
        gridPanel.add(scrollTiposRetorno, gbc);

        panelDatos.add(gridPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, panelDatos);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                tfImagenPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        botones.setBackground(new Color(245, 248, 250));
        botones.setBorder(new EmptyBorder(10, 0, 0, 0));
        JButton aceptar = crearBoton("Aceptar", new Color(40, 50, 70), Color.WHITE);
        JButton cancelar = crearBoton("Cancelar", new Color(60, 60, 60), Color.WHITE);
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

                if (titulo.isEmpty() || descripcion.isEmpty() || lugar.isEmpty() ||
                        tfFechaPrevista.getText().isEmpty() || tfPrecioEntrada.getText().isEmpty() ||
                        tfMontoNecesario.getText().isEmpty() || proponente == null || categoria == null ||
                        tiposSeleccionados.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Completar todos los campos obligatorios",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String rutaFinal = null;
                String rutaSeleccionada = tfImagenPath.getText().trim();

                if (!rutaSeleccionada.isEmpty()) {
                    try {
                        File carpeta = new File("uploads/propuestas/");
                        if (!carpeta.exists()) {
                            carpeta.mkdirs();
                        }

                        String nombreArchivo = UUID.randomUUID() + "_" + new File(rutaSeleccionada).getName();
                        File destino = new File(carpeta, nombreArchivo);

                        Files.copy(new File(rutaSeleccionada).toPath(),
                                destino.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);

                        rutaFinal = "uploads/propuestas/" + nombreArchivo;

                    } catch (IOException ioEx) {
                        JOptionPane.showMessageDialog(this,
                                "Error al guardar la imagen: " + ioEx.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                PropuestaContr.crearPropuesta(titulo,descripcion,lugar,fechaPrevista,precioEntrada,montoNecesario,rutaFinal,proponente,categoria,tiposSeleccionados);

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
                if (cbProponente.getItemCount() > 0) {
                    cbProponente.setSelectedIndex(0);
                }
                for (JCheckBox cb : checkBoxesTiposRetorno) {
                    cb.setSelected(false);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear propuesta: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private void estilizarTextField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(6, 10, 6, 10)
        ));
        if (field instanceof JTextField) {
            ((JTextField) field).setBackground(Color.WHITE);
            ((JTextField) field).setPreferredSize(new Dimension(200, 30));
        }
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setOpaque(true);
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setContentAreaFilled(true);
        boton.setPreferredSize(new Dimension(120, 35));
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(8, 20, 8, 20)
        ));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        return boton;
    }

    public List<String> listarTiposRetorno() {
        List<String> lista = new ArrayList<>();
        for (DTTipoRetorno t : DTTipoRetorno.values()) {
            lista.add(t.name());
        }
        return lista;
    }
}
