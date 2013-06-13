package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.Archetype;
import models.Benutzer;
import play.mvc.*;
import utils.Components;
import utils.Helper;
import utils.Module;
import views.html.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
    	Benutzer benutzer = Benutzer.find.byId(request().username());
    	List<Archetype> names = Archetype.find.all();
    	
    	for(Archetype a : names) {
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
