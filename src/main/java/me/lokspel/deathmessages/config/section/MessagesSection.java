package me.lokspel.deathmessages.config.section;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesSection {

    private final FileConfiguration config;
    private final String path;

    public MessagesSection(FileConfiguration config, String path) {
        this.config = config;
        this.path = path;
    }

    public String getPrefix() {
        return config.getString(path + ".Prefix", "&7[DeathMessages]&r ");
    }

    public String getPlayerOnly() {
        return config.getString(path + ".Player-Only-Command", "&cOnly players can run this command.");
    }

    public String getNoPermission() {
        return config.getString(path + ".No-Permission", "%prefix%&cYou do not have permission for this command.");
    }

    public List<String> getHelp() {
        return config.getStringList(path + ".Help");
    }

    public String getReloaded() {
        return config.getString(path + ".Sub-Commands.Reload.Reloaded", "%prefix%&aReloaded all plugin configs.");
    }

    public String getToggleOn() {
        return config.getString(path + ".Sub-Commands.Toggle.Toggle-On", "&6Death messages unhidden.");
    }

    public String getToggleOff() {
        return config.getString(path + ".Sub-Commands.Toggle.Toggle-Off", "&6Death messages hidden.");
    }

    public String getBlacklistHelp() {
        return config.getString(path + ".Sub-Commands.Blacklist.Help", "&cUsage: /dm blacklist <username>");
    }

    public String getBlacklistUsernameNonexistent() {
        return config.getString(path + ".Sub-Commands.Blacklist.Username-None-Existent", "%prefix%&cCould not find the player with the username &e%player%");
    }

    public String getBlacklistAdd() {
        return config.getString(path + ".Sub-Commands.Blacklist.Blacklist-Add", "&6Now blacklisting &3%player%");
    }

    public String getBlacklistRemove() {
        return config.getString(path + ".Sub-Commands.Blacklist.Blacklist-Remove", "&6No longer blacklisting &3%player%");
    }
}
