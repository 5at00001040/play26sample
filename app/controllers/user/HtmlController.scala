package controllers.user

import javax.inject._

import play.api.mvc._

@Singleton
class HtmlController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index(surveyId: Long) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.user(surveyId))
  }

}
