package org.olaven.modulist.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.olaven.modulist.R

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        applyTheme()
        super.onCreate(savedInstanceState)
    }


    private fun applyTheme() {

        val themeName = getThemeName()

        Toast.makeText(applicationContext, themeName, Toast.LENGTH_SHORT)

        when(themeName) {
            getString(R.string.menu_theme_default) -> setTheme(R.style.DefaultTheme)
            getString(R.string.menu_theme_dark)    -> setTheme(R.style.DarkTheme)
            getString(R.string.menu_theme_fruit)   -> setTheme(R.style.FruitTheme)
            getString(R.string.menu_theme_cabin)   -> setTheme(R.style.CabinTheme)
        }
    }

    private fun getThemeName(): String? {
        val preferenceKey = getString(R.string.preferences_modulist)
        val themeKey = getString(R.string.theme_key)

        val preferences = getSharedPreferences(preferenceKey, Context.MODE_PRIVATE)
        val default = getString(R.string.menu_theme_default)

        val themeName = preferences.getString(themeKey, default)
        return themeName
    }
}