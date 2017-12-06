package models.user

import play.api.libs.json._

trait AnswerSummaryModel
object AnswerSummaryModel {
  implicit val answerSummaryModelWrite: Writes[AnswerSummaryModel] = Writes {
    case sa: SaAnswerSummaryModel => SaAnswerSummaryModel.saAnswerSummaryModelFormat.writes(sa)
    case eo: EoAnswerSummaryModel => EoAnswerSummaryModel.eoAnswerSummaryModelFormat.writes(eo)
  }
  implicit val answerSummaryModelRead: Reads[AnswerSummaryModel] = Reads { json =>
    (json \ "questionType").asOpt[String] match {
      case Some("sa") => SaAnswerSummaryModel.saAnswerSummaryModelFormat.reads(json)
      case Some("eo") => EoAnswerSummaryModel.eoAnswerSummaryModelFormat.reads(json)
      case x => JsError(s"json read error: $x")
    }
  }
}

