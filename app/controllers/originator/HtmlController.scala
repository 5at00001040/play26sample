package controllers.originator

import javax.inject._
import play.api.mvc._

@Singleton
class HtmlController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def surveyList() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.survey_list())
  }



}
