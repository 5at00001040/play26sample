package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import play.api._
import play.api.mvc._
import utils.auth.DefaultEnv

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(silhouette: Silhouette[DefaultEnv]) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = silhouette.SecuredAction /* Action */ { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
