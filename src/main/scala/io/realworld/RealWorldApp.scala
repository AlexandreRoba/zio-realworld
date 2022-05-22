package io.realworld

import zio._
import io.realworld.server._
import io.realworld.services._
import zhttp.http.Middleware
import zhttp.service.Server

object RealWorldApp extends ZIOAppDefault {
  val program = for {
    _             <- Migrations.migrate
    _             <- Server.start(8080, RealWorldServer.handleApp @@ Middleware.cors())
  } yield ()

  override def run = {
    program.provide(QuillContext.dataSourceLayer, AppConfig.layer, TokenServiceLive.layer)
  }
}
