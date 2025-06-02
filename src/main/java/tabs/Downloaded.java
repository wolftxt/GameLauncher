package tabs;

import UIUtils.UISettings;
import cards.DownloadedCard;
import UIUtils.WrapLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import main.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import main.TabUpdate;

public class Downloaded extends JPanel {

    public Downloaded(TabUpdate callback) {
        UISettings settings = UISettings.getInstance();
        this.setLayout(new WrapLayout(FlowLayout.CENTER, settings.TAB_PADDING.width, settings.TAB_PADDING.height));
        try {
            File games = IOUtils.getGamesFolder();
            File jsonFile = new File(games, IOUtils.JSON_FILE_NAME);
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(jsonFile);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            sc.close();
            JSONArray json = new JSONArray(sb.toString());
            for (int i = 0; i < json.length(); i++) {
                JSONObject game = json.getJSONObject(i);
                File folder = new File(games, game.getString("title"));
                File screenshot = new File(folder, IOUtils.SCREENSHOT_NAME);
                File executable = new File(folder, game.getString("title") + ".jar");

                BufferedImage image = ImageIO.read(screenshot);
                this.add(new DownloadedCard(image, game.getString("title"), game.getString("description"), executable, callback));
            }
        } catch (IOException ex) {
            callback.setMessage(2, "You haven't downloaded any games yet!");
            Thread.currentThread().interrupt(); // Ensure the message isn't overriden
        }
    }
}
