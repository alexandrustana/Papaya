package com.papaya.settings

object Configuration {

  val appConfig = AppConfig(spotConfiguration = SpotConfiguration(apiKey = "***", apiSecret = "***"))
  
  def getSpotConfiguration: SpotConfiguration = appConfig.spotConfiguration
  
}
