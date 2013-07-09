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

    @Security.Authenticated(Secured.class)
    public static Result index() {
        response().setCookie("user", request().username());
        Benutzer benutzer = Benutzer.find.byId(request().username());
        List<Archetype> names = Archetype.find.all();
        return ok(index.render(benutzer, names));
    }

    public static Result showArchetypeModal(String archetypeId) {
        Archetype arche = Archetype.find.byId(archetypeId);
        return ok(modalRemote.render(arche));
    }

    public static Result showForm(String archetypeID) {
        Archetype arche = Archetype.find.byId(archetypeID);
        Benutzer benutzer = Benutzer.find.byId(session("accountname"));
        List<Archetype> names = Archetype.find.all();
        return ok(showForm.render(benutzer, names, arche));
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out.");
        return redirect(routes.LoginController.login());
    }


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
//        new Daten(session("accountname"), arche, dynForm.get(String.valueOf(arche.getElements().get(0).getId())), dynForm.get("choice"));
//
        flash("saved", "Data has been saved succesfully.");
        return redirect(routes.Application.showForm(archeID));
    }


    public static boolean dataInDB(Archetype arche) {
        return Daten.find.where().eq("archetype", arche).eq("userID", session("accountname")).findList().size() > 0;
    }

    public static boolean allDataInDB(Archetype arche) {
        for (String archeID : arche.getUsedArchetypes()) {
            if (!dataInDB(Archetype.find.byId(archeID))) {
                return false;
            }
        }
        return true;
    }

    public static Daten getDataFromDB(Archetype arche) {
        return Daten.find.where().eq("archetype", arche).eq("userID", session("accountname")).findUnique();
    }


    /**
     * BMI = m/l^2
     * @return
     */
    public static int calculateBMI() {
        Daten d = Daten.find.where().eq("archetype.name", "PatientBodyWeight").eq("userID", session("accountname")).findUnique();
        double m = Double.valueOf(d.getValue());
        if (d.getSelected().equals("lb")){
            m *= 0.45359237;
        }

        d = Daten.find.where().eq("archetype.name","PatientBodyHeight").eq("userID",session("accountname")).findUnique();
        double l = Double.valueOf(d.getValue());
        if (d.getSelected().equals("foot")){
            l *= 0.3048;
        }else{
            l /= 100;
        }

        return (int) (m/(l*l));
    }

    public static String getPhysician() {
        Daten d = Daten.find.where().eq("archetype.name","Physician").eq("userID",session("accountname")).findUnique();
        int physicianIndex = Integer.valueOf(d.getValue());
        Archetype type = d.getArchetype();
        return type.getElements().get(0).getChoices().get(physicianIndex).getChoice();
    }
}
