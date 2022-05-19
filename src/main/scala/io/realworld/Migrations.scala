package io.realworld

import zio._
import org.flywaydb.core.Flyway

import javax.sql.DataSource

object Migrations {

  val migrate: ZIO[DataSource, Throwable, Unit] =
    for {
      datasource <- ZIO.service[DataSource]
      _ <- ZIO.attempt {
        Flyway
          .configure()
          .dataSource(datasource)
          .baselineOnMigrate(true)
          .baselineVersion("0")
          .load()
          .migrate()
      }
    } yield ()

}
