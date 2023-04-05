package com.papaya.settings

import slick.jdbc.PostgresProfile.api._

object Configuration {

  val appConfig = AppConfig(spotConfiguration = SpotConfiguration(apiKey = "***", apiSecret = "***"))
  
  def getSpotConfiguration: SpotConfiguration = appConfig.spotConfiguration

  def db = Database.forConfig("postgres")
}
