package me.lokspel.deathmessages;

import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.events.OnPlayerDeathEvent;
import me.lokspel.deathmessages.events.OnPlayerJoinEvent;
import me.lokspel.deathmessages.events.OnPlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathMessages extends JavaPlugin {

    private static DeathMessages instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("§b[DeathMessages] Plugin is loading...");

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        getServer().getPluginManager().registerEvents(new OnPlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), this);

        getLogger().info("§a[DeathMessages] Plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("§c[DeathMessages] Plugin disabled!");
    }

    public static DeathMessages getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
