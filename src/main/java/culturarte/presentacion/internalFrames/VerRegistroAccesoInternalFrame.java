package culturarte.presentacion.internalFrames;

import culturarte.servicios.DTs.DTAccesoSitio;
import culturarte.servicios.interfaces.IUsuarioController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VerRegistroAccesoInternalFrame extends JInternalFrame {

    private final IUsuarioController UsuarioContr;
    private final JTable tablaAccesos;
    private final DefaultTableModel modeloTabla;
    private final JButton btnActualizar;
    private final JLabel lblTotal;

    public VerRegistroAccesoInternalFrame(IUsuarioController icu) {
        super("Registro de Accesos al Sitio", true, true, true, true);
        setSize(1000, 500);
        setLayout(new BorderLayout());

        UsuarioContr = icu;

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Tabla de Registro de Accesos");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(titulo, BorderLayout.WEST);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = crearBoton("Actualizar", new Color(40, 50, 70), Color.WHITE);
        btnActualizar.addActionListener(e -> cargarAccesos());
        panelBotones.add(btnActualizar);
        panelSuperior.add(panelBotones, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"#", "Fecha y Hora", "IP", "URL", "Browser", "Sistema Operativo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaAccesos = new JTable(modeloTabla);
        tablaAccesos.setDefaultEditor(Object.class, null);

        tablaAccesos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaAccesos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaAccesos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaAccesos.getColumnModel().getColumn(3).setPreferredWidth(300);
        tablaAccesos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaAccesos.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tablaAccesos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        lblTotal = new JLabel("Total de accesos: 0");
        lblTotal.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInferior.add(lblTotal);

        JLabel lblInfo = new JLabel("  |  Se mantienen hasta 10,000 accesos de los últimos 30 días");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        panelInferior.add(lblInfo);
        add(panelInferior, BorderLayout.SOUTH);

        cargarAccesos();
    }

    private void cargarAccesos() {
        try {
            btnActualizar.setEnabled(false);
            modeloTabla.setRowCount(0);
            List<DTAccesoSitio> accesos = UsuarioContr.devolverRegistroAccesos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            int numero = 1;
            for (DTAccesoSitio acceso : accesos) {
                Object[] fila = {
                        numero++,
                        acceso.getFechaHora().format(formatter),
                        acceso.getIp(),
                        acceso.getUrl(),
                        acceso.getBrowser(),
                        acceso.getSistemaOperativo()};
                modeloTabla.addRow(fila);
            }

            lblTotal.setText("Total de accesos: " + accesos.size());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los accesos: " +
                            e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnActualizar.setEnabled(true);
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
}