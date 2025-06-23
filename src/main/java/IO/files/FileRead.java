package IO.files;

import UI.UIUtils.UISettings;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.json.JSONArray;

/**
 * A class used to read files saved by FileWrite allowing UI settings and
 * downloaded games to be saved.
 *
 * @author davidwolf
 */
public class FileRead {

    public static JSONArray readJSON() throws FileNotFoundException {
        File jsonFile = FileNavigation.getJSONFile();
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(jsonFile);
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        sc.close();
        return new JSONArray(sb.toString());
    }

    public static BufferedImage readImage(String title) throws IOException {
        File imageFile = FileNavigation.getScreenshotFile(title);
        return ImageIO.read(imageFile);
    }

    public static UISettings loadSettings() {
        UISettings result = null;
        File file = FileNavigation.getSettingsFile();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            result = (UISettings) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Failed to load settings. The file is probably missing");
        }
        return result;
    }

}
