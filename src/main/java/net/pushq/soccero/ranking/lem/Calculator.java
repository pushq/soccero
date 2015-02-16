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
public class Calculator {

    public static final int VICTORY_RATIO = 1;

    protected static class SinglePlayerScore {
        public double scoreSum = 0;
        public int plays = 0;

        public double getAvg() {
            return scoreSum / plays;
        }
    }

    protected final List<Game> games;

    public Calculator(List<Game> games) {
        this.games = games;
    }

    public List<StatsRecord> calculate() {
        List<String> axis = preparePlayerAxis(games);

        Map<String, SinglePlayerScore> scoreMap = prepareScoreRatioMap(games, axis);

        RealMatrix coefficients = prepareCoefficientsMatrix(games, axis);

        RealVector scoreConstant = prepareAverageScoreVector(axis, scoreMap);

        adjustCoefficients(axis, scoreMap, coefficients);

        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector solution = solver.solve(scoreConstant);

        List<StatsRecord> results = axis.stream().map(player -> {
            StatsRecord statsRecord = new StatsRecord();
            statsRecord.setName(player);
            statsRecord.setRatio(new BigDecimal(solution.getEntry(axis.indexOf(player))).setScale(2, BigDecimal.ROUND_UP));
            statsRecord.setAvg(new BigDecimal(scoreMap.get(player).getAvg()).setScale(2, BigDecimal.ROUND_UP));
            statsRecord.setMod(statsRecord.getRatio().subtract(statsRecord.getAvg()));
            return statsRecord;
        }).collect(Collectors.toList());

        results.sort((r1, r2) -> -r1.getRatio().compareTo(r2.getRatio()));

        return results;
    }

    protected void adjustCoefficients(List<String> axis, Map<String, SinglePlayerScore> scoreMap, RealMatrix coefficients) {
        scoreMap.forEach((player, score) -> {
            RealVector rowVector = coefficients.getRowVector(axis.indexOf(player));
            rowVector = rowVector.mapMultiply(1.0 / (score.plays * 3));
            coefficients.setRowVector(axis.indexOf(player), rowVector);
        });

        for (int i = 0; i < axis.size(); i++) {
            coefficients.setEntry(i, i, 1.0);
        }
    }

    protected RealVector prepareAverageScoreVector(List<String> axis, Map<String, SinglePlayerScore> scoreMap) {
        RealVector scoreConstant = new ArrayRealVector(axis.size());

        scoreMap.forEach((player, score) -> {
            scoreConstant.setEntry(axis.indexOf(player), score.getAvg());
        });
        return scoreConstant;
    }

    protected RealMatrix prepareCoefficientsMatrix(List<Game> games, List<String> axis) {
        RealMatrix coefficients = new Array2DRowRealMatrix(axis.size(), axis.size());

        games.forEach( g -> {
            adjustCoefficients(axis, coefficients, g.getPlayerB1(), g.getPlayerB2(), g.getPlayerR1(), g.getPlayerR2(), g.getBlueScore(), g.getRedScore());
            adjustCoefficients(axis, coefficients, g.getPlayerB2(), g.getPlayerB1(), g.getPlayerR1(), g.getPlayerR2(), g.getBlueScore(), g.getRedScore());
            adjustCoefficients(axis, coefficients, g.getPlayerR1(), g.getPlayerR2(), g.getPlayerB1(), g.getPlayerB2(), g.getRedScore(), g.getBlueScore());
            adjustCoefficients(axis, coefficients, g.getPlayerR2(), g.getPlayerR1(), g.getPlayerB1(), g.getPlayerB2(), g.getRedScore(), g.getBlueScore());
        });
        return coefficients;
    }

    protected Map<String, SinglePlayerScore> prepareScoreRatioMap(List<Game> games, List<String> axis) {
        Map<String, SinglePlayerScore> scoreMap = new HashMap<>();
        axis.forEach(s -> scoreMap.put(s, new SinglePlayerScore()));
        games.forEach( g -> {
            sumScore(scoreMap, g);
        });
        return scoreMap;
    }

    protected List<String> preparePlayerAxis(List<Game> games) {
        Set<String> preAxis = new HashSet<String>();

        games.forEach( g -> {
            preAxis.add(g.getPlayerB1());
            preAxis.add(g.getPlayerB2());
            preAxis.add(g.getPlayerR1());
            preAxis.add(g.getPlayerR2());
        });

        List<String> axis = new ArrayList<String>(preAxis);
        Collections.sort(axis);
        return axis;
    }

    protected void adjustCoefficients(List<String> axis, RealMatrix coefficients, String player, String mate, String opponent1, String opponent2, Integer playerScore, Integer opponentScore) {
        coefficients.addToEntry(axis.indexOf(player), axis.indexOf(mate), 1);
        coefficients.addToEntry(axis.indexOf(player), axis.indexOf(opponent1), -1);
        coefficients.addToEntry(axis.indexOf(player), axis.indexOf(opponent2), -1);
    }

    protected void sumScore(Map<String, SinglePlayerScore> scoreMap, Game g) {
        Integer blueScore = g.getBlueScore() == 10 ? 10 + getVictoryRatio() : g.getBlueScore();
        Integer redScore = g.getRedScore() == 10 ? 10 + getVictoryRatio() : g.getRedScore();

        scoreMap.get(g.getPlayerB1()).plays++;
        scoreMap.get(g.getPlayerB1()).scoreSum += blueScore - redScore;

        scoreMap.get(g.getPlayerB2()).plays++;
        scoreMap.get(g.getPlayerB2()).scoreSum += blueScore - redScore;

        scoreMap.get(g.getPlayerR1()).plays++;
        scoreMap.get(g.getPlayerR1()).scoreSum += redScore - blueScore;

        scoreMap.get(g.getPlayerR2()).plays++;
        scoreMap.get(g.getPlayerR2()).scoreSum += redScore - blueScore;
    }

    protected int getVictoryRatio() {
        return VICTORY_RATIO;
    }
}
