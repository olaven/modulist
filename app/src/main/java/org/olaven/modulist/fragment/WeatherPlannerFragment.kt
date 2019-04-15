package org.olaven.modulist.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_module_list.*
import kotlinx.android.synthetic.main.fragment_weather_planner.*
import org.olaven.modulist.App
import org.olaven.modulist.R
import org.olaven.modulist.service.GeofenceService


class WeatherPlannerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_planner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    fun setupButton() {

        Places.initialize(context!!, App.API_PLACES_KEY)
        val location = SimpleLocation(this.activity)
        fragment_weather_planner_button.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, listOf(Place.Field.NAME))
                .setLocationBias(
                    RectangularBounds.newInstance(
                        LatLng(location.latitude, location.longitude),
                        LatLng(location.latitude + 0.1, location.longitude + 0.1)
                    ))
                .setTypeFilter(TypeFilter.CITIES)
                .build(context!!)
            startActivityForResult(intent, App.REQUEST_CODE_PLACES_CITIES)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == App.REQUEST_CODE_PLACES_CITIES) {

            if (resultCode == AppCompatActivity.RESULT_OK) {

                val place = Autocomplete.getPlaceFromIntent(data!!)
                place.name

            } else {

                Snackbar
                    .make(fragment_weather_planner, "Some error occured", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
