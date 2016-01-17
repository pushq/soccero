package net.pushq.soccero.framework;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEndpoint {

  private final static Gson GSON = new Gson();

  public void register() {
    Spark.get(getLocator(), (req, res) -> {
      Map<String, Object> attributes = new HashMap<>();
      return handleGet(req, res, attributes);

    }, GSON::toJson);
  }

  protected abstract Object handleGet(Request req, Response res, Map<String, Object> attributes);

  public abstract String getLocator();
}
