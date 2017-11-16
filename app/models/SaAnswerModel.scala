package models

import play.api.libs.json.{Json, OFormat}

case class SaAnswerRequest(req: SaAnswerModel)
object SaAnswerRequest {
  implicit def saAnswerRequestFormat: OFormat[SaAnswerRequest] =
    Json.format[SaAnswerRequest]
}

case class SaAnswerResult(res: SaAnswerModel)
object SaAnswerResult {
  implicit def saAnswerResultFormat: OFormat[SaAnswerResult] =
    Json.format[SaAnswerResult]
}

case class SaAnswerCountModel(count1: Int,
                         count2: Int,
                         count3: Int,
                         count4: Int,
                         count5: Int)
object SaAnswerCountModel {
  implicit def saAnswerCountModelFormat: OFormat[SaAnswerCountModel] =
    Json.format[SaAnswerCountModel]
}

case class SaAnswerModel(id: Option[Long],
                         questionId: Long,
                         choice: Int,
                         createAt: Option[String] = None,
                         updateAt: Option[String] = None)
object SaAnswerModel {
  implicit def saAnswerModelFormat: OFormat[SaAnswerModel] =
    Json.format[SaAnswerModel]
}

