package main;

import components.DisplayText;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

import tabs.Browse;
import tabs.Downloaded;
import tabs.Info;
import tabs.Settings;

public class PageContainer extends JPanel implements GameDownloadCallback {

    public static final Color BACKGROUND_COLOR = new Color(60, 60, 60);
    public static final Color TEXT_COLOR = new Color(230, 230, 230);
    public static final Font FONT = new Font("Page font", Font.PLAIN, 14);

    private final String[] TABS = Navbar.TABS;
    private CardLayout cardlayout;
    private int selected = 0;

    public PageContainer() {
        cardlayout = new CardLayout(10, 10);
        this.setLayout(cardlayout);
        loadPages();
    }

    private void loadPages() {
        add(new Info(), TABS[0]); // outside of thread to load faster (info is the default page)
        add(new DisplayText("Loading..."), TABS[1]);
        add(new Downloaded(), TABS[2]);
        add(new Settings(), TABS[3]);
        Thread.ofVirtual().start(() -> {
            add(new Browse(this), TABS[1]); // Loaded inside of a thread because it makes a network request
            if (selected == 1) {
                show(1);
            }
        });
    }

    public void show(int index) {
        selected = index;
        cardlayout.show(this, TABS[index]);
    }

    @Override
    public void update() {
        add(new Downloaded(), TABS[2]);
        if (selected == 2) {
            show(2);
        }
    }
}
