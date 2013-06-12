package controllers;

import java.util.List;

import models.Archetype;
import models.Benutzer;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
    	Benutzer benutzer = Benutzer.find.byId(request().username());
    	List<Archetype> names = Archetype.find.all();
        return ok(index.render(benutzer,names));
    }

    public static Result logout(){
        session().clear();
        flash("success","You've been logged out");
        return redirect(routes.LoginController.login());
    }
}
