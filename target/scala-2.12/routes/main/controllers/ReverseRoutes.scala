// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/Matteo/Documents/Personale/Scala/Progetti/authentication/auth/conf/routes
// @DATE:Sun May 13 11:48:20 CEST 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F
import _root_.utils.route.Binders._

// @LINE:5
package controllers {

  // @LINE:5
  class ReverseApplicationController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:8
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:10
  class ReverseSignInController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def view(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "signin")
    }
  
    // @LINE:12
    def submit(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "signin")
    }
  
  }


}
