package models.user

import play.api.libs.json._

trait AnswerModel
object AnswerModel {
  implicit val answerModelWrite: Writes[AnswerModel] = Writes {
    case sa: SaAnswerModel => SaAnswerModel.saAnswerModelFormat.writes(sa)
    case eo: EoAnswerModel => EoAnswerModel.eoAnswerModelFormat.writes(eo)
  }
  implicit val answerModelRead: Reads[AnswerModel] = Reads { json =>
    (json \ "questionType").asOpt[String] match {
      case Some("sa") => SaAnswerModel.saAnswerModelFormat.reads(json)
      case Some("eo") => EoAnswerModel.eoAnswerModelFormat.reads(json)
      case x => JsError(s"json read error: $x")
    }
  }
}

case class AnswerListRequest(req: AnswerModel)
object AnswerListRequest {
  implicit def answerListRequestFormat: Format[AnswerListRequest] =
    Json.format[AnswerListRequest]
}
