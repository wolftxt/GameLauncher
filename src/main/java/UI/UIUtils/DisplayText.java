package UI.UIUtils;

import javax.swing.JTextArea;

/**
 * JTextArea extension used only to display text, also allows text to wrap
 * properly. Used in the entire program to standardize how text looks.
 *
 * @author davidwolf
 */
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
