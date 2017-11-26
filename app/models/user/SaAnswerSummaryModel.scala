package models.user

import play.api.libs.json._

case class SaAnswerSummaryModel(count1: Long,
                                count2: Long,
                                count3: Long,
                                count4: Long,
                                count5: Long) extends AnswerSummaryModel
object SaAnswerSummaryModel {
  implicit val saAnswerSummaryModelFormat: Format[SaAnswerSummaryModel] = Json.format[SaAnswerSummaryModel]
}
