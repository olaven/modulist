package org.olaven.modulist.task

import android.app.Application
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.olaven.modulist.App
import org.olaven.modulist.database.entity.ModuleList

class FetchWeatherTask(application: Application): CustomTask<FetchWeatherTask.DTO, Unit, Unit>(application) {

    class DTO(val city: String)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let { dto ->

                val url = getUrl(dto)
                val request = getRequest(url)
                val response = getResponse(request)

                println(response)
            }
        }
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