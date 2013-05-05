import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.deploy.Verticle;
import java.util.HashSet;
import java.io.File;

public class Orange extends Verticle 
{

    private HashSet<String> validFiles = new HashSet<String>();

    private final String FRONTEND_FILES_PREFIX = "/home/azureuser/orange/frontend";

    private final int HTTP_PORT = 8080;

    public void start() 
    {

	RouteMatcher rm = new RouteMatcher();


        // serve js and css
        rm.getWithRegEx("/(js|css)/.*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                System.out.println("Serving " + FRONTEND_FILES_PREFIX + req.uri);
                req.response.sendFile(FRONTEND_FILES_PREFIX + req.uri);
            }
        });

        // serve index
	rm.getWithRegEx("(/|index.html)", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                System.out.println("Serving " + FRONTEND_FILES_PREFIX + "/index.html");
                req.response.sendFile(FRONTEND_FILES_PREFIX + "/index.html");
	    }
	});


        // serve 404
	rm.getWithRegEx(".*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) 
            {
                System.out.println("Got request: " + req.uri);

                System.out.println("Headers are: ");
                for (String key : req.headers().keySet()) {
                    System.out.println(key + ":" + req.headers().get(key));
                }

                // serve 404
                System.out.println("Serving 404");
                req.response.end("404 Not Found");
	    }
	});

	vertx.createHttpServer().requestHandler(rm).listen(HTTP_PORT);

    }
}
