package net.pushq.soccero.pages;

import net.pushq.soccero.framework.AbstractPage;
import spark.Request;
import spark.Response;

import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by Michal on 2014-10-10.
 */
public class LoginPage extends AbstractPage {

    public static final String LOCATOR = "/login";
    public static final String HTML = "login.ftl.html";

    @Override
    protected void handleGet(Request req, Response res, Map<String, Object> attributes) {
        attributes.put("welcome", System.getProperty("soc.welcome"));
    }

    @Override
    protected void handlePost(Request req, Response res) {
        String pass = req.queryParams("pass");

        if (pass.equals("caseflow")) {
            req.session().attribute("auth", true);
        }

        res.redirect(HomePage.LOCATOR);
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
