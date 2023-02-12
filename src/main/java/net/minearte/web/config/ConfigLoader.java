package net.minearte.web.config;

import es.angelillo15.configmanager.ConfigManager;
import lombok.Getter;
import net.minearte.web.Server;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class ConfigLoader {
    @Getter
    private static ConfigManager configManager;
    @Getter
    private static String path;

    public ConfigLoader(){
        CodeSource codeSource = Server.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        path = jarFile.getParentFile().getPath() + File.separator + "/config/";
    }

    public static void loadConfig() {
        configManager = new ConfigManager(new File(path).toPath(), "config.yml", "config.yml");
        configManager.registerConfig();
    }
}
