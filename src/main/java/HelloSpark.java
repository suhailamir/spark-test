/**
 * Created by suhail on 5/22/15.
 */
import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.*;
import spark.Request;
import spark.Response;
import java.util.*;

public class HelloSpark {
    private static Gson GSON = new GsonBuilder().create();
    public static ContactDbService<Contact> contactDbService = new ContactMongoDao<Contact>();
    public static void main(String[] args) {
        enableCORS("*", "*", "*");
        get(new Route("/contacts") {
            @Override
            public Object handle(Request request, Response response) {
                ArrayList<Contact> contacts = contactDbService.readAll();
                Gson gson = new Gson();
                return  gson.toJson(contacts);
            }
        });
        get(new Route("/get/contact/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                Gson cont = new Gson();
                return cont.toJson(contactDbService.readOne(id));
            }
        });
        post(new Route("/save/contact") {
            @Override
            public Object handle(Request request, Response response) {
                String name = request.queryParams("name");
                String email = request.queryParams("email");
                Contact contact = new Contact(name,email,contactDbService.readAll().size());
                contactDbService.create(contact);
                response.status(201);
                return "Contact saved successfully";
            }
        });
        post(new Route("/contact/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id      = Integer.parseInt(request.queryParams("id"));
                String name    = request.queryParams("name");
                String email  = request.queryParams("email");
                contactDbService.update(id, name, email);
                response.status(200);
                return "Contact Updated.";
            }
        });

        get(new Route("/contact/delete/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Integer id = Integer.parseInt(request.params(":id"));
                contactDbService.delete(id);
                response.status(200);
                return "Contact Deleted .";
            }
        });
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                response.status(200);
                return "";

            }
        });


    }
    //CORS
    private static void enableCORS(final String origin, final String methods, final String headers) {
        before(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", origin);
                response.header("Access-Control-Request-Method", methods);
                response.header("Access-Control-Allow-Headers", headers);
            }
        });
    }


}