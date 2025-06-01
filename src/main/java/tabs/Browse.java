package tabs;

import components.BrowseCard;
import components.WrapLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import main.GameDownloadCallback;
import main.IOUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class Browse extends JPanel {

    public Browse(GameDownloadCallback callback) {
        this.setLayout(new WrapLayout(FlowLayout.CENTER, 20, 20));
        try {
            String gameList = IOUtils.getGameList(callback);
            JSONArray json = new JSONArray(gameList);
            for (int i = 0; i < json.length(); i++) {
                JSONObject curr = json.getJSONObject(i);
                String title = curr.getString("title");
                String description = curr.getString("description");
                URL screenshotUrl = new URL(curr.getString("screenshotUrl"));
                BufferedImage image = ImageIO.read(screenshotUrl);
                URL executableUrl = new URL(curr.getString("executableUrl"));
                this.add(new BrowseCard(image, title, description, executableUrl, callback));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
