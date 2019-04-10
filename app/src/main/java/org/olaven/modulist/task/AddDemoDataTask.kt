package org.olaven.modulist.task

import android.app.Application
import android.graphics.Color
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList

class AddDemoDataTask(application: Application): CustomTask<AddDemoDataTask.DTO, Unit, Unit>(application) {

    // not relevant in this task
    class DTO

    override fun doInBackground(vararg DTOs: DTO?) {

        /**
         * 1: Insert modulelists
         * 2: Make them parent and child
         * 3: Insert items
         */

        //1:
        val holiday = moduleListModel
            .insertForId(ModuleList("Holiday", Color.MAGENTA))
        val winter = moduleListModel
            .insertForId(ModuleList("Winter holiday", Color.CYAN))
        val summer = moduleListModel
            .insertForId(ModuleList("Summer holiday", Color.RED))


        //2:
        listRelationModel.insert(
            ListRelation(winter, holiday)
        )
        listRelationModel.insert(
            ListRelation(summer, holiday)
        )

        //3:
        listOf(
            Item("Toothbrush", false, 30, holiday),
            Item("Socks", false, 1, holiday),
            Item("Book", false, 12, holiday),
            Item("Winter jacket", false, Integer.MAX_VALUE, winter),
            Item("Bathing suit", false, Integer.MAX_VALUE, summer),
            Item("Shorts", false, 2, summer)
        ).forEach {
            val dto = AddItemTask.DTO(it)
            AddItemTask(application).execute(dto)
        }
    }
}