package org.olaven.modulist.dialog.update

import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.R
import org.olaven.modulist.database.entity.ModuleList

class UpdateNameDialog(moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_update_name)) {

            val textView = EditText(activity)
            it.setView(textView)

            setPositiveButton {

                moduleList.name = textView.text.toString()
                update()
            }
        }
    }
}