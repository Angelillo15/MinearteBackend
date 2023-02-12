package net.minearte.web.config;

public class Config {
    public static int port() {
        return ConfigLoader.getConfigManager().getConfig().getInt("Config.port");
    }

    public static String tebexApiKey() {
        return ConfigLoader.getConfigManager().getConfig().getString("Config.tebex-api-key");
    }
}
