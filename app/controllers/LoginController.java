package controllers;

import models.Benutzer;
import play.data.*;
import play.mvc.*;
import views.html.*;

/**
 * Created with IntelliJ IDEA.
 * Benutzer: Crassus
 * Date: 08.06.13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class LoginController extends Controller{

    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }

    public static Result authenticate(){
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("accountname", loginForm.get().accountname);
            return redirect(
                    routes.Application.index()
            );
        }
    }


    public static class Login{
        public String accountname;
        public String password;

        public String validate() {
            if (Benutzer.authenticate(accountname, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }
}
