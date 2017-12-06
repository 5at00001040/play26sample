package domain

import javax.inject.{Inject, Singleton}

import models.originator.{EoQuestionModel, QuestionModel, SaQuestionModel}
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class QuestionService @Inject()(dc: DatabaseConfigProvider)(saq: SaQuestionService)(eoq: EoQuestionService) {

  /**
    * 質問の情報をDBに登録する
    * @param q 作成する質問の情報
    * @return
    */
  def createQuestion(q: QuestionModel): Future[Long] = {
    q match {
      case sa: SaQuestionModel => saq.createQuestion(sa)
      case eo: EoQuestionModel => eoq.createQuestion(eo)
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
      case eo: EoQuestionModel => eoq.updateQuestion(eo)
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
    val sa = saq.readQuestion(id, surveyId)
    val eo = eoq.readQuestion(id, surveyId)
    (sa zip eo).map(q => q._1 ++ q._2)
  }

  /**
    * 質問を削除する
    * @param id 削除対象のid
    * @return
    */
  def deleteQuestion(qtype: String, id: Long): Future[Int] = {
    if (qtype == "sa") {
      saq.deleteQuestion(id)
    } else {
      eoq.deleteQuestion(id)
    }
  }
}
