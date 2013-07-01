package controllers;

import java.util.List;

import models.Archetype;
import models.Benutzer;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import views.html.modalRemote;
import views.html.showForm;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
    	response().setCookie("user", request().username());
    	Benutzer benutzer = Benutzer.find.byId(request().username());
    	List<Archetype> names = Archetype.find.all();
        return ok(index.render(benutzer,names));
    }
    public static Result showArchetypeModal(String archetypeId){
        Archetype arche = Archetype.find.byId(archetypeId);
        return ok(modalRemote.render(arche));
    }

    public static Result showForm(String archetypeID) {
    	Archetype arche = Archetype.find.byId(archetypeID);
    	Benutzer benutzer = Benutzer.find.byId(session("accountname"));
    	List<Archetype> names = Archetype.find.all();
    	return ok(showForm.render(benutzer,names,arche));
   }
    
    public static Result logout(){
        session().clear();
        flash("success","You've been logged out.");
        return redirect(routes.LoginController.login());
    }

    public static Result saveForm(String archeID){
        DynamicForm dynForm = Form.form().bindFromRequest();
        System.out.println( dynForm.data().toString());
        Archetype arche = Archetype.find.byId(archeID);
        return redirect(routes.LoginController.login());
    }
}
