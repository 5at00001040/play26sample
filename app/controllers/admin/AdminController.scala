package controllers.admin

import javax.inject._

import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

@Singleton
class AdminController @Inject()(cc: ControllerComponents)(dc: DatabaseConfigProvider)
    extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.admin())
  }

  def reset() = Action {

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(SaAnswer.delete)
    dbConfig.db.run(SaQuestion.delete)
    dbConfig.db.run(EoAnswer.delete)
    dbConfig.db.run(EoQuestion.delete)
    dbConfig.db.run(Survey.delete)

    Ok(Json.toJson(Map("res" -> "Reset OK")))
  }

}
