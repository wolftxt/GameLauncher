package cards;

import UIUtils.DisplayText;
import UIUtils.UISettings;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * An abstract class to nicely display an image, title and a description.
 * Extended by BrowseCard and DownloadedCard classes.
 *
 * @author davidwolf
 */
public abstract class AbstractCard extends JPanel {

    protected JPanel buttons;

    public AbstractCard(BufferedImage image, String title, String description) {
        UISettings settings = UISettings.getInstance();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Image scaled = image.getScaledInstance(settings.IMAGE_SIZE.width, settings.IMAGE_SIZE.height, Image.SCALE_SMOOTH);

        JLabel img = new JLabel(new ImageIcon(scaled));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setFont(settings.NAVBAR_FONT);

        buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(heading, BorderLayout.CENTER);
        wrapper.add(buttons, BorderLayout.EAST);

        DisplayText desc = new DisplayText(description);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(img);
        this.add(wrapper);
        this.add(desc);
    }
}
