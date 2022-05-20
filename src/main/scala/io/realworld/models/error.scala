package io.realworld.models

import zio.json._

object error {
  final case class ValidationErrorResponse(errors: Map[String, List[String]])

  object ValidationErrorResponse {
    implicit val codec: JsonCodec[ValidationErrorResponse] =
      DeriveJsonCodec.gen[ValidationErrorResponse]
  }

}
