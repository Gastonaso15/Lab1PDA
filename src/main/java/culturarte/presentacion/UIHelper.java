package culturarte.presentacion;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class UIHelper {

    // Retorna true si **algún** campo está vacío o nulo
    public static boolean hayCamposVacios(String... campos) {
        for (String c : campos) {
            if (c == null || c.trim().isEmpty()) return true;
        }
        return false;
    }

    // Limpia campos de texto
    public static void limpiarCampos(JTextField... campos) {
        for (JTextField tf : campos) tf.setText("");
    }

    // Limpia labels
    public static void limpiarLabels(JLabel... labels) {
        for (JLabel lbl : labels) lbl.setText("");
    }

    // Convierte texto a LocalDate
    public static LocalDate parseFecha(String fechaTexto) throws DateTimeParseException {
        return LocalDate.parse(fechaTexto.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    // Crea un panel con label + campo de entrada
    public static JPanel crearCampoConLabel(String label, JComponent campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(label));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(5));
        return panel;
    }

    // Panel con JTextField y botón para seleccionar imagen
    public static JPanel crearCampoImagen(JInternalFrame frame, JTextField tfImagen) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Imagen (Opcional):"), BorderLayout.WEST);
        tfImagen.setEditable(false);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen(frame, tfImagen));
        panel.add(tfImagen, BorderLayout.CENTER);
        panel.add(btnSeleccionarImagen, BorderLayout.EAST);
        return panel;
    }

    // Copia la imagen a carpeta destino y devuelve la ruta final
    public static String procesarImagen(String rutaSeleccionada, String carpetaDestino) throws IOException {
        if (rutaSeleccionada == null || rutaSeleccionada.isEmpty()) return null;

        File carpeta = new File(carpetaDestino);
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta: " + carpeta.getAbsolutePath());
        }

        String nombreArchivo = UUID.randomUUID() + "_" + new File(rutaSeleccionada).getName();
        File destino = new File(carpeta, nombreArchivo);

        Files.copy(new File(rutaSeleccionada).toPath(),
                destino.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        return destino.getAbsolutePath();
    }

    // Selecciona imagen y pone la ruta en el JTextField
    public static void seleccionarImagen(JInternalFrame frame, JTextField tfImagen) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            tfImagen.setText(archivo.getAbsolutePath());
        }
    }

    // Agrega varios campos a un panel evitando duplicación
    public static void agregarCamposAlPanel(JPanel panel, Object[][] campos) {
        for (Object[] par : campos) {
            String label = (String) par[0];
            JComponent campo = (JComponent) par[1];
            panel.add(crearCampoConLabel(label, campo));
        }
    }

    // Asigna una fuente a varios labels
    public static void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel lbl : labels) {
            lbl.setFont(font);
        }
    }

    // Agrega varios labels a un panel
    public static void agregarLabels(JPanel panel, JLabel... labels) {
        for (JLabel lbl : labels) {
            panel.add(lbl);
        }
    }

}
