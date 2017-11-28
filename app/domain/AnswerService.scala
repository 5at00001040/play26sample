package domain

import javax.inject.{Inject, Singleton}

import models.originator.{EoQuestionModel, QuestionModel, SaQuestionModel}
import models.user._
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
      case _                 => ???
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

  def countAnswer(qm: QuestionModel): Future[AnswerSummaryModel] = {
    qm match {
      case sa: SaQuestionModel => countSaAnswer(sa.id.get)
      case eo: EoQuestionModel => countEoAnswer(eo.id.get)
      case _                   => ???
    }
  }

  def countSaAnswer(questionId: Long): Future[SaAnswerSummaryModel] = {

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
    res
      .map(x => x.headOption.getOrElse((0, 0, 0, 0, 0)))
      .map(x => SaAnswerSummaryModel(x._1, x._2, x._3, x._4, x._5))
  }

  def countEoAnswer(questionId: Long): Future[EoAnswerSummaryModel] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = EoAnswer
      .filter(_.questionId === questionId)
      .map(_.choice)
      .result
    val res: Future[Seq[Option[Int]]] = dbConfig.db.run(query)

    val count = res
      .map(x =>
        x.flatMap(y =>
          y.map {
            case 1 => (1, 0)
            case 0 => (0, 1)
            case _ => (0, 0)
        }))
      .map(x => x.fold((0, 0))((a, b) => (a._1 + b._1, a._2 + b._2)))

    count.map(x => EoAnswerSummaryModel(x._1, x._2))
  }

}
