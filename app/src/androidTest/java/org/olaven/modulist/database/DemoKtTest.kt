package org.olaven.modulist.database

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DemoKtTest {

    private lateinit var database: AppDatabase
    private lateinit var application: Application

    @Before
    fun init() {
        database = AppDatabase.getDatabase(InstrumentationRegistry.getInstrumentation().context)
        // application = InstrumentationRegistry.getInstrumentation()//HER
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun someDataIsInserted() {
        assertThat(false)
            .isTrue()
        // addDemoData()
    }
}