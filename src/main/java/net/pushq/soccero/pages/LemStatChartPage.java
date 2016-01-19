package net.pushq.soccero.pages;

import net.pushq.soccero.MainApplication;
import net.pushq.soccero.domain.Game;
import net.pushq.soccero.domain.StatsRecord;
import net.pushq.soccero.framework.AbstractPage;
import net.pushq.soccero.ranking.lem.Calculator;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by Michal on 2014-10-17.
 */
public class LemStatChartPage extends AbstractPage {
    public static final String LOCATOR = "/lemstatchart";
    public static final String HTML = "lemstatchart.ftl.html";

    @Override
    protected void handleGet(Request req, Response res, Map<String, Object> attributes) {
        List<Game> games = MainApplication.PROVIDER.activeGames();
        Calculator calculator = new Calculator(games);
        List<StatsRecord> stats = calculator.calculate();

        attributes.put("stats", stats);
    }

    protected void handlePost(Request req, Response res) {

    }

    @Override
    protected String getHtml() {
        return HTML;
    }

    @Override
    public String getLocator() {
        return LOCATOR;
    }
}
