package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.config.UserDataManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {

    private final ConfigManager config;
    private final UserDataManager userData;

    public OnPlayerQuitEvent() {
        this.config = DeathMessages.getInstance().getConfigManager();
        this.userData = DeathMessages.getInstance().getUserDataManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerUtils.removeDeathTime(player);

        if (!config.getSettings().isQuitMessagesEnabled()) {
            return;
        }

        long playTime = PlayerUtils.getPlayTime(player);
        int minPlayTimeMinutes = config.getSettings().getMinPlayTimeMinutes();

        if (playTime < minPlayTimeMinutes) {
            event.quitMessage(null);
            return;
        }

        Component message = event.quitMessage();
        if (message != null) {
            String plainText = MessageUtils.getPlainText(message);
            MiniMessage mm = MiniMessage.miniMessage();
            TextColor parsedMainColor = mm.deserialize(config.getColors().getQuitMain() + "x").color();

            Component colored = parsedMainColor != null
                    ? Component.text(plainText).color(parsedMainColor)
                    : Component.text(plainText);

            Component playerComponent = MessageUtils.colorName(player.getName(), config.getColors().getQuitPlayer());
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

        event.quitMessage(null);
    }
}
