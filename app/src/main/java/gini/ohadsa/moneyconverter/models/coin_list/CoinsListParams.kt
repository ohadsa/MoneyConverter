package gini.ohadsa.moneyconverter.models.coin_list

import android.provider.VoicemailContract
import com.google.gson.annotations.SerializedName
import gini.ohadsa.moneyconverter.models.coin.Coin

class CoinsListParams(
    @SerializedName(value = "timestamp")
    var timestamp: String,

    @SerializedName(value = "base")
    var base: String,

    @SerializedName(value = "date")
    var date: String,

    @SerializedName(value = "rates")
    var rates: Map<String, Double>,

    @SerializedName(value = "success")
    var succeeded: Boolean,

    @SerializedName(value = "error")
    var errorDetails: Map<String, String>,


)


