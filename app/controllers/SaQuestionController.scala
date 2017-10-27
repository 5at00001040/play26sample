package controllers

import javax.inject._

import domain.SaQuestionService
import models.{SaQuestionListResult, SaQuestionModel, SaQuestionRequest, SaQuestionResult}
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SaQuestionController @Inject()(cc: ControllerComponents)(qs: SaQuestionService)(
  dc: DatabaseConfigProvider)
  extends AbstractController(cc) {


//  def survey() = Action {
//    Ok(views.html.survey())
//  }

  def post() = Action(parse.json).async { request =>
    val placeResult = request.body.validate[SaQuestionRequest]
    val createId: Future[Long] = placeResult.map(j => qs.create(j.req)).getOrElse(Future(0))
    createId
      .map(x => SaQuestionResult(SaQuestionModel(id = Some(x))))
      .map(y => Ok(Json.toJson(y)))
  }

  def getAll() = Action.async {
    qs.read(None).map(x => SaQuestionListResult(x)).map(y => Ok(Json.toJson(y)))
  }

  def get(id: Long) = Action.async {
    qs.read(Some(id)).map(x => SaQuestionListResult(x)).map(y => Ok(Json.toJson(y)))
  }


}
