package org.olaven.modulist.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_settings.*
import org.olaven.modulist.R


class SettingsFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        Toast.makeText(activity!!.applicationContext, "create", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(activity!!.applicationContext, "HELLO", Toast.LENGTH_SHORT).show()
        fragment_settings_button_change_theme.setOnClickListener {

            activity?.let {

                setupThemePopup(it.applicationContext)
            }
        }

    }

    private fun setupThemePopup(context: Context) {

        PopupMenu(context, fragment_settings).apply {
            inflate(R.menu.menu_theme)
            show()

            setOnMenuItemClickListener {

                val themeName = it.title.toString()
                changeTheme(themeName)
                true
            }
        }
    }

    private fun changeTheme(themeName: String) {

        setThemePreference(themeName)

        Snackbar.make(fragment_settings, "Restart for changes to take effect", Snackbar.LENGTH_LONG).apply {
            this.setAction("restart") {
                restart()
            }
            show()
        }
    }

    private fun setThemePreference(name: String) {

        activity?.let {
            val preferences = it.getSharedPreferences(getString(R.string.preferences_modulist), Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(getString(R.string.theme_key), name)
            editor.apply()
        }
    }

    private fun restart() {


           Handler().post(Runnable {
               val intent = activity!!.intent
               intent.addFlags(
                   Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                           or Intent.FLAG_ACTIVITY_NO_ANIMATION
               )
               activity!!.finish()
               startActivity(intent)
           })
    }
}
