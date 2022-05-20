package io.realworld.services

import zio._
import io.realworld.models.user._

trait TokenService {
  def encodeToken(tokenContent: String): Task[AuthToken]
}
