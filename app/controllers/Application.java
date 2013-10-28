package controllers;

import java.util.List;

import models.Archetype;
import models.Benutzer;
import models.Daten;
import models.Element;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Helper;
import views.html.index;
import views.html.modalRemote;
import views.html.showForm;

public class Application extends Controller {

    /**
     * Renders Index Page
     * @return HTML Index Page
     */
    @Security.Authenticated(Secured.class)
    public static Result index() {
        response().setCookie("user", request().username());
        Benutzer benutzer = Benutzer.find.byId(request().username());
        List<Archetype> names = Archetype.find.all();
        return ok(index.render(benutzer, names));
    }

    /**
     * Renders dialog for Archetype
     * @param archetypeId Archetype's ID
     * @return HTML text for dialog
     */
    public static Result showArchetypeModal(String archetypeId) {
        Archetype arche = Archetype.find.byId(archetypeId);
        return ok(modalRemote.render(arche));
    }

    /**
     *
     * @param archetypeID Archetype's ID
     * @return HTML page for Archetype
     */
    public static Result showForm(String archetypeID) {
        Archetype arche = Archetype.find.byId(archetypeID);
        Benutzer benutzer = Benutzer.find.byId(session("accountname"));
        List<Archetype> names = Archetype.find.all();
        return ok(showForm.render(benutzer, names, arche));
    }

    /**
     * Logout
     * @return HTML Login Page
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out.");
        return redirect(routes.LoginController.login());
    }

    /**
     * Changes active Theme
     * @return HTML Index Page
     */
    public static Result changeThemen() {
    	Benutzer benutzer = Benutzer.find.byId(session("accountname"));
    	if (benutzer.getThemenType() == 0 ) {
    		benutzer.setThemenType(1);
    	} else {
    		benutzer.setThemenType(0);
    	}
    	List<Archetype> names = Archetype.find.all();
        return ok(index.render(benutzer, names));
    }

    /**
     * Saves the Archetype Data
     * @param archeID Archetype's ID
     * @return HTML active Archetype Page
     */
    public static Result saveForm(String archeID) {
        DynamicForm dynForm = Form.form().bindFromRequest();
        Archetype arche = Archetype.find.byId(archeID);
        Daten data = getDataFromDB(arche);
        for (String s : dynForm.data().keySet()) {
            if (Helper.isInteger(s)) {
                String value = dynForm.data().get(s);
                String choice = dynForm.data().get("choice" + s);

                if (data != null) {
                    data.updateData(value, choice);
                } else {
                    new Daten(session("accountname"), arche, value, choice);
                }
            } else {
                if (s.matches("(choice[0-9]{1})")) {
                    String tmp = s.replaceAll("choice", "");
                    if (!dynForm.data().containsKey(tmp)) {
                        String value = dynForm.data().get(s);
                        if (data != null) {
                            data.updateData(value, "");
                        } else {
                            new Daten(session("accountname"), arche, value, "");
                        }

                    }
                }
            }
        }
        flash("saved", "Data has been saved succesfully.");
        return redirect(routes.Application.showForm(archeID));
    }

    /**
     * Checks if the data is saved in Database
     * @param arche Archetype
     * @return true or false
     */
    public static boolean dataInDB(Archetype arche) {
        return Daten.find.where().eq("archetype", arche).eq("userID", session("accountname")).findList().size() > 0;
    }

    /**
     * Checks if all data used by this archetype is saved in database
     * @param arche
     * @return true or false
     */
    public static boolean allDataInDB(Archetype arche) {
        for (String archeID : arche.getUsedArchetypes()) {
            if (!dataInDB(Archetype.find.byId(archeID))) {
                return false;
            }
        }
        return true;
    }

    /**
     * reads all saved data by this archetype from the database
     * @param arche
     * @return daten
     */
    public static Daten getDataFromDB(Archetype arche) {
        return Daten.find.where().eq("archetype", arche).eq("userID", session("accountname")).findUnique();
    }
}
