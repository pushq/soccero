package net.pushq.soccero.pages;

import net.pushq.soccero.framework.AbstractPage;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.Map;

/**
 * Created by Michal on 2014-10-10.
 */
public class HomePage extends AbstractPage {

    public static final String LOCATOR = "/home";
    public static final String HTML = "home.ftl.html";

    @Override
    protected void handleGet(Request req, Response res, Map<String, Object> attributes) {
        attributes.put("message", "Welcome, Time now is: " + new Date());
    }

    @Override
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
