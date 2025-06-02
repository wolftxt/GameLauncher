package UIUtils;

import javax.swing.JTextArea;

public class DisplayText extends JTextArea {

    public DisplayText(String text) {
        super(text);
        this.setEditable(false);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setFocusable(false);
        this.setFont(UISettings.getInstance().PAGE_FONT);
    }

}
