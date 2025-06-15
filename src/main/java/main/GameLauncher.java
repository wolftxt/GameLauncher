package main;

import UIUtils.UISettings;
import java.awt.BorderLayout;
import java.awt.Taskbar;
import javax.swing.ImageIcon;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

/**
 * Main class of the GameLauncher program. Sets up FlatDarkLaf for looks and
 * feels, icon, taskbar icon (if supported by the operating system). Initialises
 * the 2 parts of the GUI (the navbar and the page). Uses BorderLayout to
 * organise these 2 components.
 *
 * @author davidwolf
 */
public class GameLauncher extends javax.swing.JFrame {

    public GameLauncher() {
        this.setTitle("Game Launcher");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(GameLauncher.class.getResource("/icon.png"));
        this.setIconImage(icon.getImage());
        try {
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("The OS does not support setting the dock icon.");
        }
        this.setSize(UISettings.getInstance().DEFAULT_WINDOW_SIZE);
        initComponents();
    }

    private void initComponents() {
        navbar1 = new main.Navbar();
        pageContainer1 = new main.PageContainer();

        navbar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                navbar1MouseReleased(evt);
            }
        });
        this.setLayout(new BorderLayout());

        this.add(navbar1, BorderLayout.NORTH);
        this.add(pageContainer1, BorderLayout.CENTER);
    }

    private void navbar1MouseReleased(java.awt.event.MouseEvent evt) {
        pageContainer1.showCard(navbar1.click(evt.getX()));
    }

    public static void main(String args[]) {
        FlatDarkLaf.setup();
        FlatLaf.setUseNativeWindowDecorations(true);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameLauncher().setVisible(true);
            }
        });
    }

    private main.Navbar navbar1;
    private main.PageContainer pageContainer1;
}
