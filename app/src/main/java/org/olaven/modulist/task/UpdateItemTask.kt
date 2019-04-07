package org.olaven.modulist.task

import android.app.Application
import org.olaven.modulist.database.entity.Item

class UpdateItemTask(application: Application): CustomTask<UpdateItemTask.DTO, Unit, Unit>(application) {

    class DTO(val item: Item)

    override fun doInBackground(vararg DTOs: DTO?) {

        DTOs.forEach {

            it?.let {dto ->

                val item = dto.item
                itemModel.update(item)
            }
        }
    }
}