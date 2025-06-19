package UI.tabs;

import UI.UIUtils.DisplayText;
import java.util.Scanner;

/**
 * The info tab of the program. Displays basic info text about the program.
 *
 * @author davidwolf
 */
public class Info extends DisplayText {

    public Info() {
        super("");
        this.setText(getTextFromResources());
    }

    private String getTextFromResources() {
        Scanner sc = new Scanner(Info.class.getResourceAsStream("/info.txt"));
        sc.useDelimiter("\\A");
        return sc.next();
    }
}
