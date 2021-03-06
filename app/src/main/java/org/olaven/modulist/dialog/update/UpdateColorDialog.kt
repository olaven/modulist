package org.olaven.modulist.dialog.update

import androidx.appcompat.app.AppCompatActivity
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.olaven.modulist.database.entity.ModuleList

class UpdateColorDialog(moduleList: ModuleList, activity: AppCompatActivity): UpdateModuleListDialog(moduleList, activity) {

    override fun show() {

        ColorPicker(activity).apply {
            setCallback { color ->

                moduleList.color = color
                update()
            }
            show()
            enableAutoClose()
        }
    }
}