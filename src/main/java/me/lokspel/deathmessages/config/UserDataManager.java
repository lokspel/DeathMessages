package me.lokspel.deathmessages.config;

import me.lokspel.deathmessages.DeathMessages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserDataManager {

    private final DeathMessages plugin;
    private FileConfiguration userData;
    private File userDataFile;

    public UserDataManager(DeathMessages plugin) {
        this.plugin = plugin;
    }

    public void loadUserData() {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdirs()) {
                plugin.getLogger().warning("Failed to create plugin data folder.");
            }
        }

        userDataFile = new File(plugin.getDataFolder(), "UserData.yml");

        if (!userDataFile.exists()) {
            plugin.saveResource("UserData.yml", false);
        }

        userData = YamlConfiguration.loadConfiguration(userDataFile);
    }

    public void saveUserData() {
        try {
            userData.save(userDataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save UserData.yml: " + e.getMessage());
        }
    }

    public boolean isMessagesEnabled(UUID uuid) {
        return userData.getBoolean(uuid + ".messages-enabled", true);
    }

    public void setMessagesEnabled(UUID uuid, boolean enabled) {
        userData.set(uuid + ".messages-enabled", enabled);
        saveUserData();
    }

    public boolean isConnectionMessagesEnabled(UUID uuid) {
        return userData.getBoolean(uuid + ".connection-messages-enabled", true);
    }

    public void setConnectionMessagesEnabled(UUID uuid, boolean enabled) {
        userData.set(uuid + ".connection-messages-enabled", enabled);
        saveUserData();
    }

    public List<String> getBlacklist(UUID viewerUUID) {
        return userData.getStringList(viewerUUID + ".blacklist");
    }

    public boolean isBlacklistedFor(UUID viewerUUID, UUID targetUUID) {
        return getBlacklist(viewerUUID).contains(targetUUID.toString());
    }

    public boolean toggleBlacklist(UUID viewerUUID, UUID targetUUID, String targetUsername) {
        List<String> blacklist = getBlacklist(viewerUUID);
        String targetStr = targetUUID.toString();
        boolean currentlyBlacklisted = blacklist.contains(targetStr);

        if (currentlyBlacklisted) {
            blacklist.remove(targetStr);
        } else {
            blacklist.add(targetStr);
        }

        userData.set(viewerUUID + ".blacklist", blacklist);
        userData.set(targetUUID + ".username", targetUsername);
        saveUserData();
        return !currentlyBlacklisted;
    }

    public UUID getUUIDByUsername(String username) {
        for (String key : userData.getKeys(false)) {
            String storedUsername = userData.getString(key + ".username");
            if (storedUsername != null && storedUsername.equalsIgnoreCase(username)) {
                return UUID.fromString(key);
            }
        }
        return null;
    }

    public void reloadUserData() {
        if (userDataFile != null && userDataFile.exists()) {
            userData = YamlConfiguration.loadConfiguration(userDataFile);
        }
    }

}
