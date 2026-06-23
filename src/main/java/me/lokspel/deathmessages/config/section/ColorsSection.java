package me.lokspel.deathmessages.config.section;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

public class ColorsSection {

    private final FileConfiguration config;
    private final String path;

    private TextColor deathMain;
    private TextColor deathPlayer;
    private TextColor deathKiller;
    private TextColor deathWeapon;
    private TextColor joinMain;
    private TextColor joinPlayer;
    private TextColor quitMain;
    private TextColor quitPlayer;

    public ColorsSection(FileConfiguration config, String path) {
        this.config = config;
        this.path = path;
        parse();
    }

    private void parse() {
        deathMain = parseColor("Death.Main", "<red>");
        deathPlayer = parseColor("Death.Player", "<green>");
        deathKiller = parseColor("Death.Killer", "<red>");
        deathWeapon = parseColor("Death.Weapon", "<yellow>");
        joinMain = parseColor("Join.Main", "<dark_green>");
        joinPlayer = parseColor("Join.Player", "<green>");
        quitMain = parseColor("Quit.Main", "<dark_red>");
        quitPlayer = parseColor("Quit.Player", "<light_purple>");
    }

    private TextColor parseColor(String key, String defaultRaw) {
        String raw = config.getString(path + "." + key, defaultRaw);
        return MiniMessage.miniMessage().deserialize(raw + "x").color();
    }

    public TextColor getDeathMain() {
        return deathMain;
    }

    public TextColor getDeathPlayer() {
        return deathPlayer;
    }

    public TextColor getDeathKiller() {
        return deathKiller;
    }

    public TextColor getDeathWeapon() {
        return deathWeapon;
    }

    public TextColor getJoinMain() {
        return joinMain;
    }

    public TextColor getJoinPlayer() {
        return joinPlayer;
    }

    public TextColor getQuitMain() {
        return quitMain;
    }

    public TextColor getQuitPlayer() {
        return quitPlayer;
    }
}
