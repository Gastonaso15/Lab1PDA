package culturarte.presentacion.helpers;

import javax.swing.*;
import java.awt.*;

public class ImagenUIHelper {

    public static class ImagenPanel extends JPanel {
        private Image imagen;

        public void setImagen(String ruta) {
            if (ruta != null && !ruta.isEmpty()) {
                ImageIcon icon = new ImageIcon(ruta);
                imagen = icon.getImage();
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