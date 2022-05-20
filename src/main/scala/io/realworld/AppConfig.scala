package io.realworld

import zio.Layer
import zio.config._
import zio.config.typesafe._
import zio.config.magnolia._

object AppConfig {
  final case class Config(jwt: JwtConfig)
  final case class JwtConfig(secret: String)
  val layer: Layer[ReadError[String], Config] = ZConfig.fromResourcePath(descriptor[Config])
}
