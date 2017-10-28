package models

import play.api.libs.json.{Json, OFormat}

case class SaAnswerRequest(req: SaAnswerModel)
object SaAnswerRequest {
  implicit def saAnswerRequestFormat: OFormat[SaAnswerRequest] =
    Json.format[SaAnswerRequest]
}

case class SaAnswerListResult(res: Seq[SaAnswerModel])
object SaAnswerListResult {
  implicit def saAnswerListResultFormat: OFormat[SaAnswerListResult] =
    Json.format[SaAnswerListResult]
}

case class SaAnswerModel(id: Long,
                         questionId: Long,
                         choice: Int,
                         createAt: Option[String] = None,
                         updateAt: Option[String] = None)
object SaAnswerModel {
  implicit def saAnswerModelFormat: OFormat[SaAnswerModel] =
    Json.format[SaAnswerModel]
}
