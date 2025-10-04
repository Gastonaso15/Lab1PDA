package culturarte.presentacion.helpers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ImagenUIHelper {

    public static class ImagenPanel extends JPanel {
        private Image imagen;

        public void setImagen(String ruta) {
            if (ruta != null && !ruta.isEmpty()) {

                URL recurso = getClass().getResource("/" + ruta);
                if (recurso != null) {
                    ImageIcon icon = new ImageIcon(recurso);
                    imagen = icon.getImage();
                } else {
                    File archivoExterno = new File(System.getProperty("user.dir") + "/" + ruta);
                    if (archivoExterno.exists()) {
                        ImageIcon icon = new ImageIcon(archivoExterno.getAbsolutePath());
                        imagen = icon.getImage();
                    } else {
                        imagen = null;
                    }
                }
            } else {
                imagen = null;
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imgWidth = imagen.getWidth(this);
                int imgHeight = imagen.getHeight(this);

                double ratio = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
                int nuevoAncho = (int) (imgWidth * ratio);
                int nuevoAlto = (int) (imgHeight * ratio);

                int x = (panelWidth - nuevoAncho) / 2;
                int y = (panelHeight - nuevoAlto) / 2;

                g.drawImage(imagen, x, y, nuevoAncho, nuevoAlto, this);
            }
        }
    }
}