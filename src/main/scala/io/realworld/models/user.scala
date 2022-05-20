package io.realworld.models

import zio._
import zio.json._
import zio.prelude.Assertion._
import zio.prelude._

import java.util.UUID

object user {
  object AuthToken extends Newtype[String] with Extended[String]{
    override def assertion = assert(!isEmptyString) //TODO replace this with a valid JWT matching
    implicit val codec: JsonCodec[AuthToken] = JsonCodec[String].transformOrFail(
      AuthToken.make(_).toEitherWith(_ => "Not a token"),
      AuthToken.unwrap
    )
  }
  type AuthToken = AuthToken.Type

  object Email extends Subtype[String] {
    override def assertion = assert { matches("^([\\w-\\.])*@([\\w-])*(\\.)*([\\w-]){2,4}$".r) }
    implicit val codec: JsonCodec[Email] = JsonCodec[String].transformOrFail(
      Email.make(_).toEitherWith(_ => "Not an email"),
      Email.unwrap
    )
  }
  type Email = Email.Type

  object Username extends Subtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[Username] = JsonCodec[String].transformOrFail(
      Username.make(_).toEitherWith(_ => "Not an username"),
      Username.unwrap
    )
  }
  type Username = Username.Type

  object Password extends Subtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[Password] = JsonCodec[String].transformOrFail(
      Password.make(_).toEitherWith(_ => "Not a password"),
      Password.unwrap
    )
  }
  type Password = Password.Type

  object Bio extends Subtype[String] {
    override def assertion = assert(!isEmptyString)
    implicit val codec: JsonCodec[Bio] =
      JsonCodec[String].transformOrFail(Bio.make(_).toEitherWith(_ => "Not a bio"), Bio.unwrap)
  }
  type Bio = Bio.Type

  object ImageURL extends Subtype[String] {
    override def assertion = assert {
      matches(
        "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})".r
      )
    }
    implicit val codec: JsonCodec[ImageURL] =
      JsonCodec[String].transformOrFail(
        ImageURL.make(_).toEitherWith(_ => "Not an ImageURL"),
        ImageURL.unwrap
      )
  }
  type ImageURL = ImageURL.Type

  object UserId extends Newtype[UUID] {
    def random: UIO[UserId] = Random.nextUUID.map(UserId(_))
    def fromString(id: String): Task[UserId] =
      ZIO.attempt {
        UserId(UUID.fromString(id))
      }
  }
  type UserId = UserId.Type

}
