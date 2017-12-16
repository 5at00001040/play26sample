package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import play.api.mvc._
import utils.auth.DefaultEnv


@Singleton
class SpikeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def debugPage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.debug_page("test"))
  }

  def debugMsgPage(msg: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.debug_page(msg))
  }
}
