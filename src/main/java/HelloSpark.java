/**
 * Created by suhail on 5/22/15.
 */
import static spark.Spark.*;
import spark.*;

public class HelloSpark {
    public static void main(String[] args) {
        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello Spark MVC Framework!";
            }
        });
    }
}