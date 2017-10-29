package models

import play.api.libs.json.{Json, OFormat}

case class SaQuestionRequest(req: SaQuestionModel)
object SaQuestionRequest {
  implicit def saQuestionRequestFormat: OFormat[SaQuestionRequest] =
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

