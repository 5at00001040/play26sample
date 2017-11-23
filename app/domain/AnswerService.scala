package domain

import javax.inject.{Inject, Singleton}

import models.user.{AnswerModel, EoAnswerModel, SaAnswerModel}
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AnswerService @Inject()(dc: DatabaseConfigProvider) {

  def createAnswer(al: Seq[AnswerModel]): Future[Int] = {

    val res = al.map {
      case sa: SaAnswerModel => createSaAnswer(sa)
      case eo: EoAnswerModel => createEoAnswer(eo)
      case _ => ???
    }

    res.reduce((a, b) => (a zip b).map(x => x._1 + x._2))
  }

  def createSaAnswer(a: SaAnswerModel): Future[Int] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      SaAnswer += SaAnswerRow(
        id = 0,
        questionId = a.questionId,
        choice1 = if (a.choice == 1) Some(1) else None,
        choice2 = if (a.choice == 2) Some(1) else None,
        choice3 = if (a.choice == 3) Some(1) else None,
        choice4 = if (a.choice == 4) Some(1) else None,
        choice5 = if (a.choice == 5) Some(1) else None,
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

  def createEoAnswer(a: EoAnswerModel): Future[Int] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      EoAnswer += EoAnswerRow(
        id = 0,
        questionId = a.questionId,
        choice = Some(a.choice),
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

}
