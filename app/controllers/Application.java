package controllers;

import java.util.List;

import models.Archetype;
import models.Benutzer;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
    	Benutzer benutzer = Benutzer.find.byId(request().username());
    	List<Archetype> names = Archetype.find.all();
    	
    	for(Archetype a : names) {
    		System.out.println(a.getName());
    		System.out.println(a.getElements());
    	}
    	
        return ok(index.render(benutzer,names));
    }

    public static Result logout(){
        session().clear();
        flash("success","You've been logged out");
        return redirect(routes.LoginController.login());
    }
}
