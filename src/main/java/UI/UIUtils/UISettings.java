package UI.UIUtils;

import IO.files.FileNavigation;
import IO.files.FileRead;
import java.awt.*;
import java.io.*;

/**
 * A singleton class used to store UI constants such as colors, fonts, etc. Can
 * be loaded and saved to a file. Settings are saved in the FileWrite with
 * method calls from Settings.java in UI.tabs.
 *
 * @author davidwolf
 */
public class UISettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private static UISettings instance;

    public static UISettings getInstance() {
        if (instance == null) {
            instance = FileRead.loadSettings(); // Returns null if file not found
        }
        if (instance == null) {
            instance = new UISettings();
        }
        return instance;
    }

    // UI settings
    public Dimension DEFAULT_WINDOW_SIZE = new Dimension(1200, 800);

    public Color NAVBAR_BACKGROUND_COLOR = new Color(45, 45, 45);
    public Color NAVBAR_TEXT_COLOR = new Color(230, 230, 230);
    public Font NAVBAR_FONT = new Font("SansSerif", Font.BOLD, 18);
    public Dimension NAVBAR_DEFAULT_SIZE = new Dimension(70, 70);

    public Dimension TAB_MARGIN = new Dimension(10, 10);
    public Dimension TAB_PADDING = new Dimension(20, 20);

    public int SCROLL_SPEED = 16;
    public Dimension IMAGE_SIZE = new Dimension(400, 250);
    public Font CARD_TITLE_FONT = new Font("SansSerif", Font.PLAIN, 18);
    public Font PAGE_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public static void resetToDefaults() {
        File file = FileNavigation.getSettingsFile();
        file.delete();
        instance = new UISettings();
    }
}
