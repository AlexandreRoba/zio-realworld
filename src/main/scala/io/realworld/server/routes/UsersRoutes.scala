package io.realworld.server.routes

import zhttp.http._
import zio._
import zio.json._

object UsersRoutes {

  final case class UserLogin(email: String, password: String)
  object UserLogin {
    implicit val codec: JsonCodec[UserLogin] = DeriveJsonCodec.gen[UserLogin]
  }

  final case class LoginRequest(user: UserLogin)
  object LoginRequest {
    implicit val codec: JsonCodec[LoginRequest] = DeriveJsonCodec.gen[LoginRequest]
  }

  final case class UserRegistration(username: String, email: String, password: String)

  object UserRegistration {
    implicit val codec: JsonCodec[UserRegistration] = DeriveJsonCodec.gen[UserRegistration]
  }

  final case class RegistrationRequest(user: UserRegistration)

  object RegistrationRequest {
    implicit val codec: JsonCodec[RegistrationRequest] = DeriveJsonCodec.gen[RegistrationRequest]
  }

  final case class UserDto(
      email: String,
      token: String,
      username: String,
      bio: Option[String] = None,
      image: Option[String] = None
  )
  object UserDto {
    implicit val codec: JsonCodec[UserDto] = DeriveJsonCodec.gen[UserDto]
  }

  final case class UserResponse(user: UserDto)
  object UserResponse {
    implicit val codec: JsonCodec[UserResponse] = DeriveJsonCodec.gen[UserResponse]
  }

  val routes: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {

    case req @ Method.POST -> !! / "api" / "users" / "login" =>
      for {
        body         <- req.bodyAsString.orElseFail(AppError.MissingBodyError)
        loginRequest <- ZIO.from(body.fromJson[LoginRequest]).mapError(AppError.JsonDecodingError)
        user <- ZIO.succeed(UserResponse(UserDto(loginRequest.user.email, "token", "username")))
      } yield Response.json(user.toJson)

    case req @ Method.POST -> !! / "api" / "users" =>
      for {
        body <- req.bodyAsString.orElseFail(AppError.MissingBodyError)
        registrationRequest <- ZIO
          .from(body.fromJson[RegistrationRequest])
          .mapError(AppError.JsonDecodingError)
        user <- ZIO.succeed(
          UserResponse(
            UserDto(registrationRequest.user.email, "token", registrationRequest.user.username)
          )
        )
      } yield Response.json(user.toJson)

  }
}
