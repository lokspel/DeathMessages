package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.config.UserDataManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class CommandReload implements SubCommand {

    private final ConfigManager config;
    private final UserDataManager userData;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public CommandReload(DeathMessages plugin) {
        this.config = plugin.getConfigManager();
        this.userData = plugin.getUserDataManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deathmessages.command.reload")) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.getMessages().getNoPermission().replace("%prefix%", config.getMessages().getPrefix())));
            return true;
        }

        config.reloadConfig();
        userData.reloadUserData();
        sender.sendMessage(legacySerializer.deserialize(
                config.getMessages().getReloaded().replace("%prefix%", config.getMessages().getPrefix())));
        return true;
    }
}
