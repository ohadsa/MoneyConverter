package gini.ohadsa.moneyconverter.network

import gini.ohadsa.moneyconverter.models.coin_list.CoinsListParams
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CoinsValueService {

    //val x: String get() = "http://data.fixer.io/api/latest?access_key=a09197b882e3c2d55792d966d90e004f&format=1"

    @GET("txT1A97z")
    suspend fun coins():CoinsListParams

    companion object{
        fun create():CoinsValueService{
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()

            return Retrofit
                .Builder()
                //.client(client)
                .baseUrl("https://pastebin.com/raw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinsValueService::class.java)
        }
    }
}

class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url().newBuilder()
            .addQueryParameter("access_key","a09197b882e3c2d55792d966d90e004f&format=1" ).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }

}