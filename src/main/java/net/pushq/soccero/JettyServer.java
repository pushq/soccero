package net.pushq.soccero;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by Michal on 2014-10-11.
 */
public class JettyServer {
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);

        String rootPath = JettyServer.class.getClassLoader().getResource(".").toString();
        WebAppContext webapp = new WebAppContext(rootPath + "../../src/main/webapp", "");
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
