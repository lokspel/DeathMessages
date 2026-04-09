package me.lokspel.deathmessages.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfig() {
        if (configFile != null && configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    // Death message colors
    public String getDeathMainColor() {
        return config.getString("colors.death.main", "<red>");
    }

    public String getDeathPlayerColor() {
        return config.getString("colors.death.player", "<green>");
    }

    public String getDeathKillerColor() {
        return config.getString("colors.death.killer", "<red>");
    }

    public String getDeathWeaponColor() {
        return config.getString("colors.death.weapon", "<yellow>");
    }

    // Join message colors
    public String getJoinMainColor() {
        return config.getString("colors.join.main", "<dark_green>");
    }

    public String getJoinPlayerColor() {
        return config.getString("colors.join.player", "<green>");
    }

    // Quit message colors
    public String getQuitMainColor() {
        return config.getString("colors.quit.main", "<dark_red>");
    }

    public String getQuitPlayerColor() {
        return config.getString("colors.quit.player", "<light_purple>");
    }

    // Settings
    public int getMinPlayTimeMinutes() {
        return config.getInt("settings.min-playtime-minutes", 0);
    }

    public int getDeathMessageCooldownSeconds() {
        return config.getInt("settings.death-message-cooldown-seconds", 3);
    }

    public boolean isDeathMessagesEnabled() {
        return config.getBoolean("settings.death-messages-enabled", true);
    }

    public boolean isJoinMessagesEnabled() {
        return config.getBoolean("settings.join-messages-enabled", true);
    }

    public boolean isQuitMessagesEnabled() {
        return config.getBoolean("settings.quit-messages-enabled", true);
    }
}
