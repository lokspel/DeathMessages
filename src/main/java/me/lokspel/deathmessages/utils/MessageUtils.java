package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.TextColor;

public class MessageUtils {

    public static Component colorName(String name, TextColor color) {
        return Component.text(name).color(color).hoverEvent(null);
    }

    public static Component stripHoverEvents(Component component) {
        if (component instanceof TranslatableComponent tc) {
            return tc.arguments(tc.arguments().stream()
                .map(a -> a.asComponent().hoverEvent(null))
                .toList());
        }
        return component;
    }

}
