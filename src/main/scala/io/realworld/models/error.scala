package io.realworld.models

import zio.json._

object error {
  final case class ValidationErrorResponse(errors: Map[String, List[String]])

  object ValidationErrorResponse {

    def bodyError(message: String): ValidationErrorResponse = ValidationErrorResponse(
      Map("body" -> List(message))
    )

    implicit val codec: JsonCodec[ValidationErrorResponse] =
      DeriveJsonCodec.gen[ValidationErrorResponse]
  }

}
