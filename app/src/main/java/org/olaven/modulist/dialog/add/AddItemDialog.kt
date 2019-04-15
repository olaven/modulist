package org.olaven.modulist.dialog.add

import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.CustomDialog
import org.olaven.modulist.task.add.AddItemTask
import java.lang.Exception

class AddItemDialog(val moduleList: ModuleList, activity: AppCompatActivity): CustomDialog(activity) {

    var name = activity.getString(R.string.unloaded)!!
    private var dayDistribution = Integer.MAX_VALUE
    private val alertContext = activity.applicationContext!!

    override fun show() {

        showCustomDialog("Add an item called...") {

            val view = EditText(alertContext)
            it.setView(view)
            setPositiveButton {

                name = view.text.toString()

                showCustomDialog("Pack one for every ... days") {

                    val dayOptions = activity.resources.getStringArray(R.array.day_options)
                    it.setSingleChoiceItems(dayOptions, -1) { _, item ->

                        try {
                            dayDistribution = dayOptions[item].toInt()
                        } catch (e: Exception) {
                            //Intentionally blank.
                            //Distribution stays the same.
                        }
                    }

                   setPositiveButton {

                        val distributionMessage =
                            if (dayDistribution == Integer.MAX_VALUE)
                                "Pack just the one."
                            else
                                dayDistribution.toString()

                        val view = TextView(alertContext).apply {
                            text = "Name: $name \n Distribution: $distributionMessage"
                        }

                        showCustomDialog("Does this look okay?") {

                            it.setView(view)
                            setPositiveButton {

                                val item = Item(name, false, dayDistribution, moduleList.id!!)
                                //addItem()
                                val dto = AddItemTask.DTO(item)
                                AddItemTask(activity.application).execute(dto)
                            }
                            setNegativeButton {  }
                        }
                    }
                }
            }
        }
    }
}

