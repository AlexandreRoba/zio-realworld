package io.realworld.server

import io.realworld.models.error._
import zhttp.http._
import zio.json._

object RealWorldServer {

  val handleApp: Http[Any, Throwable, Request, Response] = {
    import routes._
    UsersRoutes.routes.catchAll {
      case AppError.MissingBodyError =>
        Http.response(
          Response
            .json(ValidationErrorResponse.bodyError("missing body").toJson)
            .setStatus(Status.UnprocessableEntity)
        )
      case AppError.JsonDecodingError(message) =>
        Http.response(
          Response
            .json(ValidationErrorResponse.bodyError(message).toJson)
            .setStatus(Status.UnprocessableEntity)
        )
    }
  }

}
