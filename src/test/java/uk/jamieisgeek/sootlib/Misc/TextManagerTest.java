package uk.jamieisgeek.sootlib.Misc;

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
}