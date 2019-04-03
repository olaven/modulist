package org.olaven.modulist.database.dao

import android.graphics.Color
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.entity.ListRelation
import org.olaven.modulist.database.entity.ModuleList

@RunWith(AndroidJUnit4::class)
class ListRelationDAOTest: DAOTest() {

    @Test
    fun gettingByParentWorks() {

        val firstSuper = moduleListDAO.insert(ModuleList("first super", Color.CYAN))
        val secondSuper = moduleListDAO.insert(ModuleList("second super", Color.BLUE))
        val sub = moduleListDAO.insert(ModuleList("sub", Color.RED))


        listRelationDAO.insert(ListRelation(sub, firstSuper))
        listRelationDAO.insert(ListRelation(sub, secondSuper))

        val onFirst = getValue(listRelationDAO.getByParentIdLive(firstSuper))
        assertThat(onFirst.count())
            .isEqualTo(1)

        val onSecond = getValue(listRelationDAO.getByParentIdLive(secondSuper))
        assertThat(onSecond.count())
            .isEqualTo(1)
    }

    @Test
    fun gettignByChildWorks() {

        val firstSuper = moduleListDAO.insert(ModuleList("first super", Color.CYAN))
        val secondSuper = moduleListDAO.insert(ModuleList("second super", Color.BLUE))
        val sub = moduleListDAO.insert(ModuleList("sub", Color.RED))


        listRelationDAO.insert(ListRelation(sub, firstSuper))
        listRelationDAO.insert(ListRelation(sub, secondSuper))

        val onSub = getValue(listRelationDAO.getByChildIdLive(sub))
        assertThat(onSub.count())
            .isEqualTo(2)
    }
}