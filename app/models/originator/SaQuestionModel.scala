package models.originator

import play.api.libs.json._

case class SaQuestionModel(id: Option[Long] = None,
                           surveyId: Option[Long] = None,
                           questionType: Option[String] = None,
                           question: Option[String] = None,
                           choice1: Option[String] = None,
                           choice2: Option[String] = None,
                           choice3: Option[String] = None,
                           choice4: Option[String] = None,
                           choice5: Option[String] = None,
                           createAt: Option[String] = None,
                           updateAt: Option[String] = None) extends QuestionModel
object SaQuestionModel {
  implicit val saQuestionModelFormat: Format[SaQuestionModel] = Json.format[SaQuestionModel]
}
