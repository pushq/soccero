package net.pushq.soccero;

import net.pushq.soccero.data.Provider;
import net.pushq.soccero.framework.Pages;
import net.pushq.soccero.framework.RestEndpoints;
import net.pushq.soccero.pages.HomePage;
import net.pushq.soccero.pages.LoginPage;
import org.apache.commons.io.IOUtils;
import spark.servlet.SparkApplication;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.before;
import static spark.Spark.exception;

public class MainApplication implements SparkApplication {
    public final static VelocityTemplateEngine FTL = new VelocityTemplateEngine();
    public final static Pages PAGES = new Pages();
    public final static RestEndpoints ENDPOINTS = new RestEndpoints();
    public final static Provider PROVIDER = new Provider();

    @Override
    public void init() {
        PAGES.register();
        ENDPOINTS.register();

        before("/*", (req, res) -> {
            String requestURI = req.raw().getRequestURI();

            if (requestURI.equals("/style.css")) {
                res.body(IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("public/style.css")));
                return;
            }
            if (requestURI.equals("/combobox.js")) {
                res.body(IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("public/combobox.js")));
                return;
            }

            if (requestURI.startsWith("/api/")) {
                return;
            }

            if (req.session(true).attribute("auth") == null) {
                if (!requestURI.equals(LoginPage.LOCATOR)) {
                    res.redirect(LoginPage.LOCATOR);
                    res.body("");
                }
            } else {
                if (!PAGES.locators().contains(requestURI)) {
                    res.redirect(HomePage.LOCATOR);
                    res.body("");
                }
            }
        });

        exception(Exception.class, (e, request, response) -> {
            e.printStackTrace();
            response.body(e.toString());
        });
    }
}
