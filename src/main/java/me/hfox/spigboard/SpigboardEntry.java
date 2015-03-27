package me.hfox.spigboard;

import com.google.common.base.Splitter;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;

public class SpigboardEntry {

    private String key;
    private Spigboard spigboard;
    private String name;
    private Team team;
    private Score score;
    private int value;

    public SpigboardEntry(String key, Spigboard spigboard, int value) {
        this.key = key;
        this.spigboard = spigboard;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Spigboard getSpigboard() {
        return spigboard;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public int getValue() {
        return score != null ? (value = score.getScore()) : value;
    }

    public void setValue(int value) {
        score.setScore(value);
    }

    public void update(String newName) {
        if (name == null) {
            create(newName);
        }

        if (newName.equals(name)) {
            return;
        }

        score.getScoreboard().resetScores(score.getEntry());
        score = spigboard.getObjective().getScore(name);
        score.setScore(getValue());
    }

    private void create(String name) {
        this.name = name;
        if (score != null) {
            score.getScoreboard().resetScores(score.getEntry());
        }

        if (team != null) {
            team.unregister();
        }

        if (name.length() <= 16) {
            score = spigboard.getObjective().getScore(name);
            score.setScore(getValue());
            return;
        }

        team = spigboard.getScoreboard().registerNewTeam("spigboard-" + spigboard.getTeamId());
        Iterator<String> iterator = Splitter.fixedLength(16).split(name).iterator();
        team.setPrefix(iterator.next());
        score = spigboard.getObjective().getScore(iterator.next());
        score.setScore(getValue());
        if (name.length() > 32)
            team.setSuffix(iterator.next());
    }

}
