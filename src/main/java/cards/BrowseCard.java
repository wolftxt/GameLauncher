package cards;

import UIUtils.UISettings;
import java.awt.image.BufferedImage;
import java.net.*;
import javax.swing.*;
import main.IOUtils;

import main.TabUpdate;

/**
 * AbstractCard extension used for cards in the browse section. Adds a download
 * button to the card to download games.
 *
 * @author davidwolf
 */
public class BrowseCard extends AbstractCard {

    public BrowseCard(BufferedImage image, String title, String description, URL executableUrl, TabUpdate callback) {
        super(image, title, description);
        JButton button = new JButton("Download");
        button.setFont(UISettings.getInstance().CARD_TITLE_FONT);
        button.addActionListener(e -> {
            Thread.ofVirtual().start(() -> {
                IOUtils.downloadGame(image, title, description, executableUrl, callback);
            });
        });
        buttons.add(button);
    }
}
