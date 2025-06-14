package main;

import java.io.File;
import cards.BrowseCard;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class IOUtils {

    private static final String GAME_LIST_URL = "https://raw.githubusercontent.com/wolftxt/GameLauncher/refs/heads/master/GameList.json";

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

    public static String getGameList(TabUpdate callback) throws InterruptedException {
        String result = null;
        boolean found = false;
        int timeLength = 1;
        while (!found) {
            try {
                URL url = new URL(GAME_LIST_URL);
                InputStream is = url.openStream();
                Scanner sc = new Scanner(is).useDelimiter("\\A");
                result = sc.hasNext() ? sc.next() : "";
                sc.close();
                is.close();
                found = true;
            } catch (IOException ex) {
                int time = timeLength;
                timeLength *= 2;
                while (time > 0) {
                    callback.setMessage(1, "Unable to load game list from " + GAME_LIST_URL + "\nTrying again in " + time + " seconds");
                    Thread.sleep(1000);
                    time--;
                }
            }
        }
        return result;
    }

    public static void downloadGame(BufferedImage image, String title, String description, URL executableUrl, TabUpdate callback) {
        try {
            File games = getGamesFolder();
            File gameFolder = new File(games, title);
            if (!gameFolder.exists()) {
                gameFolder.mkdirs();
            }
            if (!appendToJSON(games, title, description)) {
                return;
            }

            File screenshotFile = new File(gameFolder, "screenshot.png");
            ImageIO.write(image, "PNG", screenshotFile);

            Path path = new File(gameFolder, title + ".jar").toPath();
            BufferedInputStream in = new BufferedInputStream(executableUrl.openStream());
            BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            in.close();

            callback.addCard(2); // callback to update the Downloaded panel
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Wasn't able to download game " + title + ". You probable aren't connected to the internet.");
        }
    }

    private static boolean appendToJSON(File games, String title, String description) {
        JSONArray json;
        File file = new File(games, JSON_FILE_NAME);
        try {
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(file);
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
            FileWriter fileOut = new FileWriter(file);
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
