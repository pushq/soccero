package net.pushq.soccero.pages;

import net.pushq.soccero.MainApplication;
import net.pushq.soccero.convert.Mapper;
import net.pushq.soccero.domain.Register;
import net.pushq.soccero.framework.AbstractPage;
import spark.Request;
import spark.Response;

import java.util.Map;

/**
 * Created by Michal on 2014-10-10.
 */
public class RegisterPage extends AbstractPage {
    public static final String LOCATOR = "/register";
    public static final String HTML = "register.ftl.html";

    @Override
    protected void handleGet(Request req, Response res, Map<String, Object> attributes) {
        attributes.put("players", MainApplication.PROVIDER.players());
        attributes.put("playerRed1", req.queryParams("playerRed1"));
        attributes.put("playerRed2", req.queryParams("playerRed2"));
        attributes.put("playerBlue1", req.queryParams("playerBlue1"));
        attributes.put("playerBlue2", req.queryParams("playerBlue2"));
    }

    @Override
    protected void handlePost(Request req, Response res) {
        Map<String, String[]> stringMap = req.queryMap().toMap();
        Register register = Mapper.mapToObject(stringMap, new Register());
        MainApplication.PROVIDER.register(register);
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
