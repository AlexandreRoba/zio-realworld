package io.realworld.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.exceptions.{JWTDecodeException, SignatureVerificationException}
import io.realworld.AppConfig._
import io.realworld.models.user._
import io.realworld.server.routes.AppError._
import zio._

trait TokenService {
  def encodeToken(userId: UserId): UIO[AuthToken]
  def decodeToken(token: AuthToken): IO[JWTTokenError, UserId]
}

object TokenService {
  def encodeClaims(userId: UserId): RIO[TokenService, AuthToken] =
    ZIO.serviceWithZIO[TokenService](_.encodeToken(userId))

  def decodeToken(token: AuthToken): ZIO[TokenService, JWTTokenError, UserId] =
    ZIO.serviceWithZIO[TokenService](_.decodeToken(token))
}

final case class TokenServiceLive(config: Config) extends TokenService {
  override def encodeToken(userId: UserId): UIO[AuthToken] =
    (for {
      token <- ZIO.attempt {
        val algo = HMAC256(config.jwt.secret)
        JWT.create().withSubject(userId.toString).sign(algo)
      }
    } yield AuthToken.unsafe(token)).orDie

  override def decodeToken(token: AuthToken): IO[JWTTokenError, UserId] =
    (for {
      decodedJWT <- ZIO.attempt {
        JWT.decode(token.toString)
      }
      _ <- ZIO.attempt {
        val algo = HMAC256(config.jwt.secret)
        algo.verify(decodedJWT)
      }
      subject <- ZIO
        .from(decodedJWT.getSubject)
        .orElseFail(new IllegalArgumentException("Subject is empty"))
      u <- UserId.fromString(subject)
    } yield u).refineOrDie[JWTTokenError] {
      case e: JWTDecodeException             => JWTTokenError(e.getMessage)
      case e: SignatureVerificationException => JWTTokenError(e.getMessage)
      case _: IllegalArgumentException       => JWTTokenError("Subject was not specified")
    }
}

object TokenServiceLive {
  val layer: URLayer[Config, TokenService] = ZLayer {
    for {
      c <- ZIO.service[Config]
    } yield TokenServiceLive(c)
  }
}
