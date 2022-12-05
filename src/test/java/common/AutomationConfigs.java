package common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class AutomationConfigs {

    private static final String GENERAL_CONFIGS_PATH = "src/resources/config.properties";

    private static AutomationConfigs instance;

    private AutomationConfigs() {
    }

    public static AutomationConfigs getInstance() {
        if(instance == null) {
            return new AutomationConfigs();
        }
        return instance;
    }

    public Properties getConfigs() {
        try {
            InputStream in = Files.newInputStream(Paths.get(GENERAL_CONFIGS_PATH));
            Properties prop = new Properties();
            prop.load(in);
            return prop;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
