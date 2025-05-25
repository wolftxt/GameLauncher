package components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import main.Navbar;

public class Card extends JPanel {

    private static final int WIDTH = 400;

    public Card(BufferedImage image, String title, String description, URL executableUrl) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Image scaled = image.getScaledInstance(WIDTH, WIDTH * 10 / 16, Image.SCALE_SMOOTH); // Images are 16:10

        JLabel img = new JLabel(new ImageIcon(scaled));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setFont(Navbar.FONT);
        JButton button = new JButton("Download");
        button.setFont(new Font("ButtonFont", Font.PLAIN, Navbar.FONT.getSize()));
        button.addActionListener(e -> {
            downloadGame(image, title, description, executableUrl);
        });
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(heading, BorderLayout.CENTER);
        wrapper.add(button, BorderLayout.EAST);

        DisplayText desc = new DisplayText(description);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(img);
        this.add(wrapper);
        this.add(desc);
    }

    private void downloadGame(BufferedImage image, String title, String description, URL executableUrl) {
        Thread.ofVirtual().start(() -> {
            try {
                File newFile = new File(Card.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                File parent = newFile.getParentFile();
                File games = new File(parent, "games");
                File gameFolder = new File(games, title);
                if (!gameFolder.exists()) {
                    gameFolder.mkdirs();
                }

                File screenshotFile = new File(gameFolder, "screenshot.png");
                ImageIO.write(image, "PNG", screenshotFile);

                Path path = new File(gameFolder, title + ".jar").toPath();
                InputStream in = executableUrl.openStream();
                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
    }

}
