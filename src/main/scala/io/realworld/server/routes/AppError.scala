package io.realworld.server.routes


sealed trait AppError extends Throwable

object AppError {
  case object MissingBodyError                  extends AppError
  final case class JsonDecodingError(message: String) extends AppError
  final case class JWTTokenError(message: String) extends AppError

}
