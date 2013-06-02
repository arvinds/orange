import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.deploy.Verticle;
import java.util.HashSet;
import java.io.File;

public class Orange extends Verticle 
{

    private HashSet<String> validFiles = new HashSet<String>();

    private final String FRONTEND_FILES_PREFIX = "/home/azureuser/orange/frontend";

    private final int HTTP_PORT = 8080;

    Logger log;

    public void start() 
    {
        log = container.getLogger();
	RouteMatcher rm = new RouteMatcher();

        // serve js and css and applet files
        rm.getWithRegEx("/(js|css|consoleapplet)/.*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                logRequestInfo(req);
                serveFile(req.response, FRONTEND_FILES_PREFIX + req.uri);
            }
        });

        // serve index. regex is forward-slash or index.html
	rm.getWithRegEx("(/|index.html)", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                logRequestInfo(req);
                serveFile(req.response, FRONTEND_FILES_PREFIX + "/index.html");
	    }
	});


        // serve 404 for everything else
	rm.getWithRegEx(".*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                logRequestInfo(req);

                // serve 404
                log.error("Serving 404 for request: " + req.uri);
                req.response.end("404 Not Found");
	    }
	});

	vertx.createHttpServer().requestHandler(rm).listen(HTTP_PORT);
    }

    private void logRequestInfo(HttpServerRequest req)
    {
        String reqInfo = "Got request: " + req.uri + "\nHeaders are: ";
        log.trace(reqInfo);
        for (String key : req.headers().keySet()) {
            reqInfo += key + ":" + req.headers().get(key) + "\n";
        }
        reqInfo += "\n";
        log.trace(reqInfo);
    }

    private void serveFile(HttpServerResponse response, String filePath)
    {
        HttpServerResponse resp = response.sendFile(filePath);
        if(resp.statusCode > 400 && resp.statusCode < 600)
        {
            log.error("Served (" + resp.statusCode + ") " + resp.statusMessage + " for filepath " + filePath);
        }
        else
        {
            log.info("Served (" + resp.statusCode + ") " + resp.statusMessage + " for filepath " + filePath);
        }
    }
}
