package org.olaven.modulist.task

import android.app.Application
import android.widget.Toast
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.olaven.modulist.App


class FetchWeatherTask(application: Application): CustomTask<FetchWeatherTask.DTO, Unit, List<FetchWeatherTask.Forecast>?>(application) {

    private var responseCatcher: AsyncForecastResponse? = null

    class DTO(
        val city: String,
        val responseCatcher: AsyncForecastResponse
    )
    interface AsyncForecastResponse {
        fun catchResponse(forecasts: List<FetchWeatherTask.Forecast>?)
    }
    data class Forecast(
        @Json(name = "temp")
        val temperature: Double,
        @Json(name = "wind_spd")
        val windSpeed: Double
    )

    override fun doInBackground(vararg DTOs: DTO?): List<Forecast>? {

        DTOs.forEach {

            it?.let { dto ->

                responseCatcher = dto.responseCatcher

                val url = getUrl(dto)
                val request = getRequest(url)
                val response = getResponse(request)

                return parseResponse(response)
            }
        }

        return null
    }

    override fun onPostExecute(result: List<Forecast>?) {

        super.onPostExecute(result)
        responseCatcher?.catchResponse(result)
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

    private fun parseResponse(response: Response): List<Forecast>? {

        if (response.body() == null)
            return null

        val json = response.body()!!.string()
        val data = JSONObject(json).get("data").toString()

        return Klaxon()
            .parseArray(data)
    }
}