package org.olaven.modulist.dialog.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import org.olaven.modulist.R
import org.olaven.modulist.activity.MainActivity
import org.olaven.modulist.database.ModelFactory
import org.olaven.modulist.database.entity.ModuleList

class DeleteModuleListDialog(moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        showCustomDialog(activity.getString(R.string.dialog_delete_module_list)) {

            setPositiveButton {

                ModelFactory.getModuleListModel(activity.application)
                    .delete(moduleList)

                // send user away from deleted item
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            }
            //although no content, here to assure user that s/he can get back to safety
            setNegativeButton {  }
        }
    }
}