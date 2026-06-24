package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.ConfigManager;
import me.lokspel.deathmessages.config.UserDataManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
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
    private final UserDataManager userData;

    public OnPlayerDeathEvent() {
        this.config = DeathMessages.getInstance().getConfigManager();
        this.userData = DeathMessages.getInstance().getUserDataManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerDeathEvent event) {
        if (!config.getSettings().isDeathMessagesEnabled()) {
            return;
        }

        Player player = event.getEntity();
        long playTime = PlayerUtils.getPlayTime(player);

        if (playTime < config.getSettings().getMinPlayTimeMinutes()) {
            return;
        }

        Player killer = player.getKiller();
        int cooldownSeconds = config.getSettings().getDeathMessageCooldownSeconds();
        Component deathMessage = event.deathMessage();

        if (deathMessage == null || PlayerUtils.isCooldownActive(player, cooldownSeconds)) {
            event.deathMessage(null);
            return;
        }

        String playerName = player.getName();
        String killerName = (killer != null) ? killer.getName() : null;

        Component colored = colorDeathMessage(
                deathMessage,
                config.getColors().getDeathMain(),
                playerName,
                config.getColors().getDeathPlayer(),
                killerName,
                config.getColors().getDeathKiller(),
                config.getColors().getDeathWeapon(),
                killer
        );

        for (Player online : player.getServer().getOnlinePlayers()) {
            if (userData.isMessagesEnabled(online.getUniqueId())
                    && !userData.isBlacklistedFor(online.getUniqueId(), player.getUniqueId())) {
                online.sendMessage(colored);
            }
        }

        Bukkit.getConsoleSender().sendMessage(colored);
        event.deathMessage(null);
        PlayerUtils.addDeathTime(player, PlayerUtils.getCurrentTimeSeconds());
    }

    private Component colorDeathMessage(
            Component message,
            TextColor mainColor,
            String playerName,
            TextColor playerColor,
            String killerName,
            TextColor killerColor,
            TextColor weaponColor,
            Player killer
    ) {
        Component colored = mainColor != null
                ? message.color(mainColor)
                : message;

        if (playerName != null && !playerName.isEmpty()) {
            Component playerComponent = MessageUtils.colorName(playerName, playerColor);
            colored = colored.replaceText(builder -> builder
                    .matchLiteral(playerName)
                    .replacement(playerComponent));
        }

        if (killerName != null && !killerName.isEmpty()) {
            Component killerComponent = MessageUtils.colorName(killerName, killerColor);
            colored = colored.replaceText(builder -> builder
                    .matchLiteral(killerName)
                    .replacement(killerComponent));
        }

        if (killer != null) {
            ItemStack weapon = killer.getInventory().getItemInMainHand();
            if (weapon != null && weapon.getType() != Material.AIR) {
                String weaponName = getWeaponName(weapon);
                if (weaponName != null) {
                    Component weaponComponent = MessageUtils.colorName(weaponName, weaponColor);
                    colored = colored.replaceText(builder -> builder
                            .matchLiteral(weaponName)
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
        String[] words = weapon.getType().name().toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }
}
