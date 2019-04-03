package org.olaven.modulist.dialog

import android.app.Activity
import android.widget.EditText
import android.widget.TextView
import org.olaven.modulist.R
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import java.lang.Exception

class AddItemDialog(val moduleList: ModuleList, val activity: Activity): CustomDialog(activity) {

    var name = activity.getString(R.string.unloaded)
    var dayDistribution = Integer.MAX_VALUE
    val alertContext = activity.applicationContext

    override fun show() {

        createCustomDialog(activity, "Add an item called...") {

            val view = EditText(alertContext)
            it.setView(view)
            it.setPositiveButton("continue") { _, _ ->

                name = view.text.toString()

                createCustomDialog(activity, "Pack one for every ... days") {

                    val dayOptions = activity.resources.getStringArray(R.array.day_options)
                    it.setSingleChoiceItems(dayOptions, -1) { _, item ->

                        try {
                            dayDistribution = dayOptions[item].toInt()
                        } catch (e: Exception) {
                            //Intentionally blank.
                            //Distribution stays the same.
                        }
                    }
                    it.setPositiveButton("continue") { _, _ ->

                        val distributionMessage =
                            if (dayDistribution == Integer.MAX_VALUE)
                                "Pack just the one."
                            else
                                dayDistribution.toString()

                        val view = TextView(alertContext).apply {
                            text = "Name: $name \n Distribution: $distributionMessage"
                        }

                        createCustomDialog(activity, "Does this look okay?") {

                            it.setView(view)
                            it.setPositiveButton("Looks good") { _, _ ->

                                val item = Item(name, false, dayDistribution, moduleList.id!!)
                                Models
                                    .getItemModel(activity.application)
                                    .insert(item)
                            }
                            it.setNegativeButton("Not what I intended") { _, _ -> }
                        }
                    }
                }
            }
        }
    }
}