package com.papaya.database.model

import slick.jdbc.PostgresProfile.api._

import java.time.LocalDateTime

abstract class KLineDAO(tag:Tag) extends Table[(String, LocalDateTime, LocalDateTime, Double, Double, Double, Double, Double)](tag, "historic-data") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
}
