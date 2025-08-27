package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import culturarte.logica.DT.DTEstadoPropuesta;
import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IPropuestaController;

public class ConsultaPropuestasPorEstadoInternalFrame extends JInternalFrame {

    private JComboBox<DTEstadoPropuesta> comboEstados;
    private JButton btnConsultar;
    private JList<DTPropuesta> jListPropuestas;
    private JLabel lblTitulo;
    private JLabel lblDescripcion;
    private JLabel lblLugar;
    private JLabel lblFechaPrevista;
    private JLabel lblEstado;
    private JLabel lblImagen;
    private JLabel lblColaboradores;
    private JLabel lblMontoTotal;

    private IPropuestaController PropuestaContr;

    public ConsultaPropuestasPorEstadoInternalFrame(IPropuestaController icp) {
        super("Consulta de Propuestas por Estado", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        // Panel superior para selección de estado
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Estado:"));

        comboEstados = new JComboBox<>();
        cargarEstados();
        panelSuperior.add(comboEstados);

        btnConsultar = new JButton("Consultar");
        panelSuperior.add(btnConsultar);

        add(panelSuperior, BorderLayout.NORTH);

        // Lista de propuestas (lado izquierdo)
        jListPropuestas = new JList<>();
        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        scrollList.setPreferredSize(new Dimension(200, 0));
        add(scrollList, BorderLayout.WEST);

        // Panel de detalles (lado derecho)
        JPanel panelDetalles = new JPanel(new GridLayout(8, 2, 5, 5));

        panelDetalles.add(new JLabel("Título:"));
        lblTitulo = new JLabel();
        panelDetalles.add(lblTitulo);

        panelDetalles.add(new JLabel("Descripción:"));
        lblDescripcion = new JLabel();
        panelDetalles.add(lblDescripcion);

        panelDetalles.add(new JLabel("Lugar:"));
        lblLugar = new JLabel();
        panelDetalles.add(lblLugar);

        panelDetalles.add(new JLabel("Fecha Prevista:"));
        lblFechaPrevista = new JLabel();
        panelDetalles.add(lblFechaPrevista);

        panelDetalles.add(new JLabel("Estado:"));
        lblEstado = new JLabel();
        panelDetalles.add(lblEstado);

        panelDetalles.add(new JLabel("Imagen:"));
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        panelDetalles.add(lblImagen);

        panelDetalles.add(new JLabel("Colaboradores:"));
        lblColaboradores = new JLabel();
        panelDetalles.add(lblColaboradores);

        panelDetalles.add(new JLabel("Monto total recaudado:"));
        lblMontoTotal = new JLabel();
        panelDetalles.add(lblMontoTotal);

        add(panelDetalles, BorderLayout.CENTER);

        // Configurar eventos
        btnConsultar.addActionListener(e -> consultarPropuestasPorEstado());

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta p = jListPropuestas.getSelectedValue();
                if (p != null) {
                    mostrarDetalles(p);
                }
            }
        });
    }

    private void cargarEstados() {
        for (DTEstadoPropuesta estado : DTEstadoPropuesta.values()) {
            comboEstados.addItem(estado);
        }
    }

    private void consultarPropuestasPorEstado() {
        DTEstadoPropuesta estadoSeleccionado = (DTEstadoPropuesta) comboEstados.getSelectedItem();

        if (estadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estado", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<DTPropuesta> propuestas = PropuestaContr.devolverPropuestasPorEstado(estadoSeleccionado);

            DefaultListModel<DTPropuesta> modeloLista = new DefaultListModel<>();
            for (DTPropuesta p : propuestas) {
                modeloLista.addElement(p);
            }
            jListPropuestas.setModel(modeloLista);

            // Limpiar detalles
            limpiarDetalles();

            if (propuestas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay propuestas en estado " + estadoSeleccionado.name(),
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al consultar propuestas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalles(DTPropuesta p) {
        lblTitulo.setText(p.getTitulo());
        lblDescripcion.setText(p.getDescripcion());
        lblLugar.setText(p.getLugar());
        lblFechaPrevista.setText(p.getFechaPrevista() != null ? p.getFechaPrevista().toString() : "");
        lblEstado.setText(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Sin estado");

        if (p.getImagen() != null) {
            ImageIcon icon = new ImageIcon(p.getImagen());
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } else {
            lblImagen.setIcon(null);
        }

        String colaboradores = p.getColaboraciones()
                .stream()
                .map(c -> c.getColaborador().getNickname())
                .collect(Collectors.joining(", "));
        lblColaboradores.setText(colaboradores);

        double montoTotal = p.getColaboraciones()
                .stream()
                .mapToDouble(c -> c.getMonto() != null ? c.getMonto() : 0)
                .sum();
        lblMontoTotal.setText(String.valueOf(montoTotal));
    }

    private void limpiarDetalles() {
        lblTitulo.setText("");
        lblDescripcion.setText("");
        lblLugar.setText("");
        lblFechaPrevista.setText("");
        lblEstado.setText("");
        lblImagen.setIcon(null);
        lblColaboradores.setText("");
        lblMontoTotal.setText("");
    }
}