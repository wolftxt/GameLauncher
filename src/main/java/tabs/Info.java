package tabs;

import components.DisplayText;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Info extends JPanel {

    public Info() {
        this.setLayout(new BorderLayout());
        DisplayText displayText = new DisplayText("Welcome to game launcher! Use the browse and downloaded tabs to download and play games.");
        JScrollPane scrollPane = new JScrollPane(displayText);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
