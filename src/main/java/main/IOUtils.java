package main;

import java.io.File;
import components.BrowseCard;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class IOUtils {

    public static void uninstall(String title) {
        try {
            File newFile = new File(BrowseCard.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File parent = newFile.getParentFile();
            File games = new File(parent, "games");
            File gameFolder = new File(games, title);
            
            File jsonFile = new File(games, "DownloadedList.json");
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(jsonFile);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            sc.close();
            JSONArray json = new JSONArray(sb.toString());
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                if (obj.getString("title").equals(title)) {
                    json.remove(i);
                    break;
                }
            }
            File screenshot = new File(gameFolder, "screenshot.png");
            File executable = new File(gameFolder, title + ".jar");
            screenshot.delete();
            executable.delete();
            gameFolder.delete();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            
        }
    }
}
