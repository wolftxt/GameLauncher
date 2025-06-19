package IO.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.json.JSONArray;

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

}
