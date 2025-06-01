package cards;

import UIUtils.UIsettings;
import java.awt.image.BufferedImage;
import java.net.*;
import javax.swing.*;
import main.IOUtils;

import main.TabUpdate;

public class BrowseCard extends AbstractCard {

    public BrowseCard(BufferedImage image, String title, String description, URL executableUrl, TabUpdate callback) {
        super(image, title, description);
        JButton button = new JButton("Download");
        button.setFont(UIsettings.CARD_TITLE_FONT);
        button.addActionListener(e -> {
            Thread.ofVirtual().start(() -> {
                IOUtils.downloadGame(image, title, description, executableUrl, callback);
            });
        });
        buttons.add(button);
    }
}
