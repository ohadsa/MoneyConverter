package gini.ohadsa.moneyconverter.models.coin_list


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gini.ohadsa.moneyconverter.R
import gini.ohadsa.moneyconverter.models.coin.AdapterCoins
import gini.ohadsa.moneyconverter.models.coin.Coin

import gini.ohadsa.moneyconverter.network.CoinsValueService
import gini.ohadsa.moneyconverter.network.MyException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class CoinsListViewModel : ViewModel() {

    val coins: LiveData<List<Coin>> get() = _coins
    private val _coins = MutableLiveData<List<Coin>>()
    private lateinit var coinsBrain: CoinsLogics
    val error = MutableLiveData<Int>()
    val refAmount: Double
        get() = coinsBrain.referenceConverterAmount

    init {
        viewModelScope.launch(moviesApiException()) {
            if (coroutineContext.job.isActive) {
                val coins: CoinsListParams = CoinsValueService.create().coins()
                if (!coins.succeeded) throw MyException.CallFailed()
                coinsBrain = CoinsLogics(coins)
                Log.d("OHADSAA", coinsBrain.coinList.toString())
                _coins.postValue(coinsBrain.coinList)
            }
        }
    }


    private fun moviesApiException() =
        CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is MyException -> {
                    when (throwable) {
                        is MyException.NoInternetException -> errorMassage(R.string.error_no_internet)
                        is MyException.JobAlreadyCanceled -> errorMassage(R.string.error_job_canceled)
                        is MyException.ApiHttpException -> errorMassage(R.string.error_http)
                        is MyException.CallFailed -> errorMassage(R.string.error_usage_limit_reached)
                        else -> errorMassage(R.string.error_unexpected)
                    }
                }
            }
        }

    private fun errorMassage(s: Int = -1, massage: String = "") {
        error.postValue(s)
    }

}