package cards;

import UIUtils.UISettings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import IO.FileIO;
import main.TabUpdate;

/**
 * AbstractCard extension used for cards in the downloaded section. Adds a play
 * and uninstall button.
 *
 * @author davidwolf
 */
public class DownloadedCard extends AbstractCard {

    public DownloadedCard(BufferedImage image, String title, String description, File executable, TabUpdate callback) {
        super(image, title, description);
        JButton uninstall = new JButton("Uninstall");
        uninstall.setFont(UISettings.getInstance().CARD_TITLE_FONT);
        uninstall.setMaximumSize(uninstall.getPreferredSize());
        uninstall.addActionListener(e -> {
            FileIO.uninstall(title);
            callback.addCard(2); // Update the Downloaded Panel
        });
        JButton play = new JButton("Play");
        play.setFont(UISettings.getInstance().CARD_TITLE_FONT);
        play.addActionListener(e -> {
            launchGame(executable);
        });
        buttons.add(uninstall);
        buttons.add(play);
    }

    private void launchGame(File executable) {
        try {
            Runtime.getRuntime().exec("java -jar " + executable.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
