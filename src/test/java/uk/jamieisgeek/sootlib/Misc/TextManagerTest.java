package uk.jamieisgeek.sootlib.Misc;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Test;

public class TextManagerTest {

    @Test
    public void testTranslateHex() {
        TextManager textManager = new TextManager();

        // Test with a single hex color code
        String message1 = "This is a message with a hex color code: &#00FF00";
        String expected1 = "This is a message with a hex color code: " + ChatColor.COLOR_CHAR + "x" +
                ChatColor.COLOR_CHAR + '0' + ChatColor.COLOR_CHAR + '0' +
                ChatColor.COLOR_CHAR + 'F' + ChatColor.COLOR_CHAR + 'F' +
                ChatColor.COLOR_CHAR + '0' + ChatColor.COLOR_CHAR + '0';
        String result1 = textManager.translateHex(message1);
        Assert.assertEquals(expected1, result1);

        // Test with multiple hex color codes
        String message2 = "Hello &#FF0000world&#00FF00!";
        String expected2 = "Hello " + ChatColor.COLOR_CHAR + "x" +
                ChatColor.COLOR_CHAR + 'F' + ChatColor.COLOR_CHAR + 'F' +
                ChatColor.COLOR_CHAR + '0' + ChatColor.COLOR_CHAR + '0' +
                ChatColor.COLOR_CHAR + '0' + ChatColor.COLOR_CHAR + '0' +
                "world" +
                ChatColor.COLOR_CHAR + "x" +
                ChatColor.COLOR_CHAR + '0' + ChatColor.COLOR_CHAR + '0' +
                ChatColor.COLOR_CHAR + 'F' + ChatColor.COLOR_CHAR + 'F' +
                ChatColor.COLOR_CHAR + '0' + ChatColor.COLOR_CHAR + '0' +
                "!";
        String result2 = textManager.translateHex(message2);
        Assert.assertEquals(expected2, result2);
    }

    @Test
    public void testDeserialization() {
        TextManager textManager = new TextManager();

        String message1 = "<red>Hello <blue>world";
        String expected1 = "TextComponentImpl{content=\"Hello \", style=StyleImpl{obfuscated=not_set, bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, color=NamedTextColor{name=\"red\", value=\"#ff5555\"}, clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[TextComponentImpl{content=\"world\", style=StyleImpl{obfuscated=not_set, bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, color=NamedTextColor{name=\"blue\", value=\"#5555ff\"}, clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[]}]}";
        String result1 = textManager.deserialize(message1).toString();
        Assert.assertEquals(expected1, result1);

        String message2 = "<red>Hello <blue><test>";
        String expected2 = "TextComponentImpl{content=\"Hello \", style=StyleImpl{obfuscated=not_set, bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, color=NamedTextColor{name=\"red\", value=\"#ff5555\"}, clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[TextComponentImpl{content=\"world\", style=StyleImpl{obfuscated=not_set, bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, color=NamedTextColor{name=\"blue\", value=\"#5555ff\"}, clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[]}]}";
        String result2 = textManager.deserialize(message2, textManager.getResolver("test", Component.text("world"))).toString();

        Assert.assertEquals(expected2, result2);
    }
}