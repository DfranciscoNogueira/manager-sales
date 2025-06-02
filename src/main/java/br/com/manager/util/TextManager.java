package br.com.manager.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextManager {

    public static final String PATH = Objects.requireNonNull(Constants.class.getResource("/label-and-message.properties")).getPath();
    private static final Logger logger = Logger.getLogger(TextManager.class.getName());
    private static final Properties properties = new Properties();

    private TextManager() {
        // Construtor privado para evitar instanciamento
    }

    static {
        try (FileInputStream file = new FileInputStream(PATH)) {
            properties.load(file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading labels: {}", e.getMessage());
        }
    }

    public static String getLabel(String key, String... values) {
        String label = properties.getProperty(key, "Label not found");
        for (int i = 0; i < values.length; i++) {
            label = label.replace("{" + i + "}", values[i]); // Substitui os placeholders
        }
        return label;
    }

}
