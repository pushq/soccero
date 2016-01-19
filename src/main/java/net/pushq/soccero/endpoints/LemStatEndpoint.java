package net.pushq.soccero.endpoints;

import net.pushq.soccero.MainApplication;
import net.pushq.soccero.domain.Game;
import net.pushq.soccero.domain.StatsRecord;
import net.pushq.soccero.framework.AbstractEndpoint;
import net.pushq.soccero.ranking.lem.Calculator;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

public class LemStatEndpoint extends AbstractEndpoint {

  @Override
  protected List<StatsRecord> handleGet(Request req, Response res, Map<String, Object> attributes) {
    List<Game> games = MainApplication.PROVIDER.activeGames();
    Calculator calculator = new Calculator(games);
    return calculator.calculate();
  }

  @Override
  public String getLocator() {
    return "/api/lemstat";
  }
}
