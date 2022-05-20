package io.realworld

import zio.prelude._
import zio.json._

package object models {

  trait Extended[A]    { self: Newtype[A] =>
    def unsafe(a: A): Type = wrap(a)
  }
  trait ExtendedSub[A] { self: Subtype[A] =>
    def unsafe(a: A): Type                 = wrap(a)
    def unsafeAll[F[_]](fa: F[A]): F[Type] = fa.asInstanceOf[F[Type]]

  }

}
