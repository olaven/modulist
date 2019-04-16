package org.olaven.modulist.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.fragment_weather_planner.*
import org.olaven.modulist.App
import org.olaven.modulist.PlacesInput
import org.olaven.modulist.R
import org.olaven.modulist.task.FetchWeatherTask
import com.jjoe64.graphview.series.LineGraphSeries




class WeatherPlannerFragment : Fragment() {

    private lateinit var placesInput: PlacesInput
    private lateinit var city: String
    private enum class GraphData { TEMPERATURE, WIND_SPEED}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_planner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        fragment_weather_planner_text_city.text = "Unlocated"
        placesInput = PlacesInput(
            activity as AppCompatActivity,
            listOf(Place.Field.NAME),
            TypeFilter.CITIES,
            App.REQUEST_CODE_PLACES_CITIES
        )
        setupButton()
    }

    private fun setupButton() {

        fragment_weather_planner_button_locate.setOnClickListener {

            val intent = placesInput.getLaunchIntent()
            startActivityForResult(intent, App.REQUEST_CODE_PLACES_CITIES)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        placesInput.getPlace(
            requestCode, resultCode, data, fragment_weather_planner)?.let {place ->

            city = place.name!!
            val dto = FetchWeatherTask.DTO(city, object: FetchWeatherTask.AsyncForecastResponse {
                override fun catchResponse(forecasts: List<FetchWeatherTask.Forecast>?) {

                    if (forecasts == null)
                        Snackbar.make(fragment_weather_planner, "No weather data for this place..", Snackbar.LENGTH_SHORT)
                     else
                        updateChart(forecasts)

                }
            })

            FetchWeatherTask(activity!!.application)
                .execute(dto)
        }
    }

    private fun updateChart(forecasts: List<FetchWeatherTask.Forecast>) {



        val temperatureSeries = getSeries(forecasts, GraphData.TEMPERATURE)
        val windSeries = getSeries(forecasts, GraphData.WIND_SPEED)

        fragment_weather_planner_graph.apply {

            removeAllSeries()
            legendRenderer.isVisible = true
            viewport.isScrollable = true
            viewport.isScalable = true
            fragment_weather_planner_text_city.text = "$city"

            temperatureSeries.color = Color.MAGENTA
            windSeries.color = Color.BLUE

            temperatureSeries.title = "Temperature"
            windSeries.title = "Wind speed"

            addSeries(temperatureSeries)
            addSeries(windSeries)

        }
    }

    private fun getSeries(forecasts: List<FetchWeatherTask.Forecast>, type: GraphData): LineGraphSeries<DataPoint> =
            LineGraphSeries(
                forecasts.mapIndexed {index, forecast ->

                    val x = index.toDouble()
                    val y = when(type) {
                        GraphData.TEMPERATURE -> forecast.temperature
                        GraphData.WIND_SPEED -> forecast.windSpeed
                    }

                    DataPoint(x, y)
                }.toTypedArray()
            )



}
