package me.lokspel.deathmessages.config;

import me.lokspel.deathmessages.config.section.ColorsSection;
import me.lokspel.deathmessages.config.section.MessagesSection;
import me.lokspel.deathmessages.config.section.SettingsSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    private ColorsSection colors;
    private SettingsSection settings;
    private MessagesSection messages;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdirs()) {
                plugin.getLogger().warning("Failed to create plugin data folder.");
            }
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        colors = new ColorsSection(config, "Colors");
        settings = new SettingsSection(config, "Settings");
        messages = new MessagesSection(config, "Commands.DeathMessages");
    }

    public void reloadConfig() {
        if (configFile != null && configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
            colors = new ColorsSection(config, "Colors");
            settings = new SettingsSection(config, "Settings");
            messages = new MessagesSection(config, "Commands.DeathMessages");
        }
    }

    public ColorsSection getColors() {
        return colors;
    }

    public SettingsSection getSettings() {
        return settings;
    }

    public MessagesSection getMessages() {
        return messages;
    }
}
