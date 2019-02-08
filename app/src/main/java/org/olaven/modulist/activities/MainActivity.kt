package org.olaven.modulist.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.olaven.modulist.R
import org.olaven.modulist.fragments.HomeFragment
import org.olaven.modulist.fragments.MyListsFragment
import org.olaven.modulist.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBarWithDrawer()
        setupDrawerItemListeners()
    }

    private fun setupDrawerItemListeners() {
        main_activity_navigation_view.bringToFront()
        main_activity_navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceMainFragment(HomeFragment())
                R.id.nav_my_lists -> replaceMainFragment(MyListsFragment())
                R.id.nav_settings -> replaceMainFragment(SettingsFragment())
            }
            main_activity_drawer.closeDrawer(Gravity.START)
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
        drawerToggle = ActionBarDrawerToggle(this, main_activity_drawer, R.string.open, R.string.close)

        main_activity_drawer.addDrawerListener(drawerToggle!!)
        drawerToggle?.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun replaceMainFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment, fragment)
        transaction.commit()

    }
}

