package orange;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.deploy.Verticle;

public class Orange extends Verticle {

    public void start() {

	RouteMatcher rm = new RouteMatcher();

/*
    rm.get("/details/:user/:id", new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        req.response.end("User: " + req.params().get("user") + " ID: " + req.params().get("id"));
      }
    });
*/

	// Catch all - serve the index page
	rm.getWithRegEx(".*", new Handler<HttpServerRequest>() {
	    public void handle(HttpServerRequest req) {
                System.out.println("Got request: " + req.uri);
                System.out.println("Headers are: ");
                for (String key : req.headers().keySet()) {
                    System.out.println(key + ":" + req.headers().get(key));
                }

		req.response.sendFile("index.html");
                //req.response.headers().put("Content-Type", "text/html; charset=UTF-8");
                //req.response.end("<html><body><h1>Hello from vert.x!</h1></body></html>");
	    }
	});

	vertx.createHttpServer().requestHandler(rm).listen(8080);
    }
}
