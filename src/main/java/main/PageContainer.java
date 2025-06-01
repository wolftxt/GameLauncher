package main;

import UIUtils.DisplayText;
import UIUtils.UIsettings;
import java.awt.CardLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tabs.Browse;
import tabs.Downloaded;
import tabs.Info;
import tabs.Settings;

public class PageContainer extends JPanel implements TabUpdate {

    private final String[] TABS = Navbar.TABS;
    private final CardLayout cardlayout;
    private int selected = 0;

    public PageContainer() {
        cardlayout = new CardLayout(UIsettings.TAB_MARGIN.width, UIsettings.TAB_MARGIN.height);
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
            if (Thread.currentThread().isInterrupted()) {
                return; // Ensure the message isn't overriden
            }
            JScrollPane scroll = new JScrollPane(card);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.getVerticalScrollBar().setUnitIncrement(UIsettings.SCROLL_SPEED);
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
