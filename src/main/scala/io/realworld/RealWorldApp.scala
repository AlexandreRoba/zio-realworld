package io.realworld

import zio._
import io.realworld.server._
import zhttp.http.Middleware
import zhttp.service.Server

import javax.sql.DataSource

object RealWorldApp extends ZIOAppDefault {
  val program: ZIO[DataSource, Throwable, Unit] = for {
    _ <- Migrations.migrate
    _ <-  Server.start(8080, RealWorldServer.handleApp @@ Middleware.cors())
  } yield ()

  override def run = {
    program.provide(QuillContext.dataSourceLayer)
  }
}
