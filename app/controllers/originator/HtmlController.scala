package controllers.originator

import javax.inject._
import play.api.mvc._

@Singleton
class HtmlController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def surveyList() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.originator.survey_list())
  }

  def questionList(surveyId: Long) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.originator.question_list(surveyId))
  }

  def surveyResult(surveyId: Long) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.originator.survey_result(surveyId))
  }

}
