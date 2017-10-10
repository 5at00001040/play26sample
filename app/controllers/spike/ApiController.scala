package controllers.spike

import javax.inject._
import java.util.Calendar

import models.spike.FooModel
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ApiController @Inject()(cc: ControllerComponents)(dc: DatabaseConfigProvider) extends AbstractController(cc) {

  def api001() = Action {
    val fooModel = FooModel("key", "value")
    Ok(Json.toJson(fooModel))
  }

  def api002() = Action {

    val now = "%tF %<tT" format Calendar.getInstance().getTime

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      KeyValue += KeyValueRow(now, Some("value"))
    )

    dbConfig.db.run(
      KeyValue ++= Seq(
        KeyValueRow(s"$now #1", Some("foo")),
        KeyValueRow(s"$now #2", Some("bar")),
        KeyValueRow(s"$now #3", Some("baz"))
      )
    )

    val fooModel = FooModel("create", "success")
    Ok(Json.toJson(fooModel))
  }

  def api003() = Action.async {

    val dbConfig = dc.get[JdbcProfile]
    val foo: Future[Seq[KeyValueRow]] = dbConfig.db.run(KeyValue.filter(_.value === "foo").result)

    val bar: Future[FooModel] = foo.map(x => {
      val baz = x.map(y => FooModel(y.key, y.value.getOrElse("")))
      baz.last
    })
    bar.map(x => Ok(Json.toJson(x)))
  }

}
