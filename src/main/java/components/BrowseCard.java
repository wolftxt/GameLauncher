package components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;
import main.GameDownloadCallback;

import main.Navbar;
import org.json.JSONArray;
import org.json.JSONObject;

public class BrowseCard extends AbstractCard {

    public BrowseCard(BufferedImage image, String title, String description, URL executableUrl, GameDownloadCallback callback) {
        super(image, title, description);
        JButton button = new JButton("Download");
        button.setFont(new Font("ButtonFont", Font.PLAIN, Navbar.FONT.getSize()));
        button.addActionListener(e -> {
            downloadGame(image, title, description, executableUrl, callback);
        });
        wrapper.add(button, BorderLayout.EAST);
    }

    private void downloadGame(BufferedImage image, String title, String description, URL executableUrl, GameDownloadCallback callback) {
        Thread.ofVirtual().start(() -> {
            try {
                File newFile = new File(BrowseCard.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                File parent = newFile.getParentFile();
                File games = new File(parent, "games");
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
                InputStream in = executableUrl.openStream();
                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
                callback.update(); // callback to update the Downloaded panel
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
    }

    private boolean appendToJSON(File games, String title, String description) {
        JSONArray json;
        File file = new File(games, "DownloadedList.json");
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
}
