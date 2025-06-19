package UI.cards;

import IO.files.FileNavigation;
import UI.UIUtils.UISettings;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import IO.files.FileWrite;
import UI.main.TabUpdate;

/**
 * AbstractCard extension used for cards in the downloaded section. Adds a play
 * and uninstall button.
 *
 * @author davidwolf
 */
public class DownloadedCard extends AbstractCard {

    public DownloadedCard(String title, String description, TabUpdate callback) throws IOException {
        super(null, title, description);
        JButton uninstall = new JButton("Uninstall");
        uninstall.setFont(UISettings.getInstance().CARD_TITLE_FONT);
        uninstall.setMaximumSize(uninstall.getPreferredSize());
        uninstall.addActionListener(e -> {
            FileWrite.uninstall(title);
            callback.addCard(2); // Update the Downloaded Panel
        });
        JButton play = new JButton("Play");
        play.setFont(UISettings.getInstance().CARD_TITLE_FONT);
        play.addActionListener(e -> {
            launchGame(title);
        });
        buttons.add(uninstall);
        buttons.add(play);
    }

    private void launchGame(String title) {
        try {
            File executable = FileNavigation.getExecutableFile(title);
            Runtime.getRuntime().exec("java -jar " + executable.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
