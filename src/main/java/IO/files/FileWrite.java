package IO.files;

import IO.IORemote;
import UI.UIUtils.UISettings;
import java.io.File;
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
public class FileWrite {

    public static void downloadGame(BufferedImage image, String title, String description, String executableURL) {
        try {
            File jsonFile = FileNavigation.getJSONFile();
            if (!FileWrite.appendToJSON(jsonFile, title, description)) {
                return;
            }
            File gameFolder = FileNavigation.getGameFolder(title);
            gameFolder.mkdirs();

            File screenshotFile = FileNavigation.getScreenshotFile(title);
            ImageIO.write(image, "PNG", screenshotFile);

            Path path = FileNavigation.getExecutableFile(title).toPath();
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
            File gameFolder = FileNavigation.getGameFolder(title);
            File jsonFile = FileNavigation.getJSONFile();

            deleteFiles(gameFolder);

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
            if (json.isEmpty()) {
                jsonFile.delete();
                return;
            }
            FileWriter fw = new FileWriter(jsonFile);
            fw.write(json.toString(4));
            fw.close();
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

    public static void saveSettings() throws IOException {
        File file = FileNavigation.getSettingsFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(UISettings.getInstance());
        oos.close();
    }

}
