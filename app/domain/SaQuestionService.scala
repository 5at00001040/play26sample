package domain

import javax.inject.{Inject, Singleton}

import models.SaQuestionModel
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SaQuestionService @Inject()(dc: DatabaseConfigProvider) {

  def create(q: SaQuestionModel): Future[Long] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      SaQuestion returning SaQuestion.map(_.id) += SaQuestionRow(
        id = 0,
        question = q.question,
        choice1 = q.choice1,
        choice2 = q.choice2,
        choice3 = q.choice3,
        choice4 = q.choice4,
        choice5 = q.choice5,
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

  def read(id: Option[Long]): Future[Seq[SaQuestionModel]] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = if (id.isDefined) {
      SaQuestion.filter(_.id === id.get).result
    } else {
      SaQuestion.result
    }

    val res: Future[Seq[SaQuestionRow]] =
      dbConfig.db.run(query)

    res.map(
      _.map(r =>
        SaQuestionModel(
          id = Some(r.id),
          question = r.question,
          choice1 = r.choice1,
          choice2 = r.choice2,
          choice3 = r.choice3,
          choice4 = r.choice4,
          choice5 = r.choice5,
          createAt = Some(r.createAt.toString),
          updateAt = Some(r.updateAt.toString)
      )))
  }

}
