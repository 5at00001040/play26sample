package models.originator

import play.api.libs.json._

trait QuestionModel
object QuestionModel {
  implicit val questionModelWrite: Writes[QuestionModel] = Writes {
    case sa: SaQuestionModel => SaQuestionModel.saQuestionModelFormat.writes(sa)
    case eo: EoQuestionModel => EoQuestionModel.eoQuestionModelFormat.writes(eo)
  }
  implicit val questionModelRead: Reads[QuestionModel] = Reads { json =>
    (json \ "questionType").asOpt[String] match {
      case Some("sa") => SaQuestionModel.saQuestionModelFormat.reads(json)
      case Some("eo") => EoQuestionModel.eoQuestionModelFormat.reads(json)
      case x => JsError(s"json read error: $x")
    }
  }
}

case class QuestionRequest(req: QuestionModel)
object QuestionRequest {
  implicit val questionRequestFormat: Format[QuestionRequest] = Json.format[QuestionRequest]
}

case class QuestionListResult(res: Seq[QuestionModel])
object QuestionListResult {
  implicit val questionListResultFormat: Format[QuestionListResult] = Json.format[QuestionListResult]
}

case class QuestionResult(res: QuestionModel)
object QuestionResult {
  implicit val questionResultFormat: Format[QuestionResult] = Json.format[QuestionResult]
}
