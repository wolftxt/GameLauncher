package tabs;

import components.Card;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

public class Browse extends JPanel {

    public Browse() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        try {
            URL url = new URL("https://raw.githubusercontent.com/wolftxt/GameLauncher/refs/heads/master/GameList.json");
            InputStream is = url.openStream();
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String s = scanner.hasNext() ? scanner.next() : "";

            JSONArray json = new JSONArray(s);
            for (int i = 0; i < json.length(); i++) {
                JSONObject cur = json.getJSONObject(i);
                String title = cur.getString("title");
                String description = cur.getString("description");
                URL screenshotUrl = new URL(cur.getString("screenshotUrl"));
                BufferedImage image = ImageIO.read(screenshotUrl);
                URL executableUrl = new URL(cur.getString("executableUrl"));
                this.add(new Card(image, title, description, executableUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
