package io.realworld.server

import zhttp.http._

object RealWorldServer {

  val handleApp: Http[Any, Throwable, Request, Response] = {
    import routes._
    UsersRoutes.routes.catchAll {
      case AppError.MissingBodyError =>
        Http.text("MISSING BODY").setStatus(Status.UnprocessableEntity)
      case AppError.JsonDecodingError(message) =>
        Http.text(s"JSON DECODING ERROR: $message").setStatus(Status.UnprocessableEntity)
    }
  }

}
