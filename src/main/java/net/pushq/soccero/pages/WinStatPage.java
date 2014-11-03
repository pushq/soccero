package net.pushq.soccero.pages;

import net.pushq.soccero.MainApplication;
import net.pushq.soccero.framework.AbstractPage;
import spark.Request;
import spark.Response;

import java.util.Map;

/**
 * Created by Michal on 2014-10-13.
 */
public class WinStatPage extends AbstractPage {
    public static final String LOCATOR = "/winstats";
    public static final String HTML = "winstats.ftl.html";

    @Override
    protected void handleGet(Request req, Response res, Map<String, Object> attributes) {
        attributes.put("stats", MainApplication.PROVIDER.winStats());
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
