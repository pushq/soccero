package net.pushq.soccero.domain;

import java.math.BigDecimal;

/**
 * Created by Michal on 2014-10-13.
 */
public class StatsRecord {

    String name;

    Integer wins;

    Integer losses;

    Integer sum;

    BigDecimal ratio;
    private BigDecimal avg;
    private BigDecimal mod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setMod(BigDecimal mod) {
        this.mod = mod;
    }

    public BigDecimal getMod() {
        return mod;
    }
}
