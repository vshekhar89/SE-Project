package com.software.game.airhockeyandroid.Entities;

import java.util.ArrayList;

/**
 * Created by shardendu on 10/21/15.
 */
public class Player {

    private static String username;
    private static int points;
    private static int rank;
    private static int games_won;
    private static int games_lost;
    public static ArrayList<PowerUp> powerUps = new ArrayList<>();
    private static Player player = null;

    private Player() {

    }

    public static Player getInstance(String username, int coins, int rank, int games_won, int games_lost) {
        if (player == null) {
            player = new Player();
        } else {
            player.setUsername(username);
            player.setGames_won(games_won);
            player.setGames_lost(games_lost);
            player.setPoints(coins);
            player.setRank(rank);
            PowerUp p1 = new PowerUp(0, "Mallet Size");
            PowerUp p2 = new PowerUp(0, "Goal Size");
            PowerUp p3 = new PowerUp(0, "Puck");
            powerUps.add(p1);
            powerUps.add(p2);
            powerUps.add(p3);
        }
        return player;
    }

    public static Player getInstance() {
        if (player == null)
            player = new Player();

        return player;
    }

    public String getUsername() {
        return Player.username;
    }

    public void setUsername(String username) {
        Player.username = username;
    }

    public int getRank() {
        return Player.rank;
    }

    public void setRank(int rank) {
        Player.rank = rank;
    }

    public int getPoints() {
        return Player.points;
    }

    public void setPoints(int points) {
        Player.points = points;
    }

    public int getGames_won() {
        return Player.games_won;
    }

    public void setGames_won(int games_won) {
        Player.games_won = games_won;
    }

    public int getGames_lost() {
        return Player.games_lost;
    }

    public void setGames_lost(int games_lost) {
        Player.games_lost = games_lost;
    }
}
