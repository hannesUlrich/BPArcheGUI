package controllers;

import models.*;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;


/**
 * Created with IntelliJ IDEA.
 * Benutzer: Crassus
 * Date: 08.06.13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx){
        return ctx.session().get("accountname");
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.LoginController.login());
    }

}
