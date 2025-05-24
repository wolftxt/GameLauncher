package tabs;

import components.Card;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Browse extends JPanel {

    public Browse() {

        try {
            URL url = new URL("https://raw.githubusercontent.com/wolftxt/Tower-of-Hanoi/refs/heads/master/screenshot.png");
            BufferedImage img = ImageIO.read(url);
            this.add(new Card(img, "Tower-of-Hanoi", "A simple Tower of Hanoi program. It includes a brute force solve algorithm."));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
