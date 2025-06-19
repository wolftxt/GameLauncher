package cards;

import IO.FileIO;
import UIUtils.UISettings;
import java.awt.image.BufferedImage;
import javax.swing.*;

import main.TabUpdate;

/**
 * AbstractCard extension used for cards in the browse section. Adds a download
 * button to the card to download games.
 *
 * @author davidwolf
 */
public class BrowseCard extends AbstractCard {

    public BrowseCard(BufferedImage image, String title, String description, String executableUrl, TabUpdate callback) {
        super(image, title, description);
        JButton button = new JButton("Download");
        button.setFont(UISettings.getInstance().CARD_TITLE_FONT);
        button.addActionListener(e -> {
            Thread.ofVirtual().start(() -> {
                FileIO.downloadGame(image, title, description, executableUrl);
                callback.addCard(2); // callback to update the Downloaded panel
            });
        });
        buttons.add(button);
    }
}
