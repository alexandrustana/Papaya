package com.papaya.settings

import cats.effect.IO
import pureconfig.generic.auto._
import pureconfig._
import pureconfig.generic.ProductHint
import slick.jdbc.PostgresProfile.api._

object Configuration {

  implicit def hint[T]: ProductHint[T] =
    ProductHint[T](ConfigFieldMapping(CamelCase, CamelCase))

  def loadConfiguration: IO[AppConfig] = IO(ConfigSource.default.loadOrThrow[AppConfig])


  def db = Database.forConfig("databaseConfiguration")
}
