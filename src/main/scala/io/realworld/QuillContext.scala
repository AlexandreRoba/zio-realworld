package io.realworld

import zio._
import io.getquill._
import io.getquill.context.ZioJdbc.DataSourceLayer

import javax.sql.DataSource

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
  val dataSourceLayer: ZLayer[Any, Nothing, DataSource] =
    DataSourceLayer.fromPrefix("database").orDie
}
