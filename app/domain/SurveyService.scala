package domain

import javax.inject.{Inject, Singleton}

import models.originator.{QuestionModel, SaQuestionModel, SurveyModel}
import models.user.{AnswerModel, AnswerSummaryModel}
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SurveyService @Inject()(dc: DatabaseConfigProvider)(qs: QuestionService)(
    as: AnswerService) {

  /**
    * アンケートをDBに登録する
    * @param title 作成する質問の情報
    * @return survey id
    */
  def createSurvey(title: String): Future[Long] = {

    val date = new java.util.Date()
    val nowTime =
      new java.sql.Timestamp(new org.joda.time.DateTime(date).getMillis)

    val dbConfig = dc.get[JdbcProfile]
    dbConfig.db.run(
      Survey returning Survey.map(_.id) += SurveyRow(
        id = 0,
        surveyTitle = Some(title),
        createAt = nowTime,
        updateAt = nowTime
      )
    )
  }

  /**
    * アンケートをDBから読み込む
    * id指定の場合は対象のアンケート1件を返し、指定がない場合は全件取得する
    * @param id 検索対象の質問のid
    * @return
    */
  def readSurvey(id: Option[Long]): Future[Seq[SurveyModel]] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = if (id.isDefined) {
      Survey.filter(_.id === id.get).result
    } else {
      Survey.result
    }

    val res: Future[Seq[SurveyRow]] =
      dbConfig.db.run(query)

    res.map(
      _.map(
        r =>
          SurveyModel(
            id = Some(r.id),
            title = r.surveyTitle,
            createAt = Some(r.createAt.toString),
            updateAt = Some(r.updateAt.toString)
        )))
  }

  def readSurveyResult(
      id: Long): Future[Seq[(QuestionModel, AnswerSummaryModel)]] = {

    val ql: Future[Seq[QuestionModel]] = qs.readQuestion(None, Some(id))
//    val al: Future[Seq[Future[AnswerSummaryModel]]] = ql.map(_.map(x => as.countAnswer(x)))
//    val fal: Future[Seq[AnswerSummaryModel]] = al.map(Future.sequence(_)).flatten
    val al: Future[Seq[AnswerSummaryModel]] =
      ql.map(_.map(x => as.countAnswer(x))).map(Future.sequence(_)).flatten

    (ql zip al).map(x => x._1 zip x._2)

  }
}
