package UIUtils;

import javax.swing.JTextArea;
import main.PageContainer;

public class DisplayText extends JTextArea {

    public DisplayText(String text) {
        super(text);
        this.setEditable(false);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setFocusable(false);
        this.setFont(PageContainer.FONT);
    }

}
