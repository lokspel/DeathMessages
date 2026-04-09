package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {

    private final ConfigManager config;

    public OnPlayerQuitEvent() {
        this.config = DeathMessages.getInstance().getConfigManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerUtils.removeDeathTime(player);

        if (!config.isQuitMessagesEnabled()) {
            return;
        }

        long playTime = PlayerUtils.getPlayTime(player);
        int minPlayTimeMinutes = config.getMinPlayTimeMinutes();

        if (playTime < minPlayTimeMinutes) {
            event.quitMessage(null);
            return;
        }

        Component message = event.quitMessage();
        if (message != null) {
            MiniMessage mm = MiniMessage.miniMessage();
            TextColor parsedMainColor = mm.deserialize(config.getQuitMainColor() + "x").color();

            Component colored = parsedMainColor != null
                    ? message.colorIfAbsent(parsedMainColor)
                    : message;

            Component playerComponent = MessageUtils.colorName(player.getName(), config.getQuitPlayerColor());
            colored = colored.replaceText(builder -> builder
                    .matchLiteral(player.getName())
                    .replacement(playerComponent));

            MessageUtils.broadcastMessage(colored);
        }

        event.quitMessage(null);
    }
}
