package io.realworld.server.routes

import io.realworld.models.user._
import io.realworld.server.routes.AppError.JWTTokenError
import io.realworld.services._
import zhttp.http._
import zio._

import java.lang

object Middlewares {

  def authenticateRequestZIO(req:Request): ZIO[TokenService, JWTTokenError, UserId] =
    for {
      authorisationHeaderValue <- ZIO
        .fromOption(req.authorization.map(_.toString))
        .orElseFail(JWTTokenError("Missing Authorization header"))
      token <- ZIO
        .fromOption(authorisationHeaderValue.split(" ").lift(1))
        .orElseFail(JWTTokenError("Authorization should be formatted as 'Token <token>'"))
      authToken    <- AuthToken.make(token).toZIO.orElseFail(JWTTokenError("The token is not a valid JWT formatted token"))
      tokenService <- ZIO.service[TokenService]
      userId <- tokenService.decodeToken(authToken)
    } yield userId

  def authenticate[R, E](authenticationFailed: JWTTokenError => HttpApp[R, E], success: UserId => HttpApp[R, E]): HttpApp[R with TokenService, E] = {
    Http
      .fromFunctionZIO { req: Request =>
        authenticateRequestZIO(req)
      }.flatMap(success).catchSome {
      case e: JWTTokenError => authenticationFailed(e)
    }
  }

}
