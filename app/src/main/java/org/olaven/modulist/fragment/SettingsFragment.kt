package org.olaven.modulist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import org.olaven.modulist.R
import org.olaven.modulist.dialog.ChangeThemeDialog
import org.olaven.modulist.dialog.ClearDataDialog
import org.olaven.modulist.dialog.add.AddDemoDataDialog


class SettingsFragment: androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        fragment_settings_button_change_theme.setOnClickListener {

            ChangeThemeDialog(activity as AppCompatActivity)
                .show()
        }

        fragment_settings_button_load_demo_data.setOnClickListener {

            AddDemoDataDialog(activity as AppCompatActivity)
                .show()
        }

        fragment_settings_button_clear_data.setOnClickListener {

            ClearDataDialog(activity as AppCompatActivity)
                .show()
        }

    }

}
