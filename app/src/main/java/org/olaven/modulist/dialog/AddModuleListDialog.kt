package org.olaven.modulist.dialog

import android.app.Activity
import android.content.DialogInterface
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
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
            it.setPositiveButton("Next") { _, _ ->

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
                    it.setPositiveButton("Next") { _, _ ->

                        displayCustomDialog("Overview") {

                            val listView = ListView(activity)
                            listView.adapter = ArrayAdapter(activity.applicationContext, android.R.layout.simple_list_item_1, selected.map { it.name })

                            it.setMessage("Name: $name \nSelected parents:")
                            it.setView(listView)


                            it.setPositiveButton("Looks good") { _, _ ->

                            }

                            it.setNegativeButton("Not what I intended") { _, _ ->

                            }
                        }
                    }
                }
            }
        }
    }
}