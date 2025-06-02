package tabs;

import UIUtils.UISettings;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import javax.swing.*;

import org.drjekyll.fontchooser.FontDialog;

import main.TabUpdate;

public class Settings extends JPanel {

    public Settings(TabUpdate callback) {
        UISettings settings = UISettings.getInstance();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Field field : settings.getClass().getDeclaredFields()) {
            Object object;
            try {
                object = field.get(settings);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                continue;
            }
            JPanel row = new JPanel(new BorderLayout(10, 10));

            JLabel label = new JLabel(field.getName() + ": ");
            label.setFont(settings.PAGE_FONT);
            row.add(label, BorderLayout.WEST);

            JLabel currValue = new JLabel(getStringValue(object));
            currValue.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(settings.PAGE_FONT);
            row.add(currValue, BorderLayout.CENTER);

            JButton button = new JButton("set");
            button.setFont(settings.PAGE_FONT);
            button.addActionListener(e -> {
                setSetting(field, object, callback);
            });
            row.add(button, BorderLayout.EAST);

            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            this.add(row);
            this.add(Box.createVerticalStrut(10));
        }
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton reset = new JButton("Reset all settings to defaults");
        reset.setAlignmentX(Component.LEFT_ALIGNMENT);
        reset.addActionListener(e -> {
            UISettings.resetToDefaults();
        });
        wrapper.add(reset);
        this.add(wrapper);
    }

    private String getStringValue(Object object) {
        switch (object) {
            case Dimension d -> {
                return d.width + ", " + d.height;
            }
            case Color c -> {
                return Arrays.toString(c.getColorComponents(null));
            }
            case Font f -> {
                return f.getFamily() + " " + f.getSize();
            }
            case int i -> {
                return String.valueOf(i);
            }
            default -> {
                return "Datatype not recognized";
            }
        }
    }

    private void setSetting(Field field, Object object, TabUpdate callback) {
        UISettings settings = UISettings.getInstance();
        try {
            switch (object) {
                case Dimension d -> {
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Set size:");
                    JPanel popup = new JPanel();
                    popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));

                    JPanel row1 = new JPanel(new FlowLayout());
                    JLabel label1 = new JLabel("Width: ");
                    JSpinner spinner1 = new JSpinner(new SpinnerNumberModel(d.width, 0, 10000, 1));
                    row1.add(label1);
                    row1.add(spinner1);
                    popup.add(row1);

                    JPanel row2 = new JPanel(new FlowLayout());
                    JLabel label2 = new JLabel("Height: ");
                    JSpinner spinner2 = new JSpinner(new SpinnerNumberModel(d.height, 0, 10000, 1));
                    row2.add(label2);
                    row2.add(spinner2);
                    popup.add(row2);

                    JButton button = new JButton("Submit");
                    button.addActionListener(e -> {
                        int width = (int) spinner1.getValue();
                        int height = (int) spinner2.getValue();
                        Dimension newSetting = new Dimension(width, height);
                        try {
                            field.set(settings, newSetting);
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            ex.printStackTrace();
                        } finally {
                            dialog.dispose();
                        }
                    });
                    popup.add(button);

                    dialog.add(popup);
                    dialog.pack();
                    dialog.setLocationRelativeTo(this);
                    dialog.setVisible(true);
                }
                case Color c -> {
                    c = JColorChooser.showDialog(this, "Choose a new color", c, true);
                    if (c == null) {
                        return;
                    }
                    field.set(settings, c);
                }
                case Font f -> {
                    FontDialog dialog = new FontDialog((Frame) null, "Font Dialog Example", true);
                    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    if (!dialog.isCancelSelected()) {
                        field.set(settings, dialog.getSelectedFont());
                    }
                }
                case int i -> {
                    String input = JOptionPane.showInputDialog("Set a numeric value");
                    field.set(settings, Integer.parseInt(input));
                }
                default ->
                    JOptionPane.showMessageDialog(this, "Unsupported datatype", "Cannot modify the datatype of this setting", JOptionPane.ERROR_MESSAGE);
            }
            UISettings.getInstance().save();
            callback.addCard(3);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
