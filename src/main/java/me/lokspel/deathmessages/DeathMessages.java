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
import java.util.Objects;

public class DeathMessages extends JavaPlugin {

    private static DeathMessages instance;
    private ConfigManager configManager;
    private UserDataManager userDataManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Plugin is loading...");

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        userDataManager = new UserDataManager(this);
        userDataManager.loadUserData();

        var toggleCommand = new CommandToggle(this);

        CommandDispatcher dispatcher = new CommandDispatcher(configManager, List.of(
                new RegisteredCommand("reload", new CommandReload(this)),
                new RegisteredCommand("toggle", toggleCommand),
                new RegisteredCommand("blacklist", new CommandBlacklist(this))
        ));
        var deathMessagesCmd = Objects.requireNonNull(getCommand("deathmessages"));
        deathMessagesCmd.setExecutor(dispatcher);
        deathMessagesCmd.setTabCompleter(dispatcher);

        Objects.requireNonNull(getCommand("toggleconnectionmsg")).setExecutor(new ToggleConnectionMsgCommand(this));

        Objects.requireNonNull(getCommand("deathmessagestoggle")).setExecutor((sender, cmd, label, args) -> toggleCommand.execute(sender, args));

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
