package io.realworld.server.routes

import io.realworld.models.user._
import io.realworld.server.routes.UsersRoutes.{UserDto, UserResponse}
import zio._
import zhttp.http._
import zio.json._

object UserRoutes {
  def routes(userId:UserId): Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {

    case Method.GET -> !! / "api" / "user" =>
      for {
        profile <- ZIO.succeed(UserResponse(UserDto(Email("alex@alex.com"), AuthToken("token"), Username("username"))))
      } yield Response.json(profile.toJson)

  }
}
