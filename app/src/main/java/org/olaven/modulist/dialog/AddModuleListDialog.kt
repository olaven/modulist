package org.olaven.modulist.dialog

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.olaven.modulist.R
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList


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

                                        val dto = InsertModulelistTask.DTO(activity, name, color, inheritanceOptions)
                                        InsertModulelistTask().execute(dto)
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


private class InsertModulelistTask: AsyncTask<InsertModulelistTask.DTO, Any, Unit>() {

    class DTO(val activity: AppCompatActivity, val name: String, val color: Int, val inheritanceOptions: List<ModuleList>)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                val itemModel = Models.getItemModel(it.activity.application)
                val moduleListModel = Models.getModuleListModel(it.activity.application)
                val listRelationModel = Models.getListRelationModel(it.activity.application)

                // persist the list
                val moduleList = ModuleList(dto.name, dto.color)
                val id = moduleListModel.insertForId(moduleList)


                /*
                 * For each parent
                 * 1: fetch items and add them to current one
                 * 2: store relation in database as ListRelation
                 */
                val items = mutableListOf<Item>()
                dto.inheritanceOptions.forEach {parent ->


                    //1:
                    val parentItemsLive = itemModel.getByModuleListId(parent.id!!)
                    parentItemsLive.observe(dto.activity, Observer {

                        it?.let { items ->

                            parentItemsLive.removeObservers(dto.activity)
                            items.forEach {

                                val copy = Item(it.name, it.done, it.dayDistribution, id)
                                itemModel.insert(copy)
                            }
                        }
                    })

                    //2:
                    val listRelation = ListRelation(moduleList.id, parent.id)
                    listRelationModel.insert(listRelation)
                }
            }
        }
    }
}
