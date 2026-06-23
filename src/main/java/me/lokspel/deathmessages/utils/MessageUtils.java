package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static final PlainTextComponentSerializer plainSerializer = PlainTextComponentSerializer.plainText();

    /**
     * Extracts plain text from a Component, removing all formatting.
     */
    public static String getPlainText(Component component) {
        return plainSerializer.serialize(component);
    }

    /**
     * Colors a name with the given color and removes hover events.
     */
    public static Component colorName(String name, TextColor color) {
        return Component.text(name).color(color).hoverEvent(null);
    }

    /**
     * Sends a message to all online players.
     */
    public static void broadcastMessage(Component message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}
