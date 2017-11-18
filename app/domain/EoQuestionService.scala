package domain

import javax.inject.{Inject, Singleton}

import models.originator.EoQuestionModel
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class EoQuestionService @Inject()(dc: DatabaseConfigProvider) {

  def createQuestion(q: EoQuestionModel): Future[Long] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      EoQuestion returning EoQuestion.map(_.id) += EoQuestionRow(
        id = 0,
        surveyId = q.surveyId.getOrElse(0),
        question = q.question,
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

  def updateQuestion(q: EoQuestionModel): Future[Int] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      EoQuestion.filter(_.id === q.id.get)
        .map(r => (r.question, r.updateAt))
        .update((q.question, nowTime))
      )
  }

  def readQuestion(id: Option[Long], surveyId: Option[Long]): Future[Seq[EoQuestionModel]] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = if (id.isDefined) {
      EoQuestion.filter(_.id === id.get).result
    } else if (surveyId.isDefined) {
      EoQuestion.filter(_.surveyId === surveyId.get).result
    } else {
      EoQuestion.result
    }

    val res: Future[Seq[EoQuestionRow]] = dbConfig.db.run(query)

    res.map(
      _.map(r =>
        EoQuestionModel(
          id = Some(r.id),
          surveyId = Some(r.surveyId),
          questionType = Some("eo"),
          question = r.question,
          createAt = Some(r.createAt.toString),
          updateAt = Some(r.updateAt.toString)
      )))
  }

  def deleteQuestion(id: Long): Future[Int] = {
    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      EoQuestion.filter(_.id === id).delete
    )
  }
}
