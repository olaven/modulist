package org.olaven.modulist

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import im.delight.android.location.SimpleLocation

class PlacesInput(
    private val activity: AppCompatActivity,
    private val fields: List<Place.Field>,
    private val typeFilter: TypeFilter,
    private val requestCode: Int) {

    init {
        Places.initialize(activity.applicationContext, App.API_PLACES_KEY)
    }

    fun getLaunchIntent(): Intent {

        val context = activity.applicationContext
        val location = SimpleLocation(context)

        return Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setLocationBias(
                RectangularBounds.newInstance(
                    LatLng(location.latitude, location.longitude),
                    LatLng(location.latitude + 0.1, location.longitude + 0.1)
                ))
            .setTypeFilter(typeFilter)
            .build(context)
    }

    fun getPlace(requestCode: Int, resultCode: Int, data: Intent?, view: View): Place? {

        if (requestCode == this.requestCode) {

            if (resultCode == AppCompatActivity.RESULT_OK) {

                return Autocomplete.getPlaceFromIntent(data!!)
            } else {

                Snackbar
                    .make(view, "Some error occured", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        return null
    }
}