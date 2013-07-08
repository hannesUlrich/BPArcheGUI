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

    public static Result showRegister(){
        return ok(register.render(Form.form(Register.class)));
    }

    public static Result register(){
        Form<Register> loginForm = Form.form(Register.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(register.render(loginForm));
        } else {
            new Benutzer(loginForm.get().accountname,loginForm.get().password,loginForm.get().fullName).save();
            return redirect(routes.Application.index());
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
    public static class Register{
        public String accountname;
        public String password;
        public String fullName;

        public String validate() {
            if(Benutzer.find.byId(accountname)!=null){
                return "User already in Database";
            }
            if(accountname == null || accountname.equals("")){
                return "Accountname has to be defined";
            }

            if(password == null || password.equals("")){
                return "Password has to be defined";
            }

            if(fullName == null || fullName.equals("")){
                return "Fullname has to be defined";
            }
            return null;
        }

    }
}
