package cards;

import UIUtils.DisplayText;
import UIUtils.UIsettings;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class AbstractCard extends JPanel {

    protected JPanel buttons;

    public AbstractCard(BufferedImage image, String title, String description) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Image scaled = image.getScaledInstance(UIsettings.IMAGE_SIZE.width, UIsettings.IMAGE_SIZE.height, Image.SCALE_SMOOTH);

        JLabel img = new JLabel(new ImageIcon(scaled));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setFont(UIsettings.NAVBAR_FONT);

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
