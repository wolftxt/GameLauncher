package UI.tabs;

import UI.UIUtils.UISettings;
import UI.cards.BrowseCard;
import UI.UIUtils.WrapLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import IO.IORemote;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;
import UI.main.TabUpdate;

/**
 * The browse tab of the program. Holds cards loaded from GitHub.
 *
 * @author davidwolf
 */
public class Browse extends JPanel {

    public Browse(TabUpdate callback) {
        UISettings settings = UISettings.getInstance();
        this.setLayout(new WrapLayout(FlowLayout.CENTER, settings.TAB_PADDING.width, settings.TAB_PADDING.height));
        try {
            JSONArray json = IORemote.getGameList(callback);
            for (int i = 0; i < json.length(); i++) {
                JSONObject curr = json.getJSONObject(i);
                String title = curr.getString("title");
                String description = curr.getString("description");
                BufferedImage image = IORemote.getImage(curr.getString("screenshotUrl"));
                String executableUrl = curr.getString("executableUrl");
                this.add(new BrowseCard(image, title, description, executableUrl, callback));
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
