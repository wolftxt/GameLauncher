package tabs;

import UIUtils.DisplayText;
import java.util.Scanner;

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
