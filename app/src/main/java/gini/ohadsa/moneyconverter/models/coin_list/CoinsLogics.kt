package gini.ohadsa.moneyconverter.models.coin_list

import gini.ohadsa.moneyconverter.models.coin.Coin
import gini.ohadsa.moneyconverter.models.coin.CoinsType

class CoinsLogics(params : CoinsListParams, val referenceValueView: String = REFERENCE_VALUE_VIEW, val referenceValueConverter:String = REFERENCE_VALUE_CONVERTER) {

    val base :String = params.base
    val date: String = params.date
    val coinList = getCoinsByMap(params.rates)
    var referenceViewIndex  = -1 // init down..
    var referenceConverterAmount = -1.0 // init down..

    init {
        initReferences()
    }

    private fun getCoinsByMap(ratesMap: Map<String, Double>): List<Coin> {
        val cList = mutableListOf<Coin>()
        ratesMap.forEach { (id, reValue ) ->
            cList.add(Coin(id,reValue , CoinsType.getAllCoinsImageMap()[id]) )
        }
        cList.sortWith(kotlin.Comparator{ c1 ,c2 ->
            return@Comparator when{
                (c1.moneyValue - c2.moneyValue > 0) ->  1
                (c1.moneyValue - c2.moneyValue < 0) -> -1
                else -> {0}
            }
        })
        return cList
    }

    private fun initReferences() {
        coinList.forEachIndexed{i , coin ->
            if(coin.id == referenceValueView )  referenceViewIndex = i
            if (coin.id == referenceValueConverter) referenceConverterAmount = coin.moneyValue
        }
        coinList.forEachIndexed{i , coin ->
            coin.higher = (i > referenceViewIndex)
        }
    }

}

const val REFERENCE_VALUE_VIEW = "USD"
const val REFERENCE_VALUE_CONVERTER = "ILS"