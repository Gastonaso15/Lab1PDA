package culturarte.presentacion.internalFrames;

import culturarte.logica.DTs.DTPropuesta;
import culturarte.logica.controladores.PropuestaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EvaluarPropuestaInternalFrame extends JInternalFrame {

    private final JTextArea txtDescripcion;
    private JTable tablaPropuestas;
    private JTextArea TextDescripcion;
    private JTextField txtTitulo, txtCategoria, txtEstado, txtProponente;
    private JRadioButton rbPublicar, rbCancelar;
    private JButton btnConfirmar, btnCerrar;

    private PropuestaController propuestaController;

    public EvaluarPropuestaInternalFrame(PropuestaController propuestaController) {

        super("Evaluar Propuesta", true, true, true, true);
        this.propuestaController = propuestaController;

        setSize(1000,500);
        setLayout(new BorderLayout());

        //Panel tabla
        JPanel panelTabla=new JPanel(new BorderLayout());
        tablaPropuestas=new JTable();
        JScrollPane scrollTabla=new JScrollPane(tablaPropuestas);
        panelTabla.add(scrollTabla,BorderLayout.CENTER);
        //panelTabla.add(btnConfirmar,BorderLayout.SOUTH);
        //panelTabla.add(btnCerrar,BorderLayout.EAST);
        add(panelTabla,BorderLayout.NORTH);

        //Panel detalle
        JPanel panelDetalle=new JPanel(new GridLayout(5,2));
        panelDetalle.add(new JLabel("Titulo:"));
        txtTitulo=new JTextField();
        txtTitulo.setEditable(false);
        panelDetalle.add(txtTitulo);

        panelDetalle.add(new JLabel("Proponente"));
        txtProponente=new JTextField();
        txtProponente.setEditable(false);
        panelDetalle.add(txtProponente);

        panelDetalle.add(new JLabel("Categoria:"));
        txtCategoria=new JTextField();
        txtCategoria.setEditable(false);
        panelDetalle.add(txtCategoria);

        panelDetalle.add(new JLabel("Estado:"));
        txtEstado=new JTextField();
        txtEstado.setEditable(false);
        panelDetalle.add(txtEstado);

        panelDetalle.add(new JLabel("Descripcion:"));
        txtDescripcion=new JTextArea(3,20);
        txtDescripcion.setEditable(false);
        panelDetalle.add(new JScrollPane(txtDescripcion));

        add(panelDetalle,BorderLayout.CENTER);

        //Panel acciones
        JPanel panelAcciones = new JPanel(new FlowLayout());
        rbPublicar = new JRadioButton("Publicar");
        rbCancelar = new JRadioButton("Cancelar");
        ButtonGroup group = new ButtonGroup();
        group.add(rbPublicar);
        group.add(rbCancelar);

        btnConfirmar = new JButton("Confirmar");
        btnCerrar = new JButton("Cancelar");

        panelAcciones.add(rbPublicar);
        panelAcciones.add(rbCancelar);
        panelAcciones.add(btnConfirmar);
        panelAcciones.add(btnCerrar);

        add(panelAcciones,BorderLayout.SOUTH);

        //Eventos
        tablaPropuestas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tablaPropuestas.getSelectedRow();
                if(row!=-1){
                    String titulo=tablaPropuestas.getValueAt(row,0).toString();
                    DTPropuesta p= propuestaController.getDTPropuesta(titulo);
                    if(p!=null){
                        txtTitulo.setText(p.getTitulo());
                        txtProponente.setText(p.getProponente());
                        txtCategoria.setText(p.getCategoria());
                        txtEstado.setText(p.getEstadoActual().toString());
                        txtDescripcion.setText(p.getDescripcion());
                    }
                }
            }
        });

        btnConfirmar.addActionListener(e->{
            int row = tablaPropuestas.getSelectedRow();
            if(row==-1){
                JOptionPane.showMessageDialog(this,"Seleccione una propuesta");
                return;
            }
            String titulo = (String)tablaPropuestas.getValueAt(row,0);
            boolean publicar = rbPublicar.isSelected();

            try{
                PropuestaController.evaluarPropuesta(titulo, publicar);
                JOptionPane.showMessageDialog(this,"Propuesta evaluada con exito");
                cargarPropuestas();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCerrar.addActionListener(e->dispose());

        cargarPropuestas();
    }
    private void cargarPropuestas(){
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Titulo","Proponente"},0);
        List<DTPropuesta> propuestas = propuestaController.getPropuestasIngresadas();
        for(DTPropuesta p : propuestas){
            modelo.addRow(new Object[]{p.getTitulo(), p.getProponente()});
        }
        tablaPropuestas.setModel(modelo);

    }
}