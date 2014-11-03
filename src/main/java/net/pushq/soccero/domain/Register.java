package net.pushq.soccero.domain;

/**
 * Created by Michal on 2014-10-12.
 */
public class Register {
    Integer playerR1;
    Integer playerR2;
    Integer playerB1;
    Integer playerB2;
    Integer redScore;
    Integer blueScore;

    public Integer getPlayerR1() {
        return playerR1;
    }

    public void setPlayerR1(Integer playerR1) {
        this.playerR1 = playerR1;
    }

    public Integer getPlayerR2() {
        return playerR2;
    }

    public void setPlayerR2(Integer playerR2) {
        this.playerR2 = playerR2;
    }

    public Integer getPlayerB1() {
        return playerB1;
    }

    public void setPlayerB1(Integer playerB1) {
        this.playerB1 = playerB1;
    }

    public Integer getPlayerB2() {
        return playerB2;
    }

    public void setPlayerB2(Integer playerB2) {
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
}
