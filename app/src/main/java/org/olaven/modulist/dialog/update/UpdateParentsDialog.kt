package org.olaven.modulist.dialog.update

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.task.GetParentsTask
import org.olaven.modulist.task.update.UpdateParentsTask

class UpdateParentsDialog(moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        Models.getModuleListModel(activity.application).getAllModuleListsLive().observe(activity, Observer {

            it?.let { moduleLists ->

                val inheritanceOptions = moduleLists.filter { it.name != moduleList.name }

                val names = inheritanceOptions.map { it.name }.map { it as CharSequence }.toTypedArray()
                val checked = inheritanceOptions.map { isParent(it) }.toBooleanArray()
                val selected = inheritanceOptions.filter { isParent(it)}.toMutableList()

                showCustomDialog("Select parents") {

                    it.setMultiChoiceItems(names, checked) {dialog, which, isChecked ->

                        val element = inheritanceOptions[which]
                        if (isChecked)
                            selected.add(element)
                        else
                            selected.remove(element)
                    }

                    setPositiveButton {

                        val dao = UpdateParentsTask.DTO(moduleList, selected)
                        UpdateParentsTask(activity.application)
                            .execute(dao)
                    }
                }
            }

        })

    }

    private fun isParent(child: ModuleList): Boolean {

        val dto = GetParentsTask.DTO(moduleList)

        val parents = GetParentsTask(activity.application)
            .execute(dto).get()

        return parents.contains(child)
    }
}