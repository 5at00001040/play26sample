package domain

import javax.inject.{Inject, Singleton}

import models.originator.{QuestionModel, SaQuestionModel}
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class QuestionService @Inject()(dc: DatabaseConfigProvider)(saq: SaQuestionService) {

  /**
    * 質問の情報をDBに登録する
    * @param q 作成する質問の情報
    * @return
    */
  def createQuestion(q: QuestionModel): Future[Long] = {
    q match {
      case sa: SaQuestionModel => saq.createQuestion(sa)
      case _ => ???
    }
  }

  /**
    * 質問の内容を更新する
    * @param q 更新内容
    * @return
    */
  def updateQuestion(q: QuestionModel): Future[Int] = {
    q match {
      case sa: SaQuestionModel => saq.updateQuestion(sa)
      case _ => ???
    }
  }

  /**
    * 質問をDBから読み込む
    * パラメーターで指定されたアンケートのリストを返す
    * @param id 検索対象の質問のid
    * @param surveyId アンケートのid
    * @return
    */
  def readQuestion(id: Option[Long], surveyId: Option[Long]): Future[Seq[QuestionModel]] = {
    saq.readQuestion(id, surveyId)
  }

  /**
    * 質問を削除する
    * @param id 削除対象のid
    * @return
    */
  def deleteQuestion(id: Long): Future[Int] = {
    saq.deleteQuestion(id)
  }


  // TODO 以下別クラスに切り出す

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
        case (_, grp) =>
          (grp.map(x => x.choice1).sum.getOrElse(0),
           grp.map(x => x.choice2).sum.getOrElse(0),
           grp.map(x => x.choice3).sum.getOrElse(0),
           grp.map(x => x.choice4).sum.getOrElse(0),
           grp.map(x => x.choice5).sum.getOrElse(0))
      }
      .result

    val res: Future[Seq[(Int, Int, Int, Int, Int)]] = dbConfig.db.run(query)
    res.map(_.head)
  }
}
