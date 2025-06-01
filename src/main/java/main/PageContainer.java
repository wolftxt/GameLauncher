package main;

import UIUtils.DisplayText;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tabs.Browse;
import tabs.Downloaded;
import tabs.Info;
import tabs.Settings;

public class PageContainer extends JPanel implements TabUpdate {

    public static final Color BACKGROUND_COLOR = new Color(60, 60, 60);
    public static final Color TEXT_COLOR = new Color(230, 230, 230);
    public static final Font FONT = new Font("Page font", Font.PLAIN, 14);

    private final String[] TABS = Navbar.TABS;
    private final CardLayout cardlayout;
    private int selected = 0;

    public PageContainer() {
        cardlayout = new CardLayout(10, 10);
        this.setLayout(cardlayout);
        for (int i = 0; i < TABS.length; i++) {
            setMessage(i, "Loading...");
        }
        for (int i = 0; i < TABS.length; i++) {
            addCard(i);
        }
    }

    public void showCard(int index) {
        selected = index;
        cardlayout.show(this, TABS[index]);
    }

    @Override
    public void addCard(int index) {
        Thread.ofVirtual().start(() -> {
            JComponent card;
            switch (index) {
                case 0 -> {
                    card = new Info();
                }
                case 1 -> {
                    card = new Browse(this);
                }
                case 2 -> {
                    card = new Downloaded(this);
                }
                case 3 -> {
                    card = new Settings();
                }
                default -> {
                    throw new IllegalArgumentException("Index must be between 0 and 3 (both inclusive)");
                }
            }
            JScrollPane scroll = new JScrollPane(card);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.getVerticalScrollBar().setUnitIncrement(16);
            this.add(scroll, TABS[index]);
            if (selected == index) {
                showCard(index);
            }
        });
    }

    @Override
    public void setMessage(int index, String message) {
        add(new DisplayText(message), TABS[index]);
        if (selected == index) {
            showCard(index);
        }
    }
}
