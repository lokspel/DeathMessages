package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnPlayerDeathEvent implements Listener {

    private final ConfigManager config;

    public OnPlayerDeathEvent() {
        this.config = DeathMessages.getInstance().getConfigManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerDeathEvent event) {
        if (!config.isDeathMessagesEnabled()) {
            return;
        }

        Component deathMessage = event.deathMessage();
        Player player = event.getEntity();
        long playTime = PlayerUtils.getPlayTime(player);
        int minPlayTimeMinutes = config.getMinPlayTimeMinutes();

        if (playTime < minPlayTimeMinutes) {
            return;
        }

        Player killer = player.getKiller();
        int cooldownSeconds = config.getDeathMessageCooldownSeconds();

        if (deathMessage != null && !PlayerUtils.isCooldownActive(player, cooldownSeconds)) {
            String playerName = player.getName();
            String killerName = (killer != null) ? killer.getName() : "Unknown";

            Component colored = colorDeathMessage(
                    deathMessage,
                    config.getDeathMainColor(),
                    playerName,
                    config.getDeathPlayerColor(),
                    killerName,
                    config.getDeathKillerColor(),
                    config.getDeathWeaponColor(),
                    killer
            );

            MessageUtils.broadcastMessage(colored);
            event.deathMessage(null);
            PlayerUtils.addDeathTime(player, PlayerUtils.getCurrentTimeSeconds());
        } else {
            event.deathMessage(null);
        }
    }

    private Component colorDeathMessage(
            Component message,
            String mainColor,
            String playerName,
            String playerColor,
            String killerName,
            String killerColor,
            String weaponColor,
            Player killer
    ) {
        String messageText = MessageUtils.getPlainText(message);
        Component colored = MessageUtils.colorMessageWithName(
                Component.text(messageText),
                mainColor,
                playerName,
                playerColor
        );

        if (killerName != null && !killerName.isEmpty() && !killerName.equals("Unknown")) {
            Component killerComponent = MessageUtils.colorName(killerName, killerColor);
            colored = colored.replaceText(builder -> builder.matchLiteral(killerName)
                    .replacement(killerComponent));
        }

        if (killer != null) {
            ItemStack weapon = killer.getInventory().getItemInMainHand();
            if (weapon != null && weapon.getType() != Material.AIR) {
                String weaponName = getWeaponName(weapon);
                if (weaponName != null) {
                    Component weaponComponent = MessageUtils.colorName(weaponName, weaponColor);
                    colored = colored.replaceText(builder -> builder.matchLiteral(weaponName)
                            .replacement(weaponComponent));
                }
            }
        }

        return colored;
    }

    private String getWeaponName(ItemStack weapon) {
        ItemMeta itemMeta = weapon.getItemMeta();
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            Component displayName = itemMeta.displayName();
            if (displayName instanceof TextComponent) {
                return ((TextComponent) displayName).content();
            }
        }
        return weapon.getType().name();
    }
}
