package org.olaven.modulist.dialog

import android.app.Activity
import android.graphics.Color
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import org.olaven.modulist.R
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.ModuleList

class AddModuleListDialog(private val inheritanceOptions: List<ModuleList>, activity: Activity): CustomDialog(activity) {

    val names = inheritanceOptions.map { it.name }.map { it as CharSequence }.toTypedArray()
    val checked = inheritanceOptions.map { false }.toBooleanArray()

    var name: String = activity.getString(R.string.unloaded)
    val selected = mutableListOf<ModuleList>()

    override fun show() {



        displayCustomDialog("Add a list called..") {

            val textView = EditText(activity)
            it.setView(textView)
            setPositiveButton {

                val input = textView.text.toString()

                //otherwise, it stays as R.string.unloaded
                if (input.count() > 0) {
                    name = input
                }

                displayCustomDialog("What lists do you want to inherit from?") {

                    it.setMultiChoiceItems(names, checked) {_, index, checked ->

                        val element = inheritanceOptions.get(index)
                        if (checked)
                            selected.add(element)
                        else
                            selected.remove(element)
                    }

                    setPositiveButton {

                        displayCustomDialog("Overview") {

                            val listView = ListView(activity)
                            listView.adapter = ArrayAdapter(activity.applicationContext, android.R.layout.simple_list_item_1, selected.map { it.name })

                            it.setMessage("Name: $name \nSelected parents:")
                            it.setView(listView)

                            setPositiveButton {

                                addModuleList()
                            }

                            setNegativeButton {
                                Toast.makeText(activity, "I am not happy", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addModuleList() {

        //TODO add items from iheritance 
        val moduleList = ModuleList(name, Color.RED)
        Models.getModuleListModel(activity.application)
            .insert(moduleList)
    }
}