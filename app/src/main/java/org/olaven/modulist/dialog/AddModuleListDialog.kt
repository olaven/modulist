package org.olaven.modulist.dialog

import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.ModuleList

class AddModuleListDialog(private val inheritanceOptions: List<ModuleList>, activity: Activity): CustomDialog(activity) {

    override fun show() {

        val names = inheritanceOptions.map { it.name }.map { it as CharSequence }.toTypedArray()
        val checked = inheritanceOptions.map { false }.toBooleanArray()

        var name: String
        val selected = mutableListOf<ModuleList>()

        displayCustomDialog("Add a list called..") {

            val textView = EditText(activity)
            it.setView(textView)
            setPositiveButton {

                val input = textView.text.toString()
                name = if (input.count() > 0)
                    input
                else
                    activity.getString(R.string.unloaded)

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
                                Toast.makeText(activity, "I want to add new list", Toast.LENGTH_SHORT).show()
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
}