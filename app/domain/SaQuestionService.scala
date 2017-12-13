package domain

import javax.inject.{Inject, Singleton}

import models.originator.SaQuestionModel
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SaQuestionService @Inject()(dc: DatabaseConfigProvider) {

  /**
    * 質問の情報をDBに登録する
    * @param q 作成する質問の情報
    * @return
    */
  def createQuestion(q: SaQuestionModel): Future[Long] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      SaQuestion returning SaQuestion.map(_.id) += SaQuestionRow(
        id = 0,
        surveyId = q.surveyId.getOrElse(0),
        question = q.question,
        questionCount = q.questionCount,
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

  /**
    * 質問の内容を更新する
    * @param q 更新内容
    * @return
    */
  def updateQuestion(q: SaQuestionModel): Future[Int] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      SaQuestion.filter(_.id === q.id.get)
        .map(r => (r.question, r.questionCount, r.choice1, r.choice2, r.choice3, r.choice4, r.choice5, r.updateAt))
        .update((q.question, q.questionCount, q.choice1, q.choice2, q.choice3, q.choice4, q.choice5, nowTime))
      )
  }

  /**
    * 質問をDBから読み込む
    * パラメーターで指定されたアンケートのリストを返す
    * @param id 検索対象の質問のid
    * @param surveyId アンケートのid
    * @return
    */
  def readQuestion(id: Option[Long], surveyId: Option[Long]): Future[Seq[SaQuestionModel]] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = if (id.isDefined) {
      SaQuestion.filter(_.id === id.get).result
    } else if (surveyId.isDefined) {
      SaQuestion.filter(_.surveyId === surveyId.get).result
    } else {
      SaQuestion.result
    }

    val res: Future[Seq[SaQuestionRow]] = dbConfig.db.run(query)

    res.map(
      _.map(r =>
        SaQuestionModel(
          id = Some(r.id),
          surveyId = Some(r.surveyId),
          question = r.question,
          questionCount = r.questionCount,
          choice1 = r.choice1,
          choice2 = r.choice2,
          choice3 = r.choice3,
          choice4 = r.choice4,
          choice5 = r.choice5,
          createAt = Some(r.createAt.toString),
          updateAt = Some(r.updateAt.toString)
      )))
  }

  /**
    * 質問を削除する
    * @param id 削除対象のid
    * @return
    */
  def deleteQuestion(id: Long): Future[Int] = {
    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      SaQuestion.filter(_.id === id).delete
    )
  }
}
