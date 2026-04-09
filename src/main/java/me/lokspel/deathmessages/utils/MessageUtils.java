package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer plainSerializer = PlainTextComponentSerializer.plainText();

    /**
     * Extracts plain text from a Component, removing all formatting.
     */
    public static String getPlainText(Component component) {
        return plainSerializer.serialize(component);
    }

    /**
     * Colors a name with MiniMessage format and removes hover events.
     */
    public static Component colorName(String name, String colorFormat) {
        return miniMessage.deserialize(colorFormat + name).hoverEvent(null);
    }

    /**
     * Colors an entire message and replaces a target name with a colored version.
     */
    public static Component colorMessageWithName(Component message, String mainColor, String name, String nameColor) {
        String messageText = getPlainText(message);
        Component colored = miniMessage.deserialize(mainColor + messageText);
        Component nameComponent = colorName(name, nameColor);
        
        return colored.replaceText(builder -> builder.matchLiteral(name)
                .replacement(nameComponent));
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
