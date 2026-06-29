package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MessageUtils {

    /**
     * Colors a name with the given color and removes hover events.
     */
    public static Component colorName(String name, TextColor color) {
        return Component.text(name).color(color).hoverEvent(null);
    }

}
