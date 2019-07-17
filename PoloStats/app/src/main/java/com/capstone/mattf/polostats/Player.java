package com.capstone.mattf.polostats;

import java.io.Serializable;

public class Player implements Serializable {
    private String capNumber;
    private String name;
    private String goals;
    private String shotAttempts;
    private String shotPercent;
    private String assists;
    private String drawnEject;
    private String eject;
    private String steals;
    private String blocks;

    public Player(String capNumber, String name, String goals, String shotAttempts, String shotPercent, String assists, String drawnEject, String eject, String steals, String blocks) {
        this.capNumber = capNumber;
        this.name = name;
        this.goals = goals;
        this.shotAttempts = shotAttempts;
        this.shotPercent = shotPercent;
        this.assists = assists;
        this.drawnEject = drawnEject;
        this.eject = eject;
        this.steals = steals;
        this.blocks = blocks;
    }

    public String getCapNumber() {
        return capNumber;
    }

    public String getName() {
        return name;
    }

    public String getGoals() {
        return goals;
    }

    public String getShotAttempts(){ return shotAttempts; }

    public String getShotPercent() {
        return shotPercent;
    }

    public String getAssists() {
        return assists;
    }

    public String getDrawnEject() {
        return drawnEject;
    }

    public String getEject() {
        return eject;
    }

    public String getSteals() {
        return steals;
    }

    public String getBlocks() {
        return blocks;
    }
}
