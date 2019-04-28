package org.olaven.modulist.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.fragment.MyListsFragment
import org.olaven.modulist.fragment.SettingsFragment
import org.olaven.modulist.fragment.TutorialVideoFragment
import org.olaven.modulist.fragment.WeatherPlannerFragment

class MainActivity : BaseActivity() {

    private var drawerToggle: ActionBarDrawerToggle? = null

    companion object {

        fun catchNotification(context: Context): Intent =
                Intent(context, ModuleList::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        replaceMainFragment(MyListsFragment())
        setUpActionBarWithDrawer()
        setupDrawerItemListeners()
    }

    private fun setupDrawerItemListeners() {
        
        activity_main_navigation_view.bringToFront()
        activity_main_navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_my_lists -> replaceMainFragment(MyListsFragment())
                R.id.nav_weather_planner -> replaceMainFragment(WeatherPlannerFragment())
                R.id.nav_settings -> replaceMainFragment(SettingsFragment())
                R.id.nav_tutorial_videos -> replaceMainFragment(TutorialVideoFragment())
            }
            activity_main_drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (drawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setUpActionBarWithDrawer() {

        drawerToggle = ActionBarDrawerToggle(this, activity_main_drawer, R.string.open, R.string.close)
        activity_main_drawer.addDrawerListener(drawerToggle!!)
        drawerToggle?.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun replaceMainFragment(fragment: androidx.fragment.app.Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frameLayout, fragment)
        transaction.commit()
    }
}

