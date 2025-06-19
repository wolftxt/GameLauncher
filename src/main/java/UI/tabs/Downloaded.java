package UI.tabs;

import UI.UIUtils.UISettings;
import UI.cards.DownloadedCard;
import UI.UIUtils.WrapLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import javax.swing.JPanel;
import IO.files.FileRead;

import org.json.JSONArray;
import org.json.JSONObject;

import UI.main.TabUpdate;

/**
 * The downloaded tab of the program. Holds all downloaded cards.
 *
 * @author davidwolf
 */
public class Downloaded extends JPanel {

    public Downloaded(TabUpdate callback) {
        UISettings settings = UISettings.getInstance();
        this.setLayout(new WrapLayout(FlowLayout.CENTER, settings.TAB_PADDING.width, settings.TAB_PADDING.height));
        try {
            JSONArray json = FileRead.readJSON();
            for (int i = 0; i < json.length(); i++) {
                JSONObject game = json.getJSONObject(i);
                String title = game.getString("title");
                String description = game.getString("description");
                this.add(new DownloadedCard(title, description, callback));
            }
        } catch (IOException ex) {
            callback.setMessage(2, "You haven't downloaded any games yet!");
            Thread.currentThread().interrupt(); // Ensure the message isn't overriden
        }
    }
}
