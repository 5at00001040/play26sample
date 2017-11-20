package models.user

import play.api.libs.json._

case class SaAnswerModel(id: Option[Long],
                         questionId: Long,
                         questionType: String,
                         choice: Int,
                         createAt: Option[String] = None,
                         updateAt: Option[String] = None) extends AnswerModel
object SaAnswerModel {
  implicit val saAnswerModelFormat: Format[SaAnswerModel] =
    Json.format[SaAnswerModel]
}

