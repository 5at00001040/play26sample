package models.spike

import play.api.libs.json.Json

case class FooModel(key: String, value: String)

object FooModel {
  implicit def fooModelFromat = Json.format[FooModel]
}