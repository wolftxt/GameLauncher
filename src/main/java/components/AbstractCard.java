package components;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import main.Navbar;

public class AbstractCard extends JPanel {

    private static final int WIDTH = 400;
    protected JPanel wrapper;

    public AbstractCard(BufferedImage image, String title, String description) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Image scaled = image.getScaledInstance(WIDTH, WIDTH * 10 / 16, Image.SCALE_SMOOTH); // Images are 16:10

        JLabel img = new JLabel(new ImageIcon(scaled));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setFont(Navbar.FONT);
        wrapper = new JPanel(new BorderLayout());
        wrapper.add(heading, BorderLayout.CENTER);

        DisplayText desc = new DisplayText(description);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(img);
        this.add(wrapper);
        this.add(desc);
    }
}
