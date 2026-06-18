package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.config.UserDataManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToggle implements SubCommand {

    private final ConfigManager config;
    private final UserDataManager userData;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public CommandToggle(DeathMessages plugin) {
        this.config = plugin.getConfigManager();
        this.userData = plugin.getUserDataManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deathmessages.command.toggle")) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.getMessages().getNoPermission().replace("%prefix%", config.getMessages().getPrefix())));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(legacySerializer.deserialize(config.getMessages().getPlayerOnly()));
            return true;
        }

        boolean current = userData.isMessagesEnabled(player.getUniqueId());
        userData.setMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            player.sendMessage(legacySerializer.deserialize(config.getMessages().getToggleOff()));
        } else {
            player.sendMessage(legacySerializer.deserialize(config.getMessages().getToggleOn()));
        }
        return true;
    }
}
