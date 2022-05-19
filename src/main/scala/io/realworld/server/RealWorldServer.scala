package io.realworld.server

import zhttp.http.{Http, Request, Response}

object RealWorldServer {

  val handleApp: Http[Any, Throwable, Request, Response] = {
    import routes._
    UsersRoutes.routes
  }

}
