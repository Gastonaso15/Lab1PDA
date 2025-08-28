package culturarte.presentacion;

import culturarte.logica.DT.DTPropuesta;
import culturarte.logica.DT.DTTipoRetorno;
import culturarte.logica.controlador.IPropuestaController;
import culturarte.logica.controlador.IUsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistrarColaboracionInternalFrame extends JInternalFrame {

    private IPropuestaController PropuestaContr;
    private IUsuarioController UsuarioContr;

    private JList<DTPropuesta> jListPropuestas;
    private JTextField txtColaborador;
    private JTextField txtMonto;
    private JComboBox<DTTipoRetorno> comboRetorno;


    public RegistrarColaboracionInternalFrame(IPropuestaController icp, IUsuarioController icu) {
        super("Registrar Colaboracion a Propuesta", true, true, true, true);
        setSize(1200, 500);
        setLayout(new BorderLayout());

        PropuestaContr = icp;
        UsuarioContr = icu;

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        jListPropuestas = new JList<>();
        JScrollPane scrollList = new JScrollPane(jListPropuestas);
        scrollList.setPreferredSize(new Dimension(200, 0));
        add(scrollList, BorderLayout.WEST);

        panel.add(new JLabel("Colaborador (nickname):"));
        txtColaborador = new JTextField();
        panel.add(txtColaborador);

        panel.add(new JLabel("Monto:"));
        txtMonto = new JTextField();
        panel.add(txtMonto);

        panel.add(new JLabel("Tipo Retorno:"));
        comboRetorno = new JComboBox<>(DTTipoRetorno.values());
        panel.add(comboRetorno);

        JButton btnRegistrar = new JButton("Registrar");
        add(btnRegistrar);

        add(panel, BorderLayout.CENTER);

        cargarPropuestas();

        jListPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DTPropuesta p = jListPropuestas.getSelectedValue();
                if (p != null) {
                    //mostrarDetalles(p);
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            try {
                String nickname = txtColaborador.getText();
                Double monto = Double.parseDouble(txtMonto.getText());
                DTTipoRetorno retorno = (DTTipoRetorno) comboRetorno.getSelectedItem();

                //PropuestaContr.registrarColaboracion(seleccionada.getId(), nickname, monto, retorno);

                JOptionPane.showMessageDialog(this, "Colaboración registrada con éxito");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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

        jListPropuestas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DTPropuesta p) {
                    setText(p.getTitulo() + " (" + p.getDTProponente().getNickname() + ")");
                }
                return this;
            }
        });
    }
}
