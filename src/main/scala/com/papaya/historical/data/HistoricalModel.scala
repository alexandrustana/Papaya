package com.papaya.historical.data

import java.time.LocalDateTime

//candlestick
//https://www.investopedia.com/trading/candlestick-charting-what-is-it/
case class HistoricalModel(
                          openTime: LocalDateTime,
                          openPrice: BigDecimal,
                          highPrice: BigDecimal,
                          lowPrice: BigDecimal,
                          volume: BigDecimal,
                          closingTime: LocalDateTime,
                          quoteAssetVolume: BigDecimal,
                          numberOfTrades: Int,
                          buyBaseAssetVolume: BigDecimal,
                          buyQuoteAssetVolume: BigDecimal,
                          ignore: String
                          )