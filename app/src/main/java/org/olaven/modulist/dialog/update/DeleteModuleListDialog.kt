package org.olaven.modulist.dialog.update

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import org.olaven.modulist.activity.MainActivity
import org.olaven.modulist.database.Models
import org.olaven.modulist.database.entity.ModuleList

class DeleteModuleListDialog(moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        showCustomDialog("This will delete the list PERMANENTLY.") {

            setPositiveButton {

                Models.getModuleListModel(activity.application)
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