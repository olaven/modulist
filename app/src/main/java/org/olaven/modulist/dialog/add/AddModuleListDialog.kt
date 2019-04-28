package org.olaven.modulist.dialog.add

import android.graphics.Color
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.dialog.CustomDialog
import org.olaven.modulist.task.add.AddModuleListTask


class AddModuleListDialog(private val inheritanceOptions: List<ModuleList>, activity: AppCompatActivity): CustomDialog(activity) {

    private val names = inheritanceOptions.map { it.name }.map { it as CharSequence }.toTypedArray()
    private val checked = inheritanceOptions.map { false }.toBooleanArray()

    var name: String = activity.getString(R.string.unloaded)
    private var listColor: Int = Color.GRAY
    private val selected = mutableListOf<ModuleList>()

    override fun show() {




        showCustomDialog("Add a list called..") {

            val textView = EditText(activity)
            it.setView(textView)

            setPositiveButton {

                val input = textView.text.toString()

                if (input.count() > 0) {
                    name = input
                }

                ColorPicker(activity).apply {

                    setCallback { colorFromPicker ->

                        listColor = colorFromPicker

                        // there are lists to extend
                        if (names.isNotEmpty()) {
                            showCustomDialog("What lists do you want to extend?") {

                                it.setMultiChoiceItems(names, checked) {_, index, checked ->

                                    val element = inheritanceOptions.get(index)
                                    if (checked)
                                        selected.add(element)
                                    else
                                        selected.remove(element)
                                }

                                setPositiveButton {
                                    showOverview()
                                }
                            }
                        } else {
                            showOverview()
                        }
                    }

                    enableAutoClose()
                    show()
                }
            }
        }
    }

    private fun showOverview() {

        showCustomDialog("Overview") {

            val listView = ListView(activity)
            listView.adapter = ArrayAdapter(activity.applicationContext, android.R.layout.simple_list_item_1, selected.map { it.name })

            it.setMessage("Name: $name \nParents: ${if (selected.isEmpty()) "NONE" else ""}")
            if (selected.isNotEmpty()) {
                it.setView(listView)
            }

            setPositiveButton {

                val moduleList = ModuleList(name, listColor)
                val dto = AddModuleListTask.DTO(moduleList, selected)
                //executes the task in background thread, as it writse to db
                AddModuleListTask(activity.application).execute(dto)
            }

            setNegativeButton {

                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


