package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties config;

    public static void load() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";

        config = new Properties();
        config.load(new FileInputStream(appConfigPath));
    }

    public static String getProperty(String property) {
        return config.getProperty(property);
    }
    public static int getInteger(String property) {
        return Integer.parseInt(config.getProperty(property));
    }
}
