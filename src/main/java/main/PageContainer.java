package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

import tabs.Browse;
import tabs.Downloaded;
import tabs.Info;
import tabs.Settings;

public class PageContainer extends JPanel {

    public static final Color BACKGROUND_COLOR = new Color(60, 60, 60);
    public static final Color TEXT_COLOR = new Color(230, 230, 230);
    public static final Font FONT = new Font("Page font", Font.PLAIN, 14);

    private final String[] TABS = Navbar.TABS;
    private CardLayout cardlayout;

    public void init() {
        cardlayout = new CardLayout();
        this.setLayout(cardlayout);
        this.add(new Info(), TABS[0]);
        this.add(new Browse(), TABS[1]);
        this.add(new Downloaded(), TABS[2]);
        this.add(new Settings(), TABS[3]);
    }

    public void show(int index) {
        cardlayout.show(this, TABS[index]);
    }
}
