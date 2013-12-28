import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import java.util.HashSet;
import java.util.Map.Entry;
import java.io.File;

public class Orange extends Verticle 
{

    private HashSet<String> validFiles = new HashSet<String>();

    private final String FRONTEND_FILES_PREFIX = "/home/ubuntu/orange/frontend";

    private final int HTTP_PORT = 8080;

    Logger log;

    public void start() 
    {

        log = container.logger();
	RouteMatcher rm = new RouteMatcher();

        // serve js and css and applet files
        rm.getWithRegEx("/(js|css)/.*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                logRequestInfo(req);
                serveFile(req.response(), FRONTEND_FILES_PREFIX + req.uri());
            }
        });

        // serve index. regex is forward-slash or index.html
	rm.getWithRegEx("(/|index.html)", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                logRequestInfo(req);
                serveFile(req.response(), FRONTEND_FILES_PREFIX + "/index.html");
	    }
	});


        // serve 404 for everything else
	rm.getWithRegEx(".*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                logRequestInfo(req);

                // serve 404
                log.error("Serving 404 for request: " + req.uri());
                req.response().end("404 Not Found");
	    }
	});

	vertx.createHttpServer().requestHandler(rm).listen(HTTP_PORT);
	log.info("Started server on port: " + HTTP_PORT);
    }

    private void logRequestInfo(HttpServerRequest req)
    {
        String reqInfo = "Got request: " + req.uri() + "\nHeaders are: ";
        log.trace(reqInfo);
        for (Entry e : req.headers().entries()) {
            reqInfo += e.getKey() + ":" + e.getValue() + "\n";
        }
        reqInfo += "\n";
        log.trace(reqInfo);
    }

    private void serveFile(HttpServerResponse response, String filePath)
    {
        HttpServerResponse resp = response.sendFile(filePath);
        if(resp.getStatusCode() > 400 && resp.getStatusCode() < 600)
        {
            log.error("Served (" + resp.getStatusCode() + ") " + resp.getStatusMessage() + " for filepath " + filePath);
        }
        else
        {
            log.info("Served (" + resp.getStatusCode() + ") " + resp.getStatusMessage() + " for filepath " + filePath);
        }

    }
}
