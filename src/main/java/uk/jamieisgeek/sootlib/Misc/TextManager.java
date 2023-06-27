package uk.jamieisgeek.sootlib.Misc;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.kyori.adventure.text.Component;

public class TextManager {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * @param message The message to translate
     * @return The color translated message
     */
    public String translateHex(String message) {
        final char COLOUR_CHAR = ChatColor.COLOR_CHAR;
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");

        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOUR_CHAR + "x"
                    + COLOUR_CHAR + group.charAt(0) + COLOUR_CHAR + group.charAt(1)
                    + COLOUR_CHAR + group.charAt(2) + COLOUR_CHAR + group.charAt(3)
                    + COLOUR_CHAR + group.charAt(4) + COLOUR_CHAR + group.charAt(5)
            );
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    /**
     * @author Spanner (<a href="https://spanner.codes"></a>)
     * @param string The string to deserialize
     * @param resolvers The resolvers to use
     * @return The deserialized component
     */
    public Component deserialize(String string,  TagResolver... resolvers) {
        return miniMessage.deserialize(string, resolvers);
    }

    /**
     * @author Spanner (<a href="https://spanner.codes"></a>)
     * @param key The key to resolve
     * @param value The value to replace with
     * @return The TagResolver component
     */
    public TagResolver.Single getResolver(String key, Component value) {
        return Placeholder.component(key, value);
    }
}
