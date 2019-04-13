package org.olaven.modulist.dialog.update

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import org.olaven.modulist.database.entity.ModuleList
import org.olaven.modulist.task.GetParentsTask
import org.olaven.modulist.task.UpdateParentsTask

class UpdateParentsDialog(val inheritanceOptions: List<ModuleList>, moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

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

    private fun isParent(child: ModuleList): Boolean {

        val dto = GetParentsTask.DTO(moduleList)

        val parents = GetParentsTask(activity.application)
            .execute(dto).get()

        return parents.contains(child)
    }
}