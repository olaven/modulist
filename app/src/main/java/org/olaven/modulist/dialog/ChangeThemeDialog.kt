package org.olaven.modulist.dialog

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_settings.*
import org.olaven.modulist.R

class ChangeThemeDialog(activity: AppCompatActivity): CustomDialog(activity) {

    private var selectedTheme: String? = null

    override fun show() {

        val options = activity.resources.getStringArray(R.array.theme_options)

        showCustomDialog("Select your new selectedTheme") {

            it.setSingleChoiceItems(R.array.theme_options, -1) { _, index ->

                selectedTheme = options[index]
            }
            setPositiveButton {

                changeTheme()
            }
        }
    }

    private fun changeTheme() {

        if (selectedTheme == null)
            return


        activity.apply{

            val preferences = getSharedPreferences(getString(R.string.preferences_modulist_key), Context.MODE_PRIVATE)
            val editor = preferences.edit()

            editor.putString(getString(R.string.theme_key), selectedTheme)
            editor.apply()

            Snackbar.make(fragment_settings, "Restart for changes to take effect", Snackbar.LENGTH_LONG).apply {
                this.setAction("restart") {
                    restart()
                }
                show()
            }
        }
    }



    private fun restart() {

        Handler().post {
            val intent = activity.intent
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
            activity.finish()
            activity.startActivity(intent)
        }
    }
}