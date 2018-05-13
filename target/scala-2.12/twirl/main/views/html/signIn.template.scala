
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

object signIn extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[play.api.data.Form[forms.SignInForm.Data],com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry,play.api.mvc.RequestHeader,play.api.i18n.MessagesProvider,org.webjars.play.WebJarAssets,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(signInForm: play.api.data.Form[forms.SignInForm.Data], socialProviders: com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry)(implicit request: play.api.mvc.RequestHeader, messages: play.api.i18n.MessagesProvider, webJarAssets: org.webjars.play.WebJarAssets):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*4.2*/import play.mvc.Http.Context.Implicit

implicit def /*3.2*/implicitFieldConstructor/*3.26*/ = {{ b3.vertical.fieldConstructor() }};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.64*/("""
"""),format.raw/*5.1*/("""
"""),_display_(/*6.2*/main("sign.in.title")/*6.23*/ {_display_(Seq[Any](format.raw/*6.25*/("""
"""),format.raw/*7.1*/("""<fieldset class="col-md-6 col-md-offset-3">
    <legend>sign.in.credentials"</legend>
    """),_display_(/*9.6*/b3/*9.8*/.vertical.form(routes.SignInController.submit)/*9.54*/ { implicit vfc =>_display_(Seq[Any](format.raw/*9.72*/("""
    """),_display_(/*10.6*/b3/*10.8*/.email(signInForm("email"), '_label -> "email", 'placeholder -> "email")),format.raw/*10.80*/("""
    """),_display_(/*11.6*/b3/*11.8*/.password(signInForm("password"), '_label -> "password", 'placeholder -> Messages("password"), 'class -> "form-control input-lg")),format.raw/*11.137*/("""
    """),_display_(/*12.6*/b3/*12.8*/.checkbox(signInForm("rememberMe"), '_text -> "remember.me", 'checked -> true)),format.raw/*12.86*/("""
    """),format.raw/*13.5*/("""<div class="form-group">
        <div>
            <button id="submit" type="submit" value="submit" class="btn btn-lg btn-primary btn-block">"sign.in"</button>
        </div>
    </div>
    """)))}),format.raw/*18.6*/("""

    """),format.raw/*20.5*/("""<div>
        <p class="not-a-member">"not.a.member"
            """),format.raw/*25.15*/("""
        """),format.raw/*26.9*/("""</p>
    </div>


</fieldset>
""")))}),format.raw/*31.2*/("""
"""))
      }
    }
  }

  def render(signInForm:play.api.data.Form[forms.SignInForm.Data],socialProviders:com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry,request:play.api.mvc.RequestHeader,messages:play.api.i18n.MessagesProvider,webJarAssets:org.webjars.play.WebJarAssets): play.twirl.api.HtmlFormat.Appendable = apply(signInForm,socialProviders)(request,messages,webJarAssets)

  def f:((play.api.data.Form[forms.SignInForm.Data],com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry) => (play.api.mvc.RequestHeader,play.api.i18n.MessagesProvider,org.webjars.play.WebJarAssets) => play.twirl.api.HtmlFormat.Appendable) = (signInForm,socialProviders) => (request,messages,webJarAssets) => apply(signInForm,socialProviders)(request,messages,webJarAssets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun May 13 11:48:20 CEST 2018
                  SOURCE: /Users/Matteo/Documents/Personale/Scala/Progetti/authentication/auth/app/views/signIn.scala.html
                  HASH: 10a1f24575f265fda66d47b3c01392fe28748f49
                  MATRIX: 1137->1|1481->339|1540->275|1572->299|1639->273|1667->337|1694->377|1721->379|1750->400|1789->402|1816->403|1932->494|1941->496|1995->542|2050->560|2082->566|2092->568|2185->640|2217->646|2227->648|2378->777|2410->783|2420->785|2519->863|2551->868|2772->1059|2805->1065|2898->1372|2934->1381|2995->1412
                  LINES: 28->1|31->4|33->3|33->3|34->2|35->3|36->5|37->6|37->6|37->6|38->7|40->9|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|43->12|43->12|43->12|44->13|49->18|51->20|53->25|54->26|59->31
                  -- GENERATED --
              */
          