package uk.jamieisgeek.sootlib.Misc;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextManager {

    /*
        @param message The message to translate
        @return The formatted message
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
}
