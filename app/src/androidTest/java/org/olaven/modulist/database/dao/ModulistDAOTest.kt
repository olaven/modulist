package org.olaven.modulist.database.dao

import android.graphics.Color
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.entity.ModuleList

@RunWith(AndroidJUnit4::class)
class ModulistDAOTest: DAOTest() {

    @Test
    fun canInsert() {

        val list = ModuleList("some list", Color.BLUE)
        val id = moduleListDAO.insert(list)

        val retrieved = getValue(moduleListDAO.getByIdLive(id))
        assertThat(retrieved)
            .isEqualToIgnoringGivenFields(list, "id")
    }

    @Test
    fun test() {

        val list = ModuleList("some list", Color.BLUE)
        val id = moduleListDAO.insert(list)

        val before = getValue(moduleListDAO.getByIdLive(id))
        assertThat(before)
            .isNotNull

        moduleListDAO.delete(before)

        val after = getValue(moduleListDAO.getByIdLive(before.id!!))

        assertThat(after)
            .isNull()
    }
}