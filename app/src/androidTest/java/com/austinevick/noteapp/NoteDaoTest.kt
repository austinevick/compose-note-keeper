package com.austinevick.noteapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.austinevick.noteapp.data.NoteDao
import com.austinevick.noteapp.data.NoteDatabase
import com.austinevick.noteapp.data.NoteEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .build()
        noteDao = noteDatabase.noteDao()
    }


    @Test
    fun addItem_shouldReturn_theItem_inFlow() = runTest {

    }





@After
fun tearDown() {
    noteDatabase.close()
}

}