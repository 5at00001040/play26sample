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

  /**
    * 質問をDBから読み込む
    * id指定の場合は対象の質問1件を返し、指定がない場合は全件取得する
    * @param id 検索対象の質問のid
    * @return
    */
  def readQuestion(id: Option[Long]): Future[Seq[SaQuestionModel]] = {

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

  /**
    * 質問の回答結果をDBに保存する
    * @param questionId 登録する回答に紐づく質問id
    * @param choice 選択した選択肢の番号
    * @return
    */
  def createAnswer(questionId: Long, choice: Int): Future[Long] = {

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

  /**
    * 質問ごとに回答結果の合計を返す
    * @param questionId 質問id
    * @return
    */
  def countAnswer(questionId: Long): Future[(Int, Int, Int, Int, Int)] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = SaAnswer
      .filter(_.questionId === questionId)
      .groupBy(_.choice)
      .map { case (s, results) => s -> results.length }
      .result

    val res: Future[Seq[(Int, Int)]] = dbConfig.db.run(query)

    res.map(a => {
      a.map {
          case (1, x) => (x, 0, 0, 0, 0)
          case (2, x) => (0, x, 0, 0, 0)
          case (3, x) => (0, 0, x, 0, 0)
          case (4, x) => (0, 0, 0, x, 0)
          case (5, x) => (0, 0, 0, 0, x)
          case _      => (0, 0, 0, 0, 0)
        }
        .foldLeft((0, 0, 0, 0, 0)) { (a, b) =>
          (a._1 + b._1, a._2 + b._2, a._3 + b._3, a._4 + b._4, a._5 + b._5)
        }
    })

  }
}
