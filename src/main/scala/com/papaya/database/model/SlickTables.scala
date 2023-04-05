package com.papaya.database.model

import slick.jdbc.PostgresProfile
import slick.lifted.TableQuery

object SlickTables extends SlickTablesGeneric(PostgresProfile) {
  lazy val historicTable = TableQuery[KLineDAO]
}
