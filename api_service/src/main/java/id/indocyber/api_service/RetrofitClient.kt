package id.indocyber.api_service

import android.content.Context
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.GanderInterceptor
import com.ashokvarma.gander.imdb.GanderIMDB
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getRetrofitMovieClient(context: Context): Retrofit {
        Gander.setGanderStorage(GanderIMDB.getInstance())
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor {
                try {
                    it.proceed(it.request())
                } catch (e: Exception) {
                    Response.Builder()
                        .request(it.request()).message(e.message ?: "")
                        .protocol(Protocol.HTTP_1_1)
                        .code(0)
                        .body(
                            with(JsonObject()) {
                                addProperty("error", e.message)
                            }.toString().toResponseBody(
                                "application/json".toMediaType()
                            )
                        ).build()
                }
            })
            .addInterceptor(GanderInterceptor(context).showNotification(true))
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_MOVIE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}