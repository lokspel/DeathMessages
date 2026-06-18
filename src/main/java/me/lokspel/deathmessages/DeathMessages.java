package me.lokspel.deathmessages;

import me.lokspel.deathmessages.commands.CommandBlacklist;
import me.lokspel.deathmessages.commands.CommandDispatcher;
import me.lokspel.deathmessages.commands.CommandDispatcher.RegisteredCommand;
import me.lokspel.deathmessages.commands.CommandReload;
import me.lokspel.deathmessages.commands.CommandToggle;
import me.lokspel.deathmessages.commands.ToggleConnectionMsgCommand;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.config.UserDataManager;
import me.lokspel.deathmessages.events.OnPlayerDeathEvent;
import me.lokspel.deathmessages.events.OnPlayerJoinEvent;
import me.lokspel.deathmessages.events.OnPlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class DeathMessages extends JavaPlugin {

    private static DeathMessages instance;
    private ConfigManager configManager;
    private UserDataManager userDataManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("§b[DeathMessages] Plugin is loading...");

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        userDataManager = new UserDataManager(this);
        userDataManager.loadUserData();

        CommandDispatcher dispatcher = new CommandDispatcher(configManager, List.of(
                new RegisteredCommand("reload", new CommandReload(this)),
                new RegisteredCommand("toggle", new CommandToggle(this)),
                new RegisteredCommand("blacklist", new CommandBlacklist(this))
        ));
        getCommand("deathmessages").setExecutor(dispatcher);
        getCommand("deathmessages").setTabCompleter(dispatcher);
        getCommand("toggleconnectionmsg").setExecutor(new ToggleConnectionMsgCommand(this));

        getServer().getPluginManager().registerEvents(new OnPlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), this);

        getLogger().info("Plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        if (userDataManager != null) {
            userDataManager.saveUserData();
        }
        getLogger().info("Plugin disabled!");
    }

    public static DeathMessages getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public UserDataManager getUserDataManager() {
        return userDataManager;
    }
}
