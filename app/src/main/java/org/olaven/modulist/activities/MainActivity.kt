package org.olaven.modulist.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.olaven.modulist.R
import org.olaven.modulist.fragments.HomeFragment
import org.olaven.modulist.fragments.MyListsFragment
import org.olaven.modulist.fragments.SettingsFragment

class MainActivity : BaseActivity() {

    private var drawerToggle: ActionBarDrawerToggle? = null

    //TODO: hvis savedInstance IKKE er null, hent ut forrige fragment
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceMainFragment(HomeFragment())

        setUpActionBarWithDrawer()
        setupDrawerItemListeners()
    }

    private fun setupDrawerItemListeners() {
        activity_main_navigation_view.bringToFront()
        activity_main_navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home     -> replaceMainFragment(HomeFragment())
                R.id.nav_my_lists -> replaceMainFragment(MyListsFragment())
                R.id.nav_settings -> {
                    Toast.makeText(applicationContext, "SETTINGS", Toast.LENGTH_SHORT).show()
                    replaceMainFragment(SettingsFragment())
                }
            }
            activity_main_drawer.closeDrawer(Gravity.START)
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
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

    private fun replaceMainFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frameLayout, fragment)
        transaction.commit()
    }
}

