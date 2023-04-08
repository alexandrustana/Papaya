package com.papaya

package object settings {

  case class AppConfig(spotConfiguration: Option[SpotConfiguration], databaseConfiguration: Option[DatabaseConfiguration])

  case class SpotConfiguration(apiKey: String, apiSecret: String)

  case class DatabaseConfiguration(connectionPool: String, dataSourceClass: String, properties: Properties, numThreads: Int)

  case class Properties(serverName: String, portNumber: String, databaseName: String, user: String, password: String)

}
