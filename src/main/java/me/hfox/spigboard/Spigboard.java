package me.hfox.spigboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Spigboard {

    private Scoreboard scoreboard;
    private Objective objective;
    private Map<String, SpigboardEntry> entries;

    private int teamId;

    public Spigboard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("spigobjective", "dummy");
        setTitle(title);

        this.entries = new HashMap<>();
        this.teamId = 1;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setTitle(String title) {
        objective.setDisplayName(title);
    }

    public SpigboardEntry getEntry(String key) {
        return entries.get(key);
    }

    public SpigboardEntry add(String name, int value) {
        return add((String) null, name, value);
    }

    public SpigboardEntry add(Enum key, String name, int value) {
        return add(key.name(), name, value);
    }

    public SpigboardEntry add(String key, String name, int value) {
        return add(key, name, value, false);
    }

    public SpigboardEntry add(String name, int value, boolean overwrite) {
        return add((String) null, name, value, overwrite);
    }

    public SpigboardEntry add(Enum key, String name, int value, boolean overwrite) {
        return add(key.name(), name, value, overwrite);
    }

    public SpigboardEntry add(String key, String name, int value, boolean overwrite) {
        if (overwrite && contains(name)) {
            SpigboardEntry entry = getEntryByName(name);
            if (key != null && entries.get(key) != entry) {
                throw new IllegalArgumentException("Supplied key references a different score than the one to be overwritten");
            }

            entry.setValue(value);
            return entry;
        }

        if (entries.get(key) != null) {
            throw new IllegalArgumentException("Score already exists with that key");
        }

        if (!overwrite) {
            name = create(name);
        }

        SpigboardEntry entry = new SpigboardEntry(key, this, value);
        entry.update(name);
        return entry;
    }

    private String create(String name) {
        Set<String> scores = scoreboard.getEntries();
        while (scores.contains(name)) {
            name += ChatColor.RESET;
        }

        if (name.length() > 48) {
            name = name.substring(0, 47);
        }

        if (scores.contains(name)) {
            throw new IllegalArgumentException("Could not find a suitable replacement name for '" + name + "'");
        }

        return name;
    }

    public int getTeamId() {
        return teamId++;
    }

    public SpigboardEntry getEntryByName(String name) {
        for (SpigboardEntry entry : entries.values()) {
            if (entry.getName().equals(name)) {
                return entry;
            }
        }

        return null;
    }

    public boolean contains(String name) {
        for (SpigboardEntry entry : entries.values()) {
            if (entry.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void add(Player player) {
        player.setScoreboard(scoreboard);
    }

}
