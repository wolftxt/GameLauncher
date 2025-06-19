package UI.main;

import UI.UIUtils.DisplayText;
import UI.UIUtils.UISettings;
import java.awt.CardLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import UI.tabs.Browse;
import UI.tabs.Downloaded;
import UI.tabs.Info;
import UI.tabs.Settings;

/**
 * The main JPanel of the program used to hold all the tabs and enable
 * scrolling. Uses CardLayout to switch between the tabs. Initially sets all
 * tabs to a loading text while it loads the contents of the tabs in a virtual
 * thread.
 *
 * @author davidwolf
 */
public class PageContainer extends JPanel implements TabUpdate {

    private final String[] TABS = Navbar.TABS;
    private final CardLayout cardlayout;
    private int selected = 0;

    public PageContainer() {
        UISettings settings = UISettings.getInstance();
        cardlayout = new CardLayout(settings.TAB_MARGIN.width, settings.TAB_MARGIN.height);
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
                    card = new Settings(this);
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
            scroll.getVerticalScrollBar().setUnitIncrement(UISettings.getInstance().SCROLL_SPEED);
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
