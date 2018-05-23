package com.jarz.game.Database;

/**
 * Created by Margarita on 12/10/2017.
 */

public class Scores {

    private String name;
    private int score;

    public Scores( String newName, int newScore)
    {
        setName(newName);
        setScore(newScore);
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setScore(int newScore)
    {
        score = newScore;
    }

    public String getName( ) {

        return name;
    }

    public int getScore( ) {
        return score;
    }
}

