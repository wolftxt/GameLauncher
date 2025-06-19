package IO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import javax.imageio.ImageIO;
import main.TabUpdate;

import org.json.JSONArray;

public class IORemote {

    private static final String GAME_LIST_URL = "https://raw.githubusercontent.com/wolftxt/GameLauncher/refs/heads/master/GameList.json";

    public static JSONArray getGameList(TabUpdate callback) throws InterruptedException, URISyntaxException {
        String result = null;
        boolean found = false;
        int timeLength = 1;
        while (!found) {
            try {
                URL url = new URI(GAME_LIST_URL).toURL();
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
        return new JSONArray(result);
    }

    public static BufferedImage getImage(String url) throws IOException, URISyntaxException {
        URL screenshotUrl = new URI(url).toURL();
        return ImageIO.read(screenshotUrl);
    }

    public static void downloadJar(String executableURL, Path path) throws IOException, URISyntaxException {
        URL url = new URI(executableURL).toURL();
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        in.close();
    }
}
