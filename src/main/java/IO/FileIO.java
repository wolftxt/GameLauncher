package IO;

import java.io.File;
import cards.BrowseCard;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A class used for files IO operations.
 *
 * File IO: Creates a folder called "games" in the same directory as the .jar
 * file and saves and loads everything from that folder.
 *
 * @author davidwolf
 */
public class FileIO {

    public static final String SETTINGS_FILE_NAME = "settings.UISettings";

    public static final String JSON_FILE_NAME = "DownloadedList.json";
    public static final String SCREENSHOT_NAME = "screenshot.png";

    public static File getGamesFolder() {
        try {
            File newFile = new File(BrowseCard.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File parent = newFile.getParentFile();
            File games = new File(parent, "games");
            return games;
        } catch (URISyntaxException ex) {
            throw new RuntimeException("URISyntaxException", ex);
        }
    }

    public static void downloadGame(BufferedImage image, String title, String description, String executableURL) {
        try {
            File games = getGamesFolder();
            File gameFolder = new File(games, title);
            if (!gameFolder.exists()) {
                gameFolder.mkdirs();
            }
            File jsonFile = new File(games, JSON_FILE_NAME);
            if (!FileIO.appendToJSON(jsonFile, title, description)) {
                return;
            }

            File screenshotFile = new File(gameFolder, "screenshot.png");
            ImageIO.write(image, "PNG", screenshotFile);

            Path path = new File(gameFolder, title + ".jar").toPath();
            IORemote.downloadJar(executableURL, path);
        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(null, "Wasn't able to download game " + title + ". You probable aren't connected to the internet.");
        }
    }

    public static boolean appendToJSON(File jsonFile, String title, String description) {
        JSONArray json;
        try {
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(jsonFile);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            json = new JSONArray(sb.toString());
            sc.close();
        } catch (FileNotFoundException ex) {
            json = new JSONArray();
        }

        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            if (object.get("title").equals(title)) {
                return false;
            }
        }
        JSONObject newObj = new JSONObject();
        newObj.put("title", title);
        newObj.put("description", description);
        json.put(newObj);

        try {
            FileWriter fileOut = new FileWriter(jsonFile);
            fileOut.write(json.toString(4));
            fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static void uninstall(String title) {
        try {
            File games = getGamesFolder();
            File gameFolder = new File(games, title);

            File jsonFile = new File(games, JSON_FILE_NAME);
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
            FileWriter fw = new FileWriter(jsonFile);
            fw.write(json.toString(4));
            fw.close();
            deleteFiles(gameFolder);
            if (json.isEmpty()) {
                jsonFile.delete();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recursively deletes a folder and its contents. Same thing as:
     *
     * rm -rf *
     *
     * cd ..
     *
     * rmdir directoryName
     *
     * @param folder
     */
    private static void deleteFiles(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                deleteFiles(file);
            } else {
                file.delete();
            }
        }
        folder.delete();
    }

}
