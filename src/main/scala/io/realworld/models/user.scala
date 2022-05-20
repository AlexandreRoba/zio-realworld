package io.realworld.models

import zio._
import zio.json._
import zio.prelude.Assertion._
import zio.prelude._

object user {
  object AuthToken extends Newtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[AuthToken] = JsonCodec[String].transformOrFail(
      AuthToken.make(_).toEitherWith(_ => "InvalidToken"),
      AuthToken.unwrap
    )
  }
  type AuthToken = AuthToken.Type

  object Email extends Subtype[String] {
    override def assertion = assert { matches("^([\\w-\\.])*@([\\w-])*(\\.)*([\\w-]){2,4}$".r) }
    implicit val codec: JsonCodec[Email] = JsonCodec[String].transformOrFail(
      Email.make(_).toEitherWith(_ => "Invalid email"),
      Email.unwrap
    )
  }
  type Email = Email.Type

  object Username extends Subtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[Username] = JsonCodec[String].transformOrFail(
      Username.make(_).toEitherWith(_ => "Invalid username"),
      Username.unwrap
    )
  }
  type Username = Username.Type

  object Password extends Subtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[Password] = JsonCodec[String].transformOrFail(
      Password.make(_).toEitherWith(_ => "Invalid password"),
      Password.unwrap
    )
  }
  type Password = Password.Type

  object Bio extends Subtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[Bio] =
      JsonCodec[String].transformOrFail(Bio.make(_).toEitherWith(_ => "Invalid bio"), Bio.unwrap)
  }
  type Bio = Bio.Type

}
