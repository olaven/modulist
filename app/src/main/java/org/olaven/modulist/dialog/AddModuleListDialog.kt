package org.olaven.modulist.dialog

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.tasks.InsertModulelistTask


class AddModuleListDialog(private val inheritanceOptions: List<ModuleList>, activity: AppCompatActivity): CustomDialog(activity) {

    private val names = inheritanceOptions.map { it.name }.map { it as CharSequence }.toTypedArray()
    private val checked = inheritanceOptions.map { false }.toBooleanArray()

    var name: String = activity.getString(R.string.unloaded)
    private var color: Int = Color.GRAY//just default
    private val selected = mutableListOf<ModuleList>()

    override fun show() {



        showCustomDialog("Add a list called..") {

            val textView = EditText(activity)
            it.setView(textView)
            setPositiveButton {

                val input = textView.text.toString()

                //otherwise, it stays as R.string.unloaded
                if (input.count() > 0) {
                    name = input
                }

                showCustomDialog("What lists do you want to extend?") {

                    it.setMultiChoiceItems(names, checked) {_, index, checked ->

                        val element = inheritanceOptions.get(index)
                        if (checked)
                            selected.add(element)
                        else
                            selected.remove(element)
                    }

                    setPositiveButton {

                        ColorPicker(activity).apply {
                            setCallback {color ->

                                this.color = color

                                showCustomDialog("Overview") {

                                    val listView = ListView(activity)
                                    listView.adapter = ArrayAdapter(activity.applicationContext, android.R.layout.simple_list_item_1, selected.map { it.name })

                                    it.setMessage("Name: $name \nSelected parents:")
                                    it.setView(listView)

                                    setPositiveButton {

                                        val moduleList = ModuleList(name, color)
                                        val dto = InsertModulelistTask.DTO(moduleList, inheritanceOptions)
                                        //executes the task in background thread, as it reads from db
                                        InsertModulelistTask(activity.application).execute(dto)
                                    }

                                    setNegativeButton {
                                        Toast.makeText(activity, "I am not happy", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            show()
                            enableAutoClose()
                        }
                    }
                }
            }
        }
    }

}


