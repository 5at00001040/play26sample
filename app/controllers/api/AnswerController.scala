package controllers.api

import javax.inject.{Inject, Singleton}

import domain.AnswerService
import models.originator.{QuestionResult, SaQuestionModel}
import models.user.AnswerListRequest
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AnswerController @Inject()(cc: ControllerComponents)(as: AnswerService)(
  dc: DatabaseConfigProvider)
  extends AbstractController(cc) {

  def postAnswer() = Action(parse.json).async { request =>
    val placeResult = request.body.validate[AnswerListRequest]
    val createCount: Future[Int] = placeResult.map(j => as.createAnswer(j.req)).getOrElse(Future.successful(0))
    createCount
//      .map(x => QuestionResult(SaQuestionModel(id = Some(x))))
      .map(_ => Ok(Json.toJson(Map("res" -> "OK")	)))
  }

}
