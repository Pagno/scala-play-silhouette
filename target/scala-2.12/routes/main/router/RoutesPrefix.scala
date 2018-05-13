// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/Matteo/Documents/Personale/Scala/Progetti/authentication/auth/conf/routes
// @DATE:Sun May 13 11:48:20 CEST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
