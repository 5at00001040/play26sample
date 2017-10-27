package controllers.spike

import javax.inject._
import java.util.Calendar

import domain.SaQuestionService
import models.{SaQuestionListResult, SaQuestionModel, SaQuestionRequest, SaQuestionResult}
import models.spike.FooModel
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

@Singleton
class ApiController @Inject()(cc: ControllerComponents)(qs: SaQuestionService)(
    dc: DatabaseConfigProvider)
    extends AbstractController(cc) {

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
    val foo: Future[Seq[KeyValueRow]] =
      dbConfig.db.run(KeyValue.filter(_.value === "foo").result)

    val bar: Future[FooModel] = foo.map(x => {
      val baz = x.map(y => FooModel(y.key, y.value.getOrElse("")))
      baz.last
    })
    bar.map(x => Ok(Json.toJson(x)))
  }


  def api004_3() = Action(parse.json).async { request =>

    val placeResult = request.body.validate[SaQuestionRequest]
    val createId = placeResult.map(j => qs.create(j.req)).getOrElse(Future(0))
    createId.map(x => FooModel("create: ", x.toString)).map(y => Ok(Json.toJson(y)))

  }



  def api005() = Action.async {

    val dbConfig = dc.get[JdbcProfile]
    val foo: Future[Seq[SaQuestionRow]] =
      dbConfig.db.run(SaQuestion/*.filter(_.choice1 === "choice1")*/.result)

    val bar: Future[SaQuestionListResult] = foo.map(x => {
      val baz = x.map(
        y =>
          SaQuestionModel(
            id = Some(y.id),
            choice1 = y.choice1,
            choice2 = y.choice2,
            choice3 = y.choice3,
            choice4 = y.choice4,
            choice5 = y.choice5,
            createAt = Some(y.createAt.toString),
            updateAt = Some(y.updateAt.toString)
        ))

      SaQuestionListResult(baz)
    })

    bar.map(x => Ok(Json.toJson(x)))
  }

}
