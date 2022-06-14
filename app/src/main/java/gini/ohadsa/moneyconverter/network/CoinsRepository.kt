package gini.ohadsa.moneyconverter.network

import gini.ohadsa.moneyconverter.models.coin_list.CoinsListParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class CoinsRepository {

    companion object {
        suspend fun getCoinsList(): CoinsListParams {
            return withContext(Dispatchers.IO) {
                if (!coroutineContext.job.isActive) throw MyException.JobAlreadyCanceled()
                try {
                    CoinsValueService.create().coins()
                } catch (e: Exception) {
                    throw mapExceptions(e)
                }

            }
        }
    }
}


sealed class MyException : Exception() {
    class NoInternetException : MyException()
    class ApiHttpException : MyException()
    class UnExpected : MyException()
    class JobAlreadyCanceled : MyException()
    class CallFailed : MyException()
}

private fun mapExceptions(e: Exception): MyException =
    when (e) {
        is InterruptedException -> MyException.JobAlreadyCanceled()
        is IOException -> MyException.NoInternetException()
        is HttpException -> MyException.ApiHttpException()
        else -> MyException.UnExpected()
    }
