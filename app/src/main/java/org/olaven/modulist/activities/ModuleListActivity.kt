package org.olaven.modulist.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_module_list.*
import org.olaven.modulist.R

class ModuleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_list)

        val id: Int = intent.extras["id"] as Int
        id.let {
            // TODO: Replace mock with fetching from database
            activity_module_list_name.text = "PLACEHOLDER"
            activity_module_list_name.text = id.toString()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }


}
