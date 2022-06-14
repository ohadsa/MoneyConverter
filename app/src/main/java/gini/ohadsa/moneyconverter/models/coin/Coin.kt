package gini.ohadsa.moneyconverter.models.coin

import gini.ohadsa.moneyconverter.R
import kotlin.random.Random


data class Coin(
    val id: String,
    val moneyValue: Double, // Euro , Shekel
    val imagePath: String?,
    var higher : Boolean = false,
    var faceUp: Boolean = false,
){

    fun convert(reference: Double) :String{
        return   "%.4f".format(  reference / moneyValue)
    }
}

