package components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import javax.swing.*;
import main.GameDownloadCallback;
import main.IOUtils;

import main.Navbar;

public class BrowseCard extends AbstractCard {

    public BrowseCard(BufferedImage image, String title, String description, URL executableUrl, GameDownloadCallback callback) {
        super(image, title, description);
        JButton button = new JButton("Download");
        button.setFont(new Font("ButtonFont", Font.PLAIN, Navbar.FONT.getSize()));
        button.addActionListener(e -> {
            Thread.ofVirtual().start(() -> {
                IOUtils.downloadGame(image, title, description, executableUrl, callback);
            });
        });
        wrapper.add(button, BorderLayout.EAST);
    }
}
