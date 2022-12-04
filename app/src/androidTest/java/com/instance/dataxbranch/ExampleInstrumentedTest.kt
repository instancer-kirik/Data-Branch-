package com.instance.dataxbranch

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.instance.dataxbranch.data.AppDatabase
import com.instance.dataxbranch.data.daos.UserDao
import com.instance.dataxbranch.data.entities.CharacterEntity
import com.instance.dataxbranch.data.local.CharacterWithStuff
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.instance.dataxbranch", appContext.packageName)
    }
}
@RunWith(AndroidJUnit4::class)
class TodoDatabaseTest {

    private lateinit var uDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context =               InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        uDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCharacter() = runBlocking {
        val char = CharacterEntity(uuid = "test")//(itemId = 1, itemName = "Dummy Item", isDone = false)
        uDao.insertCharacter(char)
        val oneItem = uDao.getCharacterEntity(UUID.fromString("test"))
        assertEquals(oneItem?.uuid, "test")
    }

}