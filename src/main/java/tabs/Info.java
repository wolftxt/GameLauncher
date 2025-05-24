package tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import main.PageContainer;

public class Info extends JPanel {

    private JScrollPane scrollPane;
    private JTextArea textArea;

    public Info() {
        this.setLayout(new BorderLayout());
        textArea = new JTextArea("Welcome to game launcher! Use the browse and downloaded tabs to download and play games.");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);
        textArea.setFont(PageContainer.FONT);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
