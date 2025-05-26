package components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import main.Navbar;

public class DownloadedCard extends AbstractCard {

    public DownloadedCard(BufferedImage image, String title, String description, File executable) {
        super(image, title, description);
        JButton button = new JButton("Play");
        button.setFont(new Font("ButtonFont", Font.PLAIN, Navbar.FONT.getSize()));
        button.addActionListener(e -> {
            launchGame(executable);
        });
        wrapper.add(button, BorderLayout.EAST);
    }

    private void launchGame(File executable) {
        try {
            Runtime.getRuntime().exec("java -jar " + executable.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
