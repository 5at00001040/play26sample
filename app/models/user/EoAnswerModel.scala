package models.user

import play.api.libs.json._

case class EoAnswerModel(id: Option[Long],
                         questionId: Long,
                         questionType: String,
                         choice: Int,
                         createAt: Option[String] = None,
                         updateAt: Option[String] = None) extends AnswerModel
object EoAnswerModel {
  implicit val eoAnswerModelFormat: Format[EoAnswerModel] =
    Json.format[EoAnswerModel]
}
