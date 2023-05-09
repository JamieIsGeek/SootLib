package uk.jamieisgeek.sootlib.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import uk.jamieisgeek.sootlib.SootLib;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardInterface {
    ScoreboardManager manager;
    private final Scoreboard scoreboard;

    private String title;
    private List<String> lines;
    private final TextManager textManager;

    /**
     * @param title The title for the scoreboard
     * @param lines The lines for the scoreboard
     */
    public ScoreboardInterface(String title, List<String> lines) {
        this.textManager = SootLib.getSootLib().textManager;

        this.title = textManager.translateHex(title);
        this.lines = this.formatLines(lines);

        manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
    }

    /**
     * @param lines The unformatted lines
     * @return The formatted lines
     */
    private List<String> formatLines(List<String> lines) {
        List<String> formatted = new ArrayList<>();
        lines.forEach(line -> formatted.add(textManager.translateHex(line)));
        return formatted;
    }

    /**
     * @return The scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * @return The scoreboard manager
     */
    public ScoreboardManager getManager() {
        return manager;
    }

    /**
     * @return The current scoreboard title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The current scoreboard lines
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * @param title The new scoreboard title
     */
    public void setTitle(String title) {
        this.title = textManager.translateHex(title);
    }

    /**
     * @param lines The new scoreboard lines
     */
    public void setLines(List<String> lines) {
        this.lines = formatLines(lines);
    }

    private void makeScoreboard() {
        scoreboard.registerNewObjective("scoreboard", "dummy", title).setDisplaySlot(DisplaySlot.SIDEBAR);
        lines.forEach(line -> {
            Score score = scoreboard.getObjective("scoreboard").getScore(line);
            score.setScore(lines.indexOf(line));
        });
    }

    /**
     * @param player The player to update the scoreboard for
     */
    public void update(Player player) {
        this.makeScoreboard();
        player.setScoreboard(scoreboard);
    }

    /**
     * @param player The player to reset the scoreboard for
     */
    public void resetScoreboard(Player player) {
        player.setScoreboard(manager.getNewScoreboard());
    }
}
