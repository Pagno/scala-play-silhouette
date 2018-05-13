
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object home extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[models.User,play.api.mvc.RequestHeader,play.api.i18n.MessagesProvider,org.webjars.play.WebJarAssets,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(user: models.User)(implicit request: play.api.mvc.RequestHeader, messages: play.api.i18n.MessagesProvider, webJarAssets: org.webjars.play.WebJarAssets):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<p>Ciao</p>
"""))
      }
    }
  }

  def render(user:models.User,request:play.api.mvc.RequestHeader,messages:play.api.i18n.MessagesProvider,webJarAssets:org.webjars.play.WebJarAssets): play.twirl.api.HtmlFormat.Appendable = apply(user)(request,messages,webJarAssets)

  def f:((models.User) => (play.api.mvc.RequestHeader,play.api.i18n.MessagesProvider,org.webjars.play.WebJarAssets) => play.twirl.api.HtmlFormat.Appendable) = (user) => (request,messages,webJarAssets) => apply(user)(request,messages,webJarAssets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun May 13 11:48:20 CEST 2018
                  SOURCE: /Users/Matteo/Documents/Personale/Scala/Progetti/authentication/auth/app/views/home.scala.html
                  HASH: 51935c90acbf5eeddf1df200d0a08f1d40f7dfe3
                  MATRIX: 1040->1|1286->154|1313->155
                  LINES: 28->1|33->2|34->3
                  -- GENERATED --
              */
          