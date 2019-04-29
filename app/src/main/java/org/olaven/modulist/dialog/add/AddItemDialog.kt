package org.olaven.modulist.dialog.add

import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.CustomDialog
import org.olaven.modulist.task.add.AddItemTask

class AddItemDialog(val moduleList: ModuleList, activity: AppCompatActivity): CustomDialog(activity) {

    var name = activity.getString(R.string.unloaded)!!
    private var dayDistribution = Integer.MAX_VALUE
    private val alertContext = activity.applicationContext!!

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_add_item_name)) {

            val view = EditText(alertContext)
            it.setView(view)
            setPositiveButton {

                name = view.text.toString()

                showCustomDialog(activity.getString(R.string.dialog_add_item_day_distribution)) {

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
                                activity.getString(R.string.dialog_add_item_day_just_the_one)
                            else
                                dayDistribution.toString()

                        val view = TextView(alertContext).apply {
                            text = activity.getString(R.string.dialog_add_item_day_final_message, name, distributionMessage)
                        }

                        showCustomDialog(activity.getString(R.string.dialog_add_item_okay_question)) {

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

