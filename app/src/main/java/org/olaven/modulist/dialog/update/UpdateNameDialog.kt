package org.olaven.modulist.dialog.update

import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import org.olaven.modulist.database.entity.ModuleList

class UpdateNameDialog(moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        showCustomDialog("Enter new name") {

            val textView = EditText(activity)
            it.setView(textView)

            setPositiveButton {

                moduleList.name = textView.text.toString()
                update()
            }
        }
    }
}