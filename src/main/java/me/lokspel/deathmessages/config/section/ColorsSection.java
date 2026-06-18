package me.lokspel.deathmessages.config.section;

import org.bukkit.configuration.file.FileConfiguration;

public class ColorsSection {

    private final FileConfiguration config;
    private final String path;

    public ColorsSection(FileConfiguration config, String path) {
        this.config = config;
        this.path = path;
    }

    public String getDeathMain() {
        return config.getString(path + ".Death.Main", "<red>");
    }

    public String getDeathPlayer() {
        return config.getString(path + ".Death.Player", "<green>");
    }

    public String getDeathKiller() {
        return config.getString(path + ".Death.Killer", "<red>");
    }

    public String getDeathWeapon() {
        return config.getString(path + ".Death.Weapon", "<yellow>");
    }

    public String getJoinMain() {
        return config.getString(path + ".Join.Main", "<dark_green>");
    }

    public String getJoinPlayer() {
        return config.getString(path + ".Join.Player", "<green>");
    }

    public String getQuitMain() {
        return config.getString(path + ".Quit.Main", "<dark_red>");
    }

    public String getQuitPlayer() {
        return config.getString(path + ".Quit.Player", "<light_purple>");
    }
}
