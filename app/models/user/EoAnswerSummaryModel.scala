package models.user

import play.api.libs.json._

case class EoAnswerSummaryModel(count1: Long,
                                count2: Long) extends AnswerSummaryModel
object EoAnswerSummaryModel {
  implicit val eoAnswerSummaryModelFormat: Format[EoAnswerSummaryModel] = Json.format[EoAnswerSummaryModel]
}
