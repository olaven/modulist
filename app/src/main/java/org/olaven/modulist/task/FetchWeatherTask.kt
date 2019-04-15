package org.olaven.modulist.task

import android.app.Application
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.olaven.modulist.App
import java.util.regex.Pattern

class FetchWeatherTask(application: Application): CustomTask<FetchWeatherTask.DTO, Unit, Unit>(application) {

    class DTO(val city: String)
    class Forecast(val temp: Float, clouds: Int, wind_spd: Double)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let { dto ->

                val url = getUrl(dto)
                val request = getRequest(url)
                val response = getResponse(request)

               val forecasts = parseResponse(response)
            }
        }
    }

    private fun parseResponse(response: Response): List<Forecast>? {

        if (response.body() == null)
            return null

        val pathMatcher = object : PathMatcher {
            override fun pathMatches(path: String) = Pattern.matches(".*data.", path)

            override fun onMatch(path: String, value: Any) {
                println("Adding $path = $value")
            }
        }

        return Klaxon()
            .parse<Forecast>(
                response.body()!!.string()
            ) as JsonArray<Forecast>
    }

    private fun getUrl(dto: DTO) =
        HttpUrl.Builder()
            .scheme("https")
            .host("api.weatherbit.io")
            .addPathSegment("/v2.0/forecast/daily")
            .addQueryParameter("key", App.API_WEATHERBIT_KEY)
            .addQueryParameter("city", dto.city)
            .build()

    private fun getRequest(url: HttpUrl) =
        Request.Builder()
            .url(url)
            .get()
            .build()

    private fun getResponse(request: Request): Response {

        val client = OkHttpClient()
        return client
            .newCall(request)
            .execute()
    }
}