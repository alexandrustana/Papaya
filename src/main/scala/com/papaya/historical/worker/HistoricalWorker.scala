package com.papaya.historical.worker

import com.papaya.binance.client.BClient

import com.papaya.database.Psql
import com.papaya.settings.AppConfig

import io.github.paoloboni.binance.common.Interval
import io.github.paoloboni.binance.spot.parameters.v3.KLines

import scala.concurrent.ExecutionContext.Implicits.global

object HistoricalWorker {

  val QUOTE_NAME = "BTCUSDT"


  // check historical database, get last saved value for the specified currency
  // save the delta into the database
  // apply strategy?
  //
  def run(bClient: BClient, config: AppConfig) = for {
    lastModel <- Psql.getLastKline(QUOTE_NAME)
    _ <- if(lastModel.empty)
          BClient.
  }

}
