package models

import play.api.libs.json.{Json, OFormat}

case class SurveyRequest(req: SurveyModel)
object SurveyRequest {
  implicit def surveyRequestFormat: OFormat[SurveyRequest] =
    Json.format[SurveyRequest]
}

case class SurveyResponse(res: SurveyModel)
object SurveyResponse {
  implicit def surveyResponseFormat: OFormat[SurveyResponse] =
    Json.format[SurveyResponse]
}

case class SurveyListResponse(res: Seq[SurveyModel])
object SurveyListResponse {
  implicit def surveyListResponseFormat: OFormat[SurveyListResponse] =
    Json.format[SurveyListResponse]
}

case class SurveyModel(id: Option[Long],
                       title: Option[String] = None,
                       createAt: Option[String] = None,
                       updateAt: Option[String] = None)
object SurveyModel {
  implicit def surveyModelFormat: OFormat[SurveyModel] =
    Json.format[SurveyModel]
}
