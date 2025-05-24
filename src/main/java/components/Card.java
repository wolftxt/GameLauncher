package components;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Navbar;

public class Card extends JPanel {

    private static final int WIDTH = 300;
    private static final int PADDING = 10;

    public Card(BufferedImage image, String title, String description) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        Image scaled = image.getScaledInstance(WIDTH, WIDTH * 10 / 16, Image.SCALE_SMOOTH); // Images are 16:10
        
        JLabel img = new JLabel(new ImageIcon(scaled));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel heading = new JLabel(title);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setFont(Navbar.FONT);
        
        DisplayText desc = new DisplayText(description);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(img);
        this.add(heading);
        this.add(desc);
    }

}
