package net.pushq.soccero.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michal on 2014-10-12.
 */
public class Game {

    final static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    String playerR1;
    String playerR2;
    String playerB1;
    String playerB2;
    Integer redScore;
    Integer blueScore;
    Date date;

    public Game(String playerR1, String playerR2, String playerB1, String playerB2, Integer redScore, Integer blueScore, Date date) {
        this.playerR1 = playerR1;
        this.playerR2 = playerR2;
        this.playerB1 = playerB1;
        this.playerB2 = playerB2;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.date = date;
    }

    public Game() {

    }

    public String getPlayerR1() {
        return playerR1;
    }

    public void setPlayerR1(String playerR1) {
        this.playerR1 = playerR1;
    }

    public String getPlayerR2() {
        return playerR2;
    }

    public void setPlayerR2(String playerR2) {
        this.playerR2 = playerR2;
    }

    public String getPlayerB1() {
        return playerB1;
    }

    public void setPlayerB1(String playerB1) {
        this.playerB1 = playerB1;
    }

    public String getPlayerB2() {
        return playerB2;
    }

    public void setPlayerB2(String playerB2) {
        this.playerB2 = playerB2;
    }

    public Integer getRedScore() {
        return redScore;
    }

    public void setRedScore(Integer redScore) {
        this.redScore = redScore;
    }

    public Integer getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(Integer blueScore) {
        this.blueScore = blueScore;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return format.format(date);
    }

    @Override
    public String toString() {
        return "Game{" +
                " playerR1='" + playerR1 + '\'' +
                " playerR2='" + playerR2 + '\'' +
                " playerB1='" + playerB1 + '\'' +
                " playerB2='" + playerB2 + '\'' +
                " redScore=" + redScore +
                " blueScore=" + blueScore +
                '}';
    }
}
