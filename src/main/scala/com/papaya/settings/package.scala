package com.papaya

package object settings {

  case class AppConfig(spotConfiguration: SpotConfiguration)

  case class SpotConfiguration(apiKey: String, apiSecret: String)

}
