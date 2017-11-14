package controllers.api

import javax.inject._

import domain.SaQuestionService
import models._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SaQuestionController @Inject()(cc: ControllerComponents)(qs: SaQuestionService)(
  dc: DatabaseConfigProvider)
  extends AbstractController(cc) {

  def postQuestion() = Action(parse.json).async { request =>
    val placeResult = request.body.validate[SaQuestionRequest]
    val createId: Future[Long] = placeResult.map(j => qs.createQuestion(j.req)).getOrElse(Future(0))
    createId
      .map(x => SaQuestionResult(SaQuestionModel(id = Some(x))))
      .map(y => Ok(Json.toJson(y)))
  }

  def putQuestion() = Action(parse.json).async { request =>
    val placeResult = request.body.validate[SaQuestionRequest]
    val updateCount: Future[Int] = placeResult.map(j => qs.updateQuestion(j.req)).getOrElse(Future(0))
    updateCount
      .map(x => SaQuestionResult(SaQuestionModel(id = Some(x))))
      .map(y => Ok(Json.toJson(y)))
  }

  def getAllQuestion() = Action.async {
    qs.readQuestion(None, None).map(x => SaQuestionListResult(x)).map(y => Ok(Json.toJson(y)))
  }

  def getQuestionBySurveyId(surveyId: Long) = Action.async {
    qs.readQuestion(None, Some(surveyId)).map(x => SaQuestionListResult(x)).map(y => Ok(Json.toJson(y)))
  }

  def getQuestion(id: Long) = Action.async {
    qs.readQuestion(Some(id), None).map(x => SaQuestionListResult(x)).map(y => Ok(Json.toJson(y)))
  }

  def deleteQuestion(id: Long) = Action(parse.json).async {
    val deleteCount: Future[Int] = qs.deleteQuestion(id)
    deleteCount
      .map(x => SaQuestionResult(SaQuestionModel(id = Some(x))))
      .map(y => Ok(Json.toJson(y)))
  }

  def postAnswer() = Action(parse.json).async { request =>
    val placeResult = request.body.validate[SaAnswerRequest]

    val (questionId, choice) = placeResult.map(a => (a.req.questionId, a.req.choice)).getOrElse((0L, 0))

    val createId: Future[Long] = placeResult.map(j => qs.createAnswer(questionId, choice)).getOrElse(Future(0))
    createId
      .map(x => SaQuestionResult(SaQuestionModel(id = Some(x))))
      .map(y => Ok(Json.toJson(y)))
  }

  def getAnswer(id: Long) = Action.async {
    qs.countAnswer(id).map(x => SaAnswerCountModel(x._1, x._2, x._3, x._4, x._5)).map(y => Ok(Json.toJson(y)))
  }
}
