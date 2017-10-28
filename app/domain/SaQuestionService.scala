package domain

import javax.inject.{Inject, Singleton}

import models.{SaAnswerModel, SaQuestionAnswer, SaQuestionModel}
import persistence.models.Tables._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SaQuestionService @Inject()(dc: DatabaseConfigProvider) {

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

  def readAnswer(questionId: Long): Future[Seq[SaAnswerModel]] = {

    val dbConfig = dc.get[JdbcProfile]

    val query = SaAnswer.filter(_.questionId === questionId).result

    val res: Future[Seq[SaAnswerRow]] = dbConfig.db.run(query)

    res.map(
      _.map(
        r =>
          SaAnswerModel(
            id = Some(r.id),
            questionId = r.questionId,
            choice = r.choice,
            createAt = Some(r.createAt.toString),
            updateAt = Some(r.updateAt.toString)
        )))
  }

  def countAnswer(questionId: Long): Future[(Int, Int, Int, Int, Int)] = {

    // TODO AnswerはQuestionと一緒にサブクエリとして取得した方がよかった

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

//  def readSurvey(): Future[Seq[SaQuestionAnswer]] = {
//
//    val f: Future[Seq[SaQuestionModel]] = readQuestion(None)
//    for (ql <- f; q <- ql; c <- countAnswer(q.id.getOrElse(0)))
//      yield SaQuestionAnswer(q, c._1, c._2, c._3, c._4, c._5)
//
//  }

//  def readQuestion2(): Future[Seq[SaQuestionAnswer]] = {
//
//    val dbConfig = dc.get[JdbcProfile]
//
////    val query = SaQuestion.flatMap(q => SaAnswer.filter(a => q.id === a.questionId))
//    val query = SaQuestion.flatMap(q =>
//      SaAnswer
//        .groupBy(a => (a.questionId, a.choice))
//        .filter(a => q.id === a._2)
//        .map{ case (ch, ag) => (q, ag.map(_.questionId).max, ch, ag.map(_.choice).length) }
//
////        .map(a => q, )
//    ).result
//
//      //for(q <- SaQuestion; a <- SaAnswer if )
//
//    val res: Future[Seq[(SaQuestionRow, Option[Long], (Long, Int), Int)]] =
//      dbConfig.db.run(query)
//
//    val res2 = res.map(ql => ql.groupBy(_._3).map(q => q.))
//
//    res.map(
//      _.map(r =>
//        SaQuestionModel(
//          question = SaQuestionModel(
//            id = Some(r._1.id),
//            question = r._1.question,
//            choice1 = r._1.choice1,
//            choice2 = r._1.choice2,
//            choice3 = r._1.choice3,
//            choice4 = r._1.choice4,
//            choice5 = r._1.choice5,
//            createAt = Some(r._1.createAt.toString),
//            updateAt = Some(r._1.updateAt.toString)),
//            count1 = Int,
//            count2 = Int,
//            count3 = Int,
//            count4 = Int,
//            count5 = Int
//
//        )))
//  }

}
