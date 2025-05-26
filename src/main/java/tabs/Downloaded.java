package tabs;

import components.BrowseCard;
import components.DisplayText;
import components.DownloadedCard;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import main.GameDownloadCallback;
import org.json.JSONArray;
import org.json.JSONObject;

public class Downloaded extends JPanel {

    public Downloaded(GameDownloadCallback callback) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        try {
            File newFile = new File(BrowseCard.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File parent = newFile.getParentFile();
            File games = new File(parent, "games");
            File jsonFile = new File(games, "DownloadedList.json");
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
                File screenshot = new File(folder, "screenshot.png");
                File executable = new File(folder, game.getString("title") + ".jar");

                BufferedImage image = ImageIO.read(screenshot);
                this.add(new DownloadedCard(image, game.getString("title"), game.getString("description"), executable, callback));
            }
        } catch (IOException | URISyntaxException ex) {
            this.add(new DisplayText("You havent downloaded any games yet!"));
        }
    }
}
