package org.olaven.modulist.database

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DemoKtTest {

    private lateinit var database: AppDatabase

    @Before
    fun init() {
        database = AppDatabase.getDatabase(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun teardown() {
        database.close()
    }
}