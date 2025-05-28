package components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.GameDownloadCallback;
import main.IOUtils;
import main.Navbar;

public class DownloadedCard extends AbstractCard {

    public DownloadedCard(BufferedImage image, String title, String description, File executable, GameDownloadCallback callback) {
        super(image, title, description);
        Font buttonFont = new Font("ButtonFont", Font.PLAIN, Navbar.FONT.getSize());
        JButton uninstall = new JButton("Uninstall");
        uninstall.setFont(buttonFont);
        uninstall.setMaximumSize(uninstall.getPreferredSize());
        uninstall.addActionListener(e -> {
                IOUtils.uninstall(title);
                callback.updateDownloaded();
        });
        JButton play = new JButton("Play");
        play.setFont(buttonFont);
        play.addActionListener(e -> {
            launchGame(executable);
        });
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttons.add(uninstall);
        buttons.add(play);
        wrapper.add(buttons, BorderLayout.EAST);
    }

    private void launchGame(File executable) {
        try {
            Runtime.getRuntime().exec("java -jar " + executable.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
