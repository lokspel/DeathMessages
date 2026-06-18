package me.lokspel.deathmessages.config.section;

import org.bukkit.configuration.file.FileConfiguration;

public class SettingsSection {

    private final FileConfiguration config;
    private final String path;

    public SettingsSection(FileConfiguration config, String path) {
        this.config = config;
        this.path = path;
    }

    public int getMinPlayTimeMinutes() {
        return config.getInt(path + ".Min-Playtime-Minutes", 0);
    }

    public int getDeathMessageCooldownSeconds() {
        return config.getInt(path + ".Death-Message-Cooldown-Seconds", 3);
    }

    public boolean isDeathMessagesEnabled() {
        return config.getBoolean(path + ".Death-Messages-Enabled", true);
    }

    public boolean isJoinMessagesEnabled() {
        return config.getBoolean(path + ".Join-Messages-Enabled", true);
    }

    public boolean isQuitMessagesEnabled() {
        return config.getBoolean(path + ".Quit-Messages-Enabled", true);
    }
}
