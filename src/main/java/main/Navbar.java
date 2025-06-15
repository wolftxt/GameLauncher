package main;

import UIUtils.UISettings;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * A simple navbar with a underline to highlight the selected tab.
 *
 * @author davidwolf
 */
public class Navbar extends JComponent {

    public static final String[] TABS = {"Info", "Browse", "Downloaded", "Settings"};
    private int selected = 0;

    public Navbar() {
        this.setPreferredSize(UISettings.getInstance().NAVBAR_DEFAULT_SIZE);
    }

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
        UISettings settings = UISettings.getInstance();
        g.setColor(settings.NAVBAR_BACKGROUND_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(settings.NAVBAR_TEXT_COLOR);
        int s = this.getWidth() / TABS.length;
        g.setFont(settings.NAVBAR_FONT);
        for (int i = 0; i < TABS.length; i++) {
            g.drawString(TABS[i], s * i + 5, this.getHeight() / 2 + 5);
            if (i == selected) {
                g.fillRect(s * i, this.getHeight() - 4, s, 3);
            }
        }
    }
}
