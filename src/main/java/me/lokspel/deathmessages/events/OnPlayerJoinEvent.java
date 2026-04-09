package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private final ConfigManager config;

    public OnPlayerJoinEvent() {
        this.config = DeathMessages.getInstance().getConfigManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerJoinEvent event) {
        if (!config.isJoinMessagesEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        long playTime = PlayerUtils.getPlayTime(player);
        int minPlayTimeMinutes = config.getMinPlayTimeMinutes();

        if (playTime < minPlayTimeMinutes) {
            event.joinMessage(null);
            return;
        }

        Component message = event.joinMessage();
        if (message != null) {
            Component colored = MessageUtils.colorMessageWithName(
                    message,
                    config.getJoinMainColor(),
                    player.getName(),
                    config.getJoinPlayerColor()
            );

            MessageUtils.broadcastMessage(colored);
        }

        event.joinMessage(null);
    }
}
