package com.papaya.settings

import slick.jdbc.JdbcBackend.Database


object Configuration {

  val appConfig = AppConfig(spotConfiguration = SpotConfiguration(apiKey = "***", apiSecret = "***"))
  
  def getSpotConfiguration: SpotConfiguration = appConfig.spotConfiguration

  def getDbConfiguration = Database.forConfig("postgres")
}
