package IO.files;

import java.io.File;
import java.net.URISyntaxException;

/**
 * A class to handle file navigation allowing other classes to know exactly
 * where to read and write files.
 *
 * @author davidwolf
 */
public class FileNavigation {

    private static final String JSON_FILE_NAME = "DownloadedList.json";
    private static final String SCREENSHOT_NAME = "screenshot.png";
    private static final String SETTINGS_FILE_NAME = "settings.UISettings";

    public static File getGamesFolder() {
        try {
            File newFile = new File(FileNavigation.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File parent = newFile.getParentFile();
            File games = new File(parent, "games");
            if (!games.exists()) {
                games.mkdirs();
            }
            return games;
        } catch (URISyntaxException ex) {
            throw new RuntimeException("URISyntaxException", ex);
        }
    }

    public static File getJSONFile() {
        File file = new File(getGamesFolder(), JSON_FILE_NAME);
        return file;
    }

    public static File getSettingsFile() {
        File file = new File(getGamesFolder(), SETTINGS_FILE_NAME);
        return file;
    }

    public static File getGameFolder(String title) {
        File file = new File(getGamesFolder(), title);
        return file;
    }

    public static File getScreenshotFile(String title) {
        File file = new File(getGameFolder(title), SCREENSHOT_NAME);
        return file;
    }

    public static File getExecutableFile(String title) {
        File file = new File(getGameFolder(title), title + ".jar");
        return file;
    }
}
