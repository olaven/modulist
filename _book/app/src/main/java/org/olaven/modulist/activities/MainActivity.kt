package org.olaven.modulist.activities

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.olaven.modulist.R

class MainActivity : AppCompatActivity() {

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBarWithDrawer()
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
}

