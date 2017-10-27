package models

import play.api.libs.json.{Json, OFormat}

case class SaQuestionRequest(req: SaQuestionModel)
object SaQuestionRequest {
  implicit def SaQuestionRequestFormat: OFormat[SaQuestionRequest] =
    Json.format[SaQuestionRequest]
}

case class SaQuestionListResult(res: Seq[SaQuestionModel])
object SaQuestionListResult {
  implicit def saQuestionListResultFormat: OFormat[SaQuestionListResult] =
    Json.format[SaQuestionListResult]
}

case class SaQuestionResult(res: SaQuestionModel)
object SaQuestionResult {
  implicit def saQuestionResultFormat: OFormat[SaQuestionResult] =
    Json.format[SaQuestionResult]
}

case class SaQuestionModel(id: Option[Long] = None,
                           question: Option[String] = None,
                           choice1: Option[String] = None,
                           choice2: Option[String] = None,
                           choice3: Option[String] = None,
                           choice4: Option[String] = None,
                           choice5: Option[String] = None,
                           createAt: Option[String] = None,
                           updateAt: Option[String] = None)
object SaQuestionModel {
  implicit def saQuestionModelFormat: OFormat[SaQuestionModel] =
    Json.format[SaQuestionModel]
}
