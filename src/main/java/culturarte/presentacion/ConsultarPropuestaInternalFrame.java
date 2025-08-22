package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.controlador.IPropuestaController;

public class ConsultarPropuestaInternalFrame extends JInternalFrame {

    private JList<DTPropuesta> jListPropuestas;
    private JLabel lblTitulo;
    private JLabel lblDescripcion;
    private JLabel lblLugar;
    private JLabel lblFechaPrevista;
    private JLabel lblEstado;
    private JLabel lblImagen;
    private JLabel lblColaboradores;
    private JLabel lblMontoTotal;
    ;
    private IPropuestaController PropuestaContr;

    public ConsultarPropuestaInternalFrame(IPropuestaController icp) {
        super("Consultar Propuesta", true, true, true, true);
        setSize(700, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;

        jListPropuestas = new JList<>();
        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        scrollList.setPreferredSize(new Dimension(200, 0));
        add(scrollList, BorderLayout.WEST);

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

        cargarPropuestas();

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta p = jListPropuestas.getSelectedValue();
                if (p != null) {
                    mostrarDetalles(p);
                }
            }
        });
    }

    private void cargarPropuestas() {
        List<DTPropuesta> propuestas = PropuestaContr.devolverTodasLasPropuestas();
        DefaultListModel<DTPropuesta> modeloLista = new DefaultListModel<>();
        for (DTPropuesta p : propuestas) {
            modeloLista.addElement(p);
        }
        jListPropuestas.setModel(modeloLista);
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
}
