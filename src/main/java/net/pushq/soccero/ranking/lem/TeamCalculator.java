package net.pushq.soccero.ranking.lem;

import net.pushq.soccero.domain.Game;
import net.pushq.soccero.domain.StatsRecord;
import org.apache.commons.math3.linear.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Michal on 2014-10-17.
 */
public class TeamCalculator extends Calculator {

    public TeamCalculator(List<Game> games) {
        super(games);
    }

    protected List<String> preparePlayerAxis(List<Game> games) {
        Set<String> preAxis = new HashSet<String>();

        games.forEach( g -> {
            preAxis.add(getTeamName(g.getPlayerB1(), g.getPlayerB2()));
            preAxis.add(getTeamName(g.getPlayerR1(), g.getPlayerR2()));
        });

        List<String> axis = new ArrayList<String>(preAxis);
        Collections.sort(axis);
        return axis;
    }

    protected void sumScore(Map<String, SinglePlayerScore> scoreMap, Game g) {
        Integer blueScore = g.getBlueScore() == 10 ? 10 + VICTORY_RATIO : g.getBlueScore();
        Integer redScore = g.getRedScore() == 10 ? 10 + VICTORY_RATIO : g.getRedScore();

        scoreMap.get(getTeamName(g.getPlayerB1(), g.getPlayerB2())).plays++;
        scoreMap.get(getTeamName(g.getPlayerB1(), g.getPlayerB2())).scoreSum += blueScore - redScore;

        scoreMap.get(getTeamName(g.getPlayerR1(), g.getPlayerR2())).plays++;
        scoreMap.get(getTeamName(g.getPlayerR1(), g.getPlayerR2())).scoreSum += redScore - blueScore;
    }

    protected RealMatrix prepareCoefficientsMatrix(List<Game> games, List<String> axis) {
        RealMatrix coefficients = new Array2DRowRealMatrix(axis.size(), axis.size());

        games.forEach( g -> {
            adjustCoefficients(axis, coefficients, getTeamName(g.getPlayerB1(), g.getPlayerB2()), getTeamName(g.getPlayerR1(), g.getPlayerR2()));
            adjustCoefficients(axis, coefficients, getTeamName(g.getPlayerR1(), g.getPlayerR2()), getTeamName(g.getPlayerB1(), g.getPlayerB2()));
        });

        return coefficients;
    }

    protected void adjustCoefficients(List<String> axis, Map<String, SinglePlayerScore> scoreMap, RealMatrix coefficients) {
        scoreMap.forEach((player, score) -> {
            RealVector rowVector = coefficients.getRowVector(axis.indexOf(player));
            rowVector = rowVector.mapMultiply(1.0 / (5 * score.plays));
            coefficients.setRowVector(axis.indexOf(player), rowVector);
        });

        for (int i = 0; i < axis.size(); i++) {
            coefficients.setEntry(i, i, 1.0);
        }
    }


    private void adjustCoefficients(List<String> axis, RealMatrix coefficients, String team, String opponent) {
        coefficients.addToEntry(axis.indexOf(team), axis.indexOf(opponent), 1);
    }

    private String getTeamName(String p1, String p2) {
        if (p1.compareTo(p2) > 0) {
            return p1 + "+" + p2;
        } else {
            return p2 + "+" + p1;
        }
    }

    @Override
    protected int getVictoryRatio() {
        return 0;
    }
}
