package main;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.BorderLayout;
import java.awt.Taskbar;
import javax.swing.ImageIcon;

public class GameLauncher extends javax.swing.JFrame {

    public GameLauncher() {
        initComponents();
    }

    private void initComponents() {
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

        this.setSize(800, 600);

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
        pageContainer1.show(navbar1.click(evt.getX()));
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
