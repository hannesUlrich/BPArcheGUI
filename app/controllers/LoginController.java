package controllers;

import models.Benutzer;
import play.data.*;
import play.mvc.*;
import views.html.*;

public class LoginController extends Controller{

    /**
     * Performs Login
     * @return Login HTML Page
     */
    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }

    /**
     * Checks if Login-Form has no errors
     * @return HTML Page login if Loginform has Errors else Main page
     */
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

    /**
     * Redirects to the Register-Form
     * @return HTML Page Register
     */
    public static Result showRegister(){
        return ok(register.render(Form.form(Register.class)));
    }

    /**
     * Registers a new user
     * @return Html Page Index
     */
    public static Result register(){
        Form<Register> loginForm = Form.form(Register.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(register.render(loginForm));
        } else {
            new Benutzer(loginForm.get().accountname,loginForm.get().password,loginForm.get().fullName,0).save();
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
