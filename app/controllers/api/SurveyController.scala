package controllers.api

import javax.inject._

import domain.SurveyService
import models.originator._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SurveyController @Inject()(cc: ControllerComponents)(ss: SurveyService)(
    dc: DatabaseConfigProvider)
    extends AbstractController(cc) {

  def postSurvey() = Action(parse.json).async { request =>
    val placeResult = request.body.validate[SurveyRequest]

    val createId: Future[Long] = placeResult
      .map(j => ss.createSurvey(j.req.title))
      .getOrElse(Future(0))
    createId.map(x => Ok(Json.toJson(Map("res" -> Map("id" -> x)))))
  }

  def getSurvey(id: Long) = Action.async {
    ss.readSurvey(Some(id)).map(x => SurveyResponse(x.head)).map(y => Ok(Json.toJson(y)))
  }

  def getAllSurvey() = Action.async {
    ss.readSurvey(None).map(x => SurveyListResponse(x)).map(y => Ok(Json.toJson(y)))
  }

  def getSurveyResult(id: Long) = Action.async {
    ss.readSurveyResult(id).map(x => SurveyResultResponse(x)).map(y => Ok(Json.toJson(y)))
  }

  def deleteSurvey(id: Long) = Action.async {
    ss.deleteSurvey(id).map(x => Ok(Json.toJson(x)))
  }

}
