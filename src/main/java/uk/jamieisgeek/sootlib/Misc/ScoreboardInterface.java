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

    public ScoreboardInterface(String title, List<String> lines) {
        this.textManager = SootLib.getSootLib().textManager;

        this.title = textManager.translateHex(title);
        this.lines = this.formatLines(lines);

        manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
    }

    private List<String> formatLines(List<String> lines) {
        List<String> formatted = new ArrayList<>();
        lines.forEach(line -> formatted.add(textManager.translateHex(line)));
        return formatted;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public ScoreboardManager getManager() {
        return manager;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setTitle(String title) {
        this.title = textManager.translateHex(title);
    }

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

    public void update(Player player) {
        this.makeScoreboard();
        player.setScoreboard(scoreboard);
    }

    public void resetScoreboard(Player player) {
        player.setScoreboard(manager.getNewScoreboard());
    }
}
