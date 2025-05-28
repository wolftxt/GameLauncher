package tabs;

import components.DisplayText;
import java.awt.BorderLayout;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Info extends JPanel {
    
    public Info() {
        this.setLayout(new BorderLayout());
        Scanner sc = new Scanner(Info.class.getResourceAsStream("/info.txt"));
        sc.useDelimiter("\\A");
        DisplayText displayText = new DisplayText(sc.next());
        JScrollPane scrollPane = new JScrollPane(displayText);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
