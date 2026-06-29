package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.config.UserDataManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private final ConfigManager config;
    private final UserDataManager userData;

    public OnPlayerJoinEvent() {
        this.config = DeathMessages.getInstance().getConfigManager();
        this.userData = DeathMessages.getInstance().getUserDataManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerJoinEvent event) {
        if (!config.getSettings().isJoinMessagesEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        long playTime = PlayerUtils.getPlayTime(player);
        int minPlayTimeMinutes = config.getSettings().getMinPlayTimeMinutes();

        if (playTime < minPlayTimeMinutes) {
            event.joinMessage(null);
            return;
        }

        Component message = event.joinMessage();
        if (message != null) {
            TextColor mainColor = config.getColors().getJoinMain();

            Component colored = MessageUtils.stripHoverEvents(message);
            if (mainColor != null) {
                colored = colored.color(mainColor);
            }

            Component playerComponent = MessageUtils.colorName(player.getName(), config.getColors().getJoinPlayer());
            colored = colored.replaceText(builder -> builder
                    .matchLiteral(player.getName())
                    .replacement(playerComponent));

            for (Player online : player.getServer().getOnlinePlayers()) {
                if (userData.isConnectionMessagesEnabled(online.getUniqueId())) {
                    online.sendMessage(colored);
                }
            }

            Bukkit.getConsoleSender().sendMessage(colored);
        }

        event.joinMessage(null);
    }
}
