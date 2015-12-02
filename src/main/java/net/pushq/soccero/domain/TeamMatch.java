package net.pushq.soccero.domain;

import java.math.BigDecimal;

/**
 * Created by Michal on 2015-12-02.
 */
public class TeamMatch {

    public final String teamA1;
    public final String teamA2;
    public final String teamB1;
    public final String teamB2;
    public final BigDecimal match;

    public TeamMatch(String teamA1, String teamA2, String teamB1, String teamB2, BigDecimal match) {

        this.teamA1 = teamA1;
        this.teamA2 = teamA2;
        this.teamB1 = teamB1;
        this.teamB2 = teamB2;
        this.match = match;
    }

    public String getTeamA1() {
        return teamA1;
    }

    public String getTeamA2() {
        return teamA2;
    }

    public String getTeamB1() {
        return teamB1;
    }

    public String getTeamB2() {
        return teamB2;
    }

    public BigDecimal getMatch() {
        return match;
    }

    public Integer getMatchScore() {
        int value = BigDecimal.valueOf(100).subtract(match.multiply(BigDecimal.valueOf(100)).abs()).intValue();
        return value > 0 ? value : 0;
    }
}
