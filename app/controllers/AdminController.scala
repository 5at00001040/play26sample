package controllers

import javax.inject._

import domain.SaQuestionService
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

@Singleton
class AdminController @Inject()(cc: ControllerComponents)(qs: SaQuestionService)(
  dc: DatabaseConfigProvider)
  extends AbstractController(cc) {

  def reset() = Action {

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(SaAnswer.delete)
    dbConfig.db.run(SaQuestion.delete)

    Ok(Json.toJson(Map("res" -> "Reset OK")))
  }

}
