package net.pushq.soccero.framework;

import net.pushq.soccero.MainApplication;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

/**
 * Created by Michal on 2014-10-10.
 */
public abstract class AbstractPage {

    public void register() {
        Spark.get(getLocator(), (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            handleGet(req, res, attributes);
            return new ModelAndView(attributes, getHtml());
        }, MainApplication.FTL);

        post(getLocator(), (req, res) -> {
            handlePost(req, res);
            return "";
        });
    }

    protected abstract void handleGet(Request req, Response res, Map<String, Object> attributes);

    protected abstract void handlePost(Request req, Response res);

    protected abstract String getHtml();

    public abstract String getLocator();


}
