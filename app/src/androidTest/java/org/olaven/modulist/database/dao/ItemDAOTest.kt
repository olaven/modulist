package org.olaven.modulist.database.dao

import android.graphics.Color
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.olaven.modulist.database.entity.Item
import org.olaven.modulist.database.entity.ModuleList


@RunWith(AndroidJUnit4::class)
class ItemDAOTest: DAOTest() {

    @Test
    @Throws(Exception::class)
    fun canRetrieve() {

        val moduleListId = moduleListDAO.insert(
            ModuleList("parent list", Color.BLUE)
        )

        val item = Item("some item", false, 3, moduleListId)
        val id = itemDAO.insert(item)

        val retrieved = getValue(
            itemDAO.getById(id)
        )
        assertThat(retrieved)
            .isEqualToIgnoringGivenFields(item, "id")

    }

    @Test
    fun canRetrieveByListId() {

        val moduleListId = moduleListDAO.insert(
            ModuleList("parent list", Color.BLUE)
        )

        val item = Item("some item", false, 3, moduleListId)
        itemDAO.insert(item)

        val retrieved = getValue(
            itemDAO.getByModuleListIdLive(moduleListId)
        )[0]

        assertThat(retrieved)
            .isEqualToIgnoringGivenFields(item, "id")
    }

    @Test
    fun byListIdRetrievesCorrectAmount() {

        val moduleListId = moduleListDAO.insert(
            ModuleList("parent list", Color.BLUE)
        )

        val items = listOf(
            Item("first item", false, 3, moduleListId),
            Item("second item", false, 3, moduleListId),
            Item("third item", false, 3, moduleListId)
        )

        items.forEach {
            itemDAO.insert(it)
        }

        val retrieved = getValue(
            itemDAO.getByModuleListIdLive(moduleListId)
        )

        assertThat(retrieved.count())
            .isEqualTo(items.count())

    }

    @Test
    fun byListIdOnlyRetrievesTheSameList() {

        val correctList = moduleListDAO.insert(
            ModuleList("correct list", Color.BLUE)
        )

        val wrongList = moduleListDAO.insert(
            ModuleList("wrong list", Color.BLUE)
        )

        //NOTE: one item is added to the other list
        val items = listOf(
            Item("first item", false, 3, correctList),
            Item("second item", false, 3, correctList),
            Item("third item", false, 3, wrongList)
        )

        items.forEach {
            itemDAO.insert(it)
        }

        val retrieved = getValue(
            itemDAO.getByModuleListIdLive(correctList)
        )

        assertThat(retrieved.count())
            .isEqualTo(items.count() - 1)
    }
}