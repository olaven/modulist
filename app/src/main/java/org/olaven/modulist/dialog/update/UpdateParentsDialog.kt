package org.olaven.modulist.dialog.update

import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import org.olaven.modulist.database.entity.ModuleList

class UpdateParentsDialog(val inheritanceOptions: List<ModuleList>, moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        showCustomDialog("Select parents") {


            Toast.makeText(activity, "IMPLEMENT ME", Toast.LENGTH_SHORT).show()
            
            setPositiveButton {

            }
        }
    }
}