package models.originator

import models.user.AnswerSummaryModel
import play.api.libs.json._

case class SurveyRequest(req: SurveyModel)
object SurveyRequest {
  implicit val surveyRequestFormat: Format[SurveyRequest] = Json.format[SurveyRequest]
}

case class SurveyResponse(res: SurveyModel)
object SurveyResponse {
  implicit val surveyResponseFormat: Format[SurveyResponse] = Json.format[SurveyResponse]
}

case class SurveyListResponse(res: Seq[SurveyModel])
object SurveyListResponse {
  implicit val surveyListResponseFormat: Format[SurveyListResponse] = Json.format[SurveyListResponse]
}

case class SurveyModel(id: Option[Long],
                       title: String,
                       createAt: Option[String] = None,
                       updateAt: Option[String] = None)
object SurveyModel {
  implicit val surveyModelFormat: Format[SurveyModel] = Json.format[SurveyModel]
}

case class SurveyResultResponse(res: Seq[QAndAModel])
object SurveyResultResponse {
  implicit val surveyResultResponseFormat: Format[SurveyResultResponse] = Json.format[SurveyResultResponse]
}

case class QAndAModel(q: QuestionModel, a: AnswerSummaryModel)
object QAndAModel {
  implicit val qAndAModelFormat: Format[QAndAModel] = Json.format[QAndAModel]
}


