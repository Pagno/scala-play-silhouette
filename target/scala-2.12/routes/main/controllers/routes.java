// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/Matteo/Documents/Personale/Scala/Progetti/authentication/auth/conf/routes
// @DATE:Sun May 13 11:48:20 CEST 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseApplicationController ApplicationController = new controllers.ReverseApplicationController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseSignInController SignInController = new controllers.ReverseSignInController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseApplicationController ApplicationController = new controllers.javascript.ReverseApplicationController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseSignInController SignInController = new controllers.javascript.ReverseSignInController(RoutesPrefix.byNamePrefix());
  }

}
