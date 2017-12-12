package models.originator

import play.api.libs.json._

case class EoQuestionModel(id: Option[Long] = None,
                           surveyId: Option[Long] = None,
                           questionType: String = "eo",
                           question: String,
                           createAt: Option[String] = None,
                           updateAt: Option[String] = None) extends QuestionModel
object EoQuestionModel {
  implicit val eoQuestionModelFormat: Format[EoQuestionModel] = Json.format[EoQuestionModel]
}
