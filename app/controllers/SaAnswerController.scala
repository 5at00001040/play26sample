package controllers

import javax.inject._

import domain.SaAnswerService
import models._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class SaAnswerController @Inject()(cc: ControllerComponents)(qs: SaAnswerService)(
  dc: DatabaseConfigProvider)
  extends AbstractController(cc) {

  def getByQuestionId(id: Long) = Action.async {
    qs.read(id).map(x => SaAnswerListResult(x)).map(y => Ok(Json.toJson(y)))
  }
}
