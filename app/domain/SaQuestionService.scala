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
        surveyId = 0, // TODO
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
        choice1 = if (choice == 1) Some(1) else None,
        choice2 = if (choice == 2) Some(1) else None,
        choice3 = if (choice == 3) Some(1) else None,
        choice4 = if (choice == 4) Some(1) else None,
        choice5 = if (choice == 5) Some(1) else None,
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

  /**
    * 指定されたquestionIdの回答結果の合計を返す
    * @param questionId 質問id
    * @return
    */
  def countAnswer(questionId: Long): Future[(Int, Int, Int, Int, Int)] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = SaAnswer
      .filter(_.questionId === questionId)
      .groupBy(_.questionId)
      .map {
        case (_, grp) => {
          (grp.map(x => x.choice1).sum.getOrElse(0),
           grp.map(x => x.choice2).sum.getOrElse(0),
           grp.map(x => x.choice3).sum.getOrElse(0),
           grp.map(x => x.choice4).sum.getOrElse(0),
           grp.map(x => x.choice5).sum.getOrElse(0))
        }
      }
      .result

    val res: Future[Seq[(Int, Int, Int, Int, Int)]] = dbConfig.db.run(query)
    res.map(_.head)
  }
}
