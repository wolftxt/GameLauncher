package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Navbar extends JComponent {

    private final Color BACKGROUND_COLOR = new Color(45, 45, 45);
    private final Color TEXT_COLOR = new Color(230, 230, 230);
    private final Font FONT = new Font("Navbar font", Font.BOLD, 18);
    
    public static final String[] TABS = {"Info", "Browse", "Downloaded", "Settings"};
    private int selected = 0;

    public int click(int x) {
        if (x < 0 || x >= this.getWidth()) {
            return selected;
        }
        int s = this.getWidth() / TABS.length;
        selected = x / s;
        this.repaint();
        return selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(TEXT_COLOR);
        int s = this.getWidth() / TABS.length;
        g.setFont(FONT);
        for (int i = 0; i < TABS.length; i++) {
            g.drawString(TABS[i], s * i + 5, this.getHeight() / 2 + 5);
            if (i == selected) {
                g.fillRect(s * i, this.getHeight() - 4, s, 3);
            }
        }
    }
}
