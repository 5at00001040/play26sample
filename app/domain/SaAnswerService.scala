package domain

import javax.inject.{Inject, Singleton}

import models.SaAnswerModel
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


@Singleton
class SaAnswerService @Inject()(dc: DatabaseConfigProvider) {

  def create(questionId: Long, choice: Int): Future[Long] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      SaAnswer returning SaAnswer.map(_.id) += SaAnswerRow(
        id = 0,
        questionId = questionId,
        choice = choice,
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

  def read(questionId: Long): Future[Seq[SaAnswerModel]] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = SaAnswer.filter(_.questionId === questionId).result

    val res: Future[Seq[SaAnswerRow]] = dbConfig.db.run(query)

    res.map(
      _.map(r =>
        SaAnswerModel(
          id = r.id,
          questionId = r.questionId,
          choice = r.choice,
          createAt = Some(r.createAt.toString),
          updateAt = Some(r.updateAt.toString)
        )))
  }

}
